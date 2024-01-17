package com.raveline.ourrelationsapp.ui.domain.use_case.authentication

data class AuthenticationUseCaseModel(
    val signInUseCase: SignInUseCase,
    val signUpUseCase: SignUpUseCase,
    val createOrUpdateUserUseCase: CreateOrUpdateUserUseCase
)