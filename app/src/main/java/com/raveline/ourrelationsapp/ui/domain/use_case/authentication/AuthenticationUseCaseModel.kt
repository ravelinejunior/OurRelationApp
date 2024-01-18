package com.raveline.ourrelationsapp.ui.domain.use_case.authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class AuthenticationUseCaseModel(
    val signInUseCase: SignInUseCase,
    val signUpUseCase: SignUpUseCase,
    val createOrUpdateUserUseCase: CreateOrUpdateUserUseCase,
    val firebaseAuth: FirebaseAuth,
    val fireStore: FirebaseFirestore
)