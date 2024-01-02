package com.raveline.ourrelationsapp.ui.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun providesFirebaseAuthentication():FirebaseAuth = Firebase.auth

    @Provides
    fun providesFireStoreDatabase(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun providesStorage(): FirebaseStorage = Firebase.storage
}