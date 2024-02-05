package com.raveline.ourrelationsapp.ui.domain.interfaces

import android.net.Uri

interface UserAuthenticationRepository {
    suspend fun signUpUser(userName: String, email: String, password: String): Pair<Boolean, String>
    suspend fun signInUser(email: String, password: String): Pair<Boolean, String>
    suspend fun createOrUpdateUser(
        name: String? = "",
        userName: String? = "",
        email: String? = "",
        bio: String? = "",
        imageUrl: String? = "",
        encryptedPassword: String? = "",
        gender: String? = "",
        genderPreference: String? = "",
        swipesLeft: List<String>? = emptyList(),
        swipesRight: List<String>? = emptyList(),
        matches: List<String>? = emptyList(),
    ): Pair<Boolean, String>

    suspend fun signOutUser()
    suspend fun uploadUserImage(uri: Uri, userUid: String): Pair<Boolean, String>
}