package com.raveline.ourrelationsapp.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.toObject
import com.raveline.ourrelationsapp.ui.common.utils.userFirebaseDatabaseCollection
import com.raveline.ourrelationsapp.ui.domain.models.GenderEnum
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.domain.state.StateEvent
import com.raveline.ourrelationsapp.ui.domain.use_case.authentication.AuthenticationUseCaseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationUseCaseModel: AuthenticationUseCaseModel,
) : ViewModel() {

    private val TAG = AuthenticationViewModel::class.java.simpleName

    val popUpNotification = mutableStateOf<StateEvent<String>?>(StateEvent(""))
    val inProgress = mutableStateOf(false)
    val inProgressMatches = mutableStateOf(false)

    private val _userState = MutableStateFlow<UserDataModel?>(UserDataModel())
    val userState = _userState.asStateFlow()

    val firebaseAuth = authenticationUseCaseModel.firebaseAuth
    private val fireStoreDatabase = authenticationUseCaseModel.fireStore

    val matchProfiles = mutableStateOf<List<UserDataModel>>(emptyList())

    init {
        isUserLoggedIn()
    }

    private fun populateMatchesCards() {
        inProgressMatches.value = true

        val gender = if (userState.value?.gender.isNullOrEmpty()) GenderEnum.OTHER.name
        else userState.value!!.gender!!.uppercase()

        val genderPref =
            if (userState.value?.genderPreference.isNullOrEmpty()) GenderEnum.OTHER.name
            else userState.value!!.genderPreference!!.uppercase()

        val cardsQuery = when (GenderEnum.valueOf(genderPref)) {
            GenderEnum.MALE -> fireStoreDatabase.collection(userFirebaseDatabaseCollection)
                .whereEqualTo("gender", GenderEnum.MALE)

            GenderEnum.FEMALE -> fireStoreDatabase.collection(userFirebaseDatabaseCollection)
                .whereEqualTo("gender", GenderEnum.FEMALE)

            GenderEnum.OTHER -> fireStoreDatabase.collection(userFirebaseDatabaseCollection)

        }

        val userGender = GenderEnum.valueOf(gender)

        cardsQuery.where(
            Filter.and(
                Filter.notEqualTo("userId", userState.value?.userId),
                Filter.or(
                    Filter.equalTo("genderPreference", userGender),
                    Filter.equalTo("genderPreference", GenderEnum.OTHER)
                )
            )
        ).addSnapshotListener { value, error ->
            if (error != null) {
                inProgressMatches.value = false
                handleException(error)
            }

            if (value != null) {
                val potentialMatches = mutableListOf<UserDataModel>()
                value.documents.forEach { document ->
                    document.toObject<UserDataModel>()?.let { potentialMatch ->
                        var showUser = true

                        if (userState.value?.swipesLeft?.contains(potentialMatch.userId) == true ||
                            userState.value?.swipesRight?.contains(potentialMatch.userId) == true ||
                            userState.value?.matches?.contains(potentialMatch.userId) == true
                        ) {
                            showUser = false
                        }

                        if (showUser) {
                            potentialMatches.add(potentialMatch)
                        }
                    }
                }

                matchProfiles.value = potentialMatches
                inProgressMatches.value = false
            }


        }
    }

    @OptIn(InternalCoroutinesApi::class)
    private fun isUserLoggedIn() = viewModelScope.launch(Main) {

        suspendCancellableCoroutine<Pair<Boolean, UserDataModel?>> {
            if (firebaseAuth.currentUser != null) {
                fireStoreDatabase.collection(userFirebaseDatabaseCollection)
                    .document(firebaseAuth.currentUser?.uid.toString())
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            it.resume(Pair(false, null))
                        }
                        if (value != null) {
                            val userModel = value.toObject<UserDataModel>()

                            if (userModel != null) {
                                _userState.value = userModel
                                mUser = userModel
                            }
                            it.tryResume(Pair(true, userModel))
                        }
                        it.cancel()
                    }
            } else {
                inProgress.value = false
                it.resume(Pair(false, null))
                it.cancel()
            }
        }

    }

    suspend fun createOrUpdateProfile(
        name: String?,
        userName: String?,
        bio: String?,
        imageUrl: String?,
        gender: GenderEnum = GenderEnum.OTHER,
        genderPreference: GenderEnum = GenderEnum.OTHER,
    ) {
        inProgress.value = true
        authenticationUseCaseModel.createOrUpdateUserUseCase.invoke(
            name = name,
            userName = userName,
            email = mUser?.email,
            bio = bio,
            imageUrl = imageUrl,
            encryptedPassword = mUser?.password,
            gender = gender.name,
            genderPreference = genderPreference.name
        )

        mUser = mUser?.copy(
            name = name,
            userName = userName,
            bio = bio,
            imageUrl = imageUrl,
            gender = gender.name,
            genderPreference = genderPreference.name
        )

        _userState.value = mUser
        populateMatchesCards()
        delay(300)
        inProgress.value = false
    }

    fun uploadProfileImage(
        uri: Uri,
        uid: String,
        name: String,
        userName: String,
        bio: String,
        gender: String,
        genderPreference: String,
    ) = viewModelScope.launch {
        inProgress.value = true

        viewModelScope.launch {
            val result = authenticationUseCaseModel.createOrUpdateUserUseCase.invoke(
                uri = uri,
                userUid = uid
            )

            if (result.first) {
                createOrUpdateProfile(
                    name = name,
                    userName = userName,
                    bio = bio,
                    gender = GenderEnum.valueOf(gender),
                    genderPreference = GenderEnum.valueOf(genderPreference),
                    imageUrl = result.second
                )
            } else {
                handleException(customMessage = result.second)
            }

            inProgress.value = false

        }
    }

    fun signOut() {
        viewModelScope.launch {
            authenticationUseCaseModel.signInUseCase.invoke()
            delay(300)
            _userState.value = null
            mUser = null
        }
    }

    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.e(TAG, "Handle Exception ${exception?.message}", exception)
        exception?.stackTrace
        val errorMessage = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMessage else "$customMessage: $errorMessage"
        popUpNotification.value = StateEvent(message)
        inProgress.value = false
    }

    companion object {
        var mUser: UserDataModel? = null
    }

}