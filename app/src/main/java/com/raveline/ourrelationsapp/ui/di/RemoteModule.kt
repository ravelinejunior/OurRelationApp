package com.raveline.ourrelationsapp.ui.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.raveline.ourrelationsapp.ui.data.repository_impl.UserAuthenticationRepositoryImpl
import com.raveline.ourrelationsapp.ui.domain.interfaces.UserAuthenticationRepository
import com.raveline.ourrelationsapp.ui.domain.use_case.authentication.AuthenticationUseCaseModel
import com.raveline.ourrelationsapp.ui.domain.use_case.authentication.CreateOrUpdateUserUseCase
import com.raveline.ourrelationsapp.ui.domain.use_case.authentication.SignInUseCase
import com.raveline.ourrelationsapp.ui.domain.use_case.authentication.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun providesFirebaseAuthentication(): FirebaseAuth = Firebase.auth

    @Provides
    fun providesFireStoreDatabase(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun providesStorage(): FirebaseStorage = Firebase.storage

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuthentication: FirebaseAuth,
        fireStoreDatabase: FirebaseFirestore,
        firebaseStorage: FirebaseStorage,
    ): UserAuthenticationRepository = UserAuthenticationRepositoryImpl(
        firebaseAuthentication = firebaseAuthentication,
        fireStoreDatabase = fireStoreDatabase,
        firebaseStorage = firebaseStorage
    )

    @Provides
    @Singleton
    fun provideAuthenticationUseCaseModel(
        repository: UserAuthenticationRepository,
        firebaseAuthentication: FirebaseAuth,
        fireStoreDatabase: FirebaseFirestore
    ): AuthenticationUseCaseModel =
        AuthenticationUseCaseModel(
            SignInUseCase(repository),
            SignUpUseCase(repository),
            CreateOrUpdateUserUseCase(repository),
            firebaseAuthentication,
            fireStoreDatabase
        )
}