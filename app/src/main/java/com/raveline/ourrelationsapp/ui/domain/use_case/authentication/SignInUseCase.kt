package com.raveline.ourrelationsapp.ui.domain.use_case.authentication

import com.raveline.ourrelationsapp.ui.domain.interfaces.UserAuthenticationRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repository: UserAuthenticationRepository) {
    suspend operator fun invoke(email: String, password: String):Pair<Boolean,String> {
        return repository.signInUser(
            email = email,
            password = password
        )
    }

    suspend operator fun invoke(){
        repository.signOutUser()
    }
}