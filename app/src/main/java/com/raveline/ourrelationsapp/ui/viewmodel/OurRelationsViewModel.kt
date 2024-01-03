package com.raveline.ourrelationsapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.raveline.ourrelationsapp.ui.common.utils.userFirebaseDatabaseCollection
import com.raveline.ourrelationsapp.ui.common.utils.userNameFirebaseKey
import com.raveline.ourrelationsapp.ui.data.state.StateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OurRelationsViewModel @Inject constructor(
    private val firebaseAuthentication: FirebaseAuth,
    private val fireStoreDatabase: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {

    private val TAG = "OurRelationsViewModel"

    val inProgress = mutableStateOf(false)
    val popUpNotification = mutableStateOf<StateEvent<String>?>(StateEvent("Potato"))

    fun onSignup(userName: String, email: String, password: String) {
        inProgress.value = true
        if (userName.isEmpty() or email.isEmpty() or password.isEmpty()) {
            handleException(customMessage = "All fields must be filled!")
            inProgress.value = false
            return
        }

        fireStoreDatabase.collection(userFirebaseDatabaseCollection)
            .whereEqualTo(userNameFirebaseKey, userName)
            .get()
            .addOnSuccessListener { task ->
                if (task.isEmpty) {
                    firebaseAuthentication.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { compTask ->
                            inProgress.value = false
                            if (compTask.isSuccessful) {
                                popUpNotification.value =
                                    StateEvent(compTask.result.user?.email.toString() + " Created")
                            } else {
                                handleException(compTask.exception, "Signup Failed")
                            }
                        }
                }
            }.addOnFailureListener { e ->
                inProgress.value = false
                handleException(e)
            }
    }

    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.e(TAG, "Handle Exception", exception)
        exception?.stackTrace
        val errorMessage = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMessage else "$customMessage: $errorMessage"
        popUpNotification.value = StateEvent(message)
        inProgress.value = false
    }

}