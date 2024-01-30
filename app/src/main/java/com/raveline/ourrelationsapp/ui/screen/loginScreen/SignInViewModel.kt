package com.raveline.ourrelationsapp.ui.screen.loginScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.toObject
import com.raveline.ourrelationsapp.ui.common.utils.userFirebaseDatabaseCollection
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.domain.state.StateEvent
import com.raveline.ourrelationsapp.ui.domain.use_case.authentication.AuthenticationUseCaseModel
import com.raveline.ourrelationsapp.ui.viewmodel.AuthenticationViewModel.Companion.mUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val useCaseModel: AuthenticationUseCaseModel
) : ViewModel() {
    private val TAG: String = SignInViewModel::class.java.simpleName
    val popUpNotification = mutableStateOf<StateEvent<String>?>(StateEvent(""))
    val inProgress = mutableStateOf(false)

    private val _userState = MutableStateFlow<UserDataModel?>(UserDataModel())
    val userState = _userState.asStateFlow()

    val firebaseAuthentication = useCaseModel.firebaseAuth
    val fireStoreDatabase = useCaseModel.fireStore

    fun onSignIn(email: String, password: String) {
        if (email.isEmpty() or password.isEmpty()) {
            handleException(customMessage = "All fields must be filled!")
            return
        }

        inProgress.value = true

        viewModelScope.launch {
            val signInComplete =
                useCaseModel.signInUseCase.invoke(email.trim(), password.trim())
            if (signInComplete.first) {
                isUserLoggedIn()
                inProgress.value = false
            } else {
                handleException(customMessage = signInComplete.second)
            }
        }

    }


    @OptIn(InternalCoroutinesApi::class)
    fun isUserLoggedIn() = viewModelScope.launch(Dispatchers.Main) {
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

    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.e(TAG, "Handle Exception ${exception?.message}", exception)
        exception?.stackTrace
        val errorMessage = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMessage else "$customMessage: $errorMessage"
        popUpNotification.value = StateEvent(message)
        inProgress.value = false
    }
}