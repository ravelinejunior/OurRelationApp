package com.raveline.ourrelationsapp.ui.data.repository_impl

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.raveline.ourrelationsapp.ui.common.utils.userFirebaseDatabaseCollection
import com.raveline.ourrelationsapp.ui.common.utils.userNameFirebaseKey
import com.raveline.ourrelationsapp.ui.domain.interfaces.UserAuthenticationRepository
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserAuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuthentication: FirebaseAuth,
    private val fireStoreDatabase: FirebaseFirestore
) : UserAuthenticationRepository {
    private val TAG: String = "UserAuthenticationRepositoryImpl"

    override suspend fun signUpUser(
        userName: String,
        email: String,
        password: String
    ): Pair<Boolean, String> =
        suspendCoroutine { continuation ->
            fireStoreDatabase.collection(userFirebaseDatabaseCollection)
                .whereEqualTo(userNameFirebaseKey, userName)
                .get()
                .addOnSuccessListener { docs ->
                    if (docs.isEmpty) {
                        firebaseAuthentication.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    continuation.resume(
                                        Pair(
                                            true,
                                            "User successfully created"
                                        )
                                    )
                                } else {
                                    continuation.resume(
                                        Pair(
                                            false,
                                            it.exception?.message.toString()
                                        )
                                    )
                                }
                            }
                    } else {
                        // User already exists
                        continuation.resume((Pair(false, "User already exists")))
                    }
                }.addOnFailureListener { e ->
                    Log.e(TAG, "Failed saving user because: ${e.message}")
                    continuation.resume(
                        Pair(
                            false,
                            "Failed saving user because: ${e.message}"
                        )
                    )

                }
        }

    override suspend fun signInUser(email: String, password: String): Pair<Boolean, String> {
        TODO("Not yet implemented")
    }

    override suspend fun createOrUpdateUser(
        name: String?,
        email: String?,
        bio: String?,
        imageUrl: String?,
        encryptedPassword: String?
    ): Pair<Boolean, String> = suspendCoroutine { continuation ->
        val uid = firebaseAuthentication.currentUser?.uid
        val userData = UserDataModel(
            userId = uid,
            userName = name,
            email = email,
            bio = bio,
            imageUrl = imageUrl,
            password = encryptedPassword
        )

        uid?.let { mUid ->
            fireStoreDatabase
                .collection(userFirebaseDatabaseCollection)
                .document(mUid)
                .get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        doc.reference
                            .update(userData.toMap())
                            .addOnSuccessListener {
                                continuation.resume(
                                    Pair(
                                        true,
                                        "User successfully updated"
                                    )
                                )
                            }.addOnFailureListener {
                                Log.e(
                                    TAG,
                                    "createOrUpdateUser\nFailed updating user because:${it.message}"
                                )
                                continuation.resume(
                                    Pair(
                                        false,
                                        "Failed updating user because:${it.message}"
                                    )
                                )
                            }
                    } else {
                        fireStoreDatabase
                            .collection(userFirebaseDatabaseCollection)
                            .document(uid)
                            .set(userData)
                            .addOnSuccessListener {
                                continuation.resume(
                                    Pair(
                                        true,
                                        "User successfully stored"
                                    )
                                )
                            }.addOnFailureListener { e ->
                                Log.e(
                                    TAG,
                                    "createOrUpdateUser\nFailed creating user because:${e.message}"
                                )
                                Pair(
                                    false,
                                    "Failed creating user because:${e.message}"
                                )
                            }
                    }
                }.addOnFailureListener {
                    Log.e(
                        TAG,
                        "createOrUpdateUser\nFailed creating user because:${it.message}"
                    )
                    continuation.resume(
                        Pair(
                            false,
                            "Failed creating user because:${it.message}"
                        )
                    )
                }
        }

    }
}












