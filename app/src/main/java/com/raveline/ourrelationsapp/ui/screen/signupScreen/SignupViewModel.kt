package com.raveline.ourrelationsapp.ui.screen.signupScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.toObject
import com.raveline.ourrelationsapp.ui.common.utils.customCapitalize
import com.raveline.ourrelationsapp.ui.common.utils.encryptString
import com.raveline.ourrelationsapp.ui.common.utils.encryptionKey
import com.raveline.ourrelationsapp.ui.common.utils.userFirebaseDatabaseCollection
import com.raveline.ourrelationsapp.ui.domain.models.GenderEnum
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.domain.state.StateEvent
import com.raveline.ourrelationsapp.ui.domain.use_case.authentication.AuthenticationUseCaseModel
import com.raveline.ourrelationsapp.ui.viewmodel.AuthenticationViewModel.Companion.mUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val useCaseModel: AuthenticationUseCaseModel
) : ViewModel() {
    private val TAG: String = SignupViewModel::class.java.simpleName
    val popUpNotification = mutableStateOf<StateEvent<String>?>(StateEvent(""))
    val inProgress = mutableStateOf(false)
    val inProgressMatches = mutableStateOf(false)

    private val _userState = MutableStateFlow<UserDataModel?>(UserDataModel())
    val userState = _userState.asStateFlow()

    private val _signupState = mutableStateOf(SignupState())
    val signupState: State<SignupState> = _signupState

    private val fireStoreDatabase = useCaseModel.fireStore

    val matchProfiles = mutableStateOf<List<UserDataModel>>(emptyList())

    fun onSignupEvent(event: SignupEvent) {
        when (event) {

            is SignupEvent.SignupUser -> {
                onSignupUser(
                    userName = event.userName,
                    email = event.email,
                    password = event.password
                )
            }

            is SignupEvent.GetFirebaseUserData -> {
                isUserLoggedIn()
            }
        }

    }

    private fun onSignupUser(userName: String, email: String, password: String) {
        if (userName.isEmpty() or email.isEmpty() or password.isEmpty()) {
            handleException(customMessage = "All fields must be filled!")
            return
        }
        _signupState.value = signupState.value.copy(inProgress = mutableStateOf(true))
        viewModelScope.launch {
            val singUpUserComplete =
                useCaseModel.signUpUseCase.invoke(
                    customCapitalize(userName),
                    email,
                    password
                )
            if (singUpUserComplete.first) {
                val encrypt = encryptString(password, encryptionKey)
                val userStored = createOrUpdateProfile(
                    userName = userName,
                    email = email,
                    password = encrypt,
                )

                if (userStored.first) {
                    populateMatchesCards()
                    isUserLoggedIn()
                } else {
                    handleException(customMessage = userStored.second)
                }
            } else {
                handleException(customMessage = singUpUserComplete.second)
            }
        }

    }

    @OptIn(InternalCoroutinesApi::class)
    private fun isUserLoggedIn() = viewModelScope.launch(Dispatchers.Main) {
        val firebaseAuthentication = useCaseModel.firebaseAuth
        val fireStoreDatabase = useCaseModel.fireStore
        suspendCancellableCoroutine<Pair<Boolean, UserDataModel?>> {
            if (firebaseAuthentication.currentUser != null) {
                fireStoreDatabase.collection(userFirebaseDatabaseCollection)
                    .document(firebaseAuthentication.currentUser?.uid.toString())
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            it.resume(Pair(false, null))
                        }
                        if (value != null) {
                            val userModel = value.toObject<UserDataModel>()
                            _userState.value = userModel
                            mUser = userModel!!
                            inProgress.value = false
                            _signupState.value =
                                signupState.value.copy(inProgress = mutableStateOf(false))
                            it.tryResume(Pair(true, userModel))
                        }
                        it.cancel()
                    }
            } else {
                _signupState.value = signupState.value.copy(inProgress = mutableStateOf(false))
                it.resume(Pair(false, null))
                it.cancel()
            }
        }

    }

    private suspend fun createOrUpdateProfile(
        name: String = "",
        email: String,
        userName: String? = "",
        bio: String? = "",
        imageUrl: String? = "",
        password: String,
        gender: GenderEnum? = null,
        genderPreference: String? = "",
    ): Pair<Boolean, String> {
        val encryptedPassword = encryptString(password, encryptionKey)
        val result = useCaseModel.createOrUpdateUserUseCase.invoke(
            name = name,
            email = email,
            userName = userName,
            bio = bio,
            imageUrl = imageUrl,
            encryptedPassword = encryptedPassword,
            gender = gender?.name,
            genderPreference = genderPreference
        )
        return viewModelScope.async {
            result
        }.await()
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

    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.e(TAG, "Handle Exception ${exception?.message}", exception)
        exception?.stackTrace
        val errorMessage = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMessage else "$customMessage: $errorMessage"
        popUpNotification.value = StateEvent(message)
        inProgress.value = false
        _signupState.value = signupState.value.copy(inProgress = mutableStateOf(false))
    }
}