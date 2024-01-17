package com.raveline.ourrelationsapp.ui.domain.use_case.authentication

import com.raveline.ourrelationsapp.ui.domain.interfaces.UserAuthenticationRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: UserAuthenticationRepository) {
    suspend operator fun invoke(
        userName: String,
        email: String,
        password: String
    ): Pair<Boolean, String> {
        return repository.signUpUser(
            userName = userName,
            email = email,
            password = password
        )
    }
}