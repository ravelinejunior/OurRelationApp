package com.raveline.ourrelationsapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.toObject
import com.raveline.ourrelationsapp.ui.common.utils.encryptString
import com.raveline.ourrelationsapp.ui.common.utils.encryptionKey
import com.raveline.ourrelationsapp.ui.common.utils.userFirebaseDatabaseCollection
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.domain.state.StateEvent
import com.raveline.ourrelationsapp.ui.domain.use_case.authentication.AuthenticationUseCaseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class OurRelationsViewModel @Inject constructor(
    private val authenticationUseCaseModel: AuthenticationUseCaseModel
) : ViewModel() {

    private val TAG = "OurRelationsViewModel"

    val inProgress = mutableStateOf(false)
    val popUpNotification = mutableStateOf<StateEvent<String>?>(StateEvent(""))

    private val _userState = MutableStateFlow<UserDataModel?>(null)
    val userState: StateFlow<UserDataModel?> get() = _userState

    init {
        isUserLoggedIn()
    }

    fun isUserLoggedIn() = viewModelScope.launch {
        inProgress.value = true
        val firebaseAuthentication = authenticationUseCaseModel.firebaseAuth
        val fireStoreDatabase = authenticationUseCaseModel.fireStore
        suspendCoroutine<Pair<Boolean, UserDataModel?>> {
            if (firebaseAuthentication.currentUser != null) {
                fireStoreDatabase.collection(userFirebaseDatabaseCollection)
                    .document(firebaseAuthentication.currentUser?.uid.toString())
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            it.resume(Pair(false, null))
                        }
                        if (value != null) {
                            inProgress.value = false
                            val userModel = value.toObject<UserDataModel>()
                            _userState.update {
                                userModel
                            }
                            it.resume(Pair(true, userModel))
                        }
                    }
            } else {
                it.resume(Pair(false, null))
                inProgress.value = false
            }
            inProgress.value = false
        }

    }

    fun onSignup(userName: String, email: String, password: String) {
        inProgress.value = true
        if (userName.isEmpty() or email.isEmpty() or password.isEmpty()) {
            handleException(customMessage = "All fields must be filled!")
            inProgress.value = false
            return
        }

        viewModelScope.launch {
            val singUpUserComplete =
                authenticationUseCaseModel.signUpUseCase.invoke(userName, email, password)
            if (singUpUserComplete.first) {
                val encrypt = encryptString(password, encryptionKey)
                val userStored = createOrUpdateProfile(
                    name = userName,
                    password = encrypt
                )

                if (userStored.first) {
                    // user created
                    isUserLoggedIn()
                    inProgress.value = false
                } else {
                    handleException(customMessage = userStored.second)
                }
                inProgress.value = false
            } else {
                handleException(customMessage = singUpUserComplete.second)
                inProgress.value = false
            }
        }

    }

    private suspend fun createOrUpdateProfile(
        name: String? = null,
        email: String? = null,
        bio: String? = null,
        imageUrl: String? = null,
        password: String,
    ): Pair<Boolean, String> {
        val encryptedPassword = encryptString(password, encryptionKey)
        val result = authenticationUseCaseModel.createOrUpdateUserUseCase.invoke(
            name, email, bio, imageUrl, encryptedPassword
        )

        return viewModelScope.async { result }.await()
    }

    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.e(TAG, "Handle Exception", exception)
        exception?.stackTrace
        val errorMessage = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMessage else "$customMessage: $errorMessage"
        popUpNotification.value = StateEvent(message)
        inProgress.value = false
    }

}