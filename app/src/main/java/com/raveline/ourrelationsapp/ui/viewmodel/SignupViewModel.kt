package com.raveline.ourrelationsapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val useCaseModel: AuthenticationUseCaseModel
) : ViewModel() {
    private val TAG: String = SignupViewModel::class.java.simpleName
    val popUpNotification = mutableStateOf<StateEvent<String>?>(StateEvent(""))
    val inProgress = mutableStateOf(false)

    private val _userState = MutableStateFlow<UserDataModel?>(UserDataModel())
    val userState = _userState.asStateFlow()

    fun onSignup(userName: String, email: String, password: String) {
        if (userName.isEmpty() or email.isEmpty() or password.isEmpty()) {
            handleException(customMessage = "All fields must be filled!")
            return
        }
        inProgress.value = true
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
                    name = userName,
                    email = email,
                    password = encrypt,
                )

                if (userStored.first) {
                    // user created
                    isUserLoggedIn()
                } else {
                    handleException(customMessage = userStored.second)
                }
            } else {
                handleException(customMessage = singUpUserComplete.second)
            }
        }

    }

    private fun isUserLoggedIn() = viewModelScope.launch(Dispatchers.Main) {
        val firebaseAuthentication = useCaseModel.firebaseAuth
        val fireStoreDatabase = useCaseModel.fireStore
        suspendCoroutine<Pair<Boolean, UserDataModel?>> {
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

                            it.resume(Pair(true, userModel))
                            inProgress.value = false
                        }
                    }
            } else {
                inProgress.value = false
                it.resume(Pair(false, null))
            }
        }

    }

    private suspend fun createOrUpdateProfile(
        name: String,
        email: String,
        bio: String? = null,
        imageUrl: String? = null,
        password: String,
        gender: GenderEnum? = null,
        genderPreference: String? = null,
    ): Pair<Boolean, String> {
        val encryptedPassword = encryptString(password, encryptionKey)
        val result = useCaseModel.createOrUpdateUserUseCase.invoke(
            name, email, bio, imageUrl, encryptedPassword, gender?.name, genderPreference
        )
        return viewModelScope.async {
            result
        }.await()
    }

    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.e(TAG, "Handle Exception ${exception?.message}", exception)
        exception?.stackTrace
        val errorMessage = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMessage else "$customMessage: $errorMessage"
        popUpNotification.value = StateEvent(message)
        inProgress.value = false
    }
}