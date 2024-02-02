package com.raveline.ourrelationsapp.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _userState = MutableStateFlow<UserDataModel?>(UserDataModel())
    val userState = _userState.asStateFlow()

    val firebaseAuth = authenticationUseCaseModel.firebaseAuth
    private val fireStoreDatabase = authenticationUseCaseModel.fireStore

    init {
        isUserLoggedIn()
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
                            _userState.value = userModel
                            mUser = userModel!!
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
        name: String? = null,
        userName: String? = null,
        bio: String? = null,
        imageUrl: String? = null,
        gender: GenderEnum? = null,
        genderPreference: GenderEnum? = null,
    ) {
        inProgress.value = true
        authenticationUseCaseModel.createOrUpdateUserUseCase.invoke(
            name = name,
            userName = userName,
            email = mUser?.email,
            bio = bio,
            imageUrl = imageUrl,
            encryptedPassword = mUser?.password,
            gender = gender?.name,
            genderPreference = genderPreference?.name
        )

        mUser = mUser?.copy(
            name = name,
            userName = userName,
            bio = bio,
            imageUrl = imageUrl,
            gender = gender?.name,
            genderPreference = genderPreference?.name
        )

        _userState.value = mUser
        delay(1000)
        inProgress.value = false
    }

    fun uploadImageUI(uri: Uri) = viewModelScope.launch {
        inProgress.value = true
        delay(500L)
        _userState.value = userState.value?.copy(imageUrl = uri.toString())
        mUser = userState.value
        inProgress.value = false
    }

    fun uploadProfileImage(uri: Uri, uid: String) = viewModelScope.launch {
        inProgress.value = true
        val user = userState.value!!

        viewModelScope.launch {
            val result = authenticationUseCaseModel.createOrUpdateUserUseCase.invoke(
                uri = uri,
                userUid = uid
            )

            if (result.first) {
                createOrUpdateProfile(
                    name = user.name,
                    userName = user.userName,
                    bio = user.bio,
                    gender = GenderEnum.valueOf(user.gender.toString()),
                    genderPreference = GenderEnum.valueOf(user.genderPreference.toString()),
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