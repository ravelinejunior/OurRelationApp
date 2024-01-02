package com.raveline.ourrelationsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OurRelationsViewModel @Inject constructor(
    private val firebaseAuthentication: FirebaseAuth,
    private val fireStoreDatabase: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {

}