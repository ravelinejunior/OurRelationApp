package com.raveline.ourrelationsapp.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.toObject
import com.raveline.ourrelationsapp.ui.common.utils.userFirebaseDatabaseCollection
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
import okhttp3.internal.wait
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

    fun signOut() {
        viewModelScope.launch {
            authenticationUseCaseModel.signInUseCase.invoke()
            delay(300)
            _userState.value = null
            mUser = null
        }
    }

    companion object {
        var mUser: UserDataModel? = null
    }

}