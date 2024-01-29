package com.raveline.ourrelationsapp.ui.viewmodel

import android.util.Log
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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

    init {
        isUserLoggedIn()
    }

    fun isUserLoggedIn() = viewModelScope.launch(Main) {
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
                            val userModel = value.toObject<UserDataModel>()
                            _userState.value = userModel
                            mUser = userModel!!
                            it.resume(Pair(true, userModel))
                        }
                    }
            } else {
                it.resume(Pair(false, null))
            }
        }

    }

    fun signOut() {
        viewModelScope.launch {
            authenticationUseCaseModel.signInUseCase.invoke().run {
                _userState.emit(null)
                mUser = null
            }
        }
    }

    companion object {
        var mUser: UserDataModel? = null
    }

}