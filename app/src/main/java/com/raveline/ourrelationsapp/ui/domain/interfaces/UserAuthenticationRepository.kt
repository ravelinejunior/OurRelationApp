package com.raveline.ourrelationsapp.ui.domain.interfaces

import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import kotlinx.coroutines.flow.StateFlow

interface UserAuthenticationRepository {
    suspend fun signUpUser(userName: String, email: String, password: String): Pair<Boolean, String>
    suspend fun signInUser(email: String, password: String): Pair<Boolean, String>
    suspend fun createOrUpdateUser(
        name: String? = null,
        email: String? = null,
        bio: String? = null,
        imageUrl: String? = null,
        encryptedPassword: String? = null,
    ): Pair<Boolean, String>

}