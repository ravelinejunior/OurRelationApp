package com.raveline.ourrelationsapp.ui.domain.use_case.authentication

import android.net.Uri
import com.raveline.ourrelationsapp.ui.domain.interfaces.UserAuthenticationRepository
import javax.inject.Inject

class CreateOrUpdateUserUseCase @Inject constructor(private val repository: UserAuthenticationRepository) {
    suspend operator fun invoke(
        name: String? = "",
        userName: String? = "",
        email: String? = "",
        bio: String? = "",
        imageUrl: String? = "",
        encryptedPassword: String? = "",
        gender: String? = "",
        genderPreference: String? = ""
    ): Pair<Boolean, String> {
        return repository.createOrUpdateUser(
            name = name,
            userName = userName,
            email = email,
            bio = bio,
            imageUrl = imageUrl,
            encryptedPassword = encryptedPassword,
            gender = gender,
            genderPreference = genderPreference
        )
    }

    suspend operator fun invoke(uri: Uri, userUid: String): Pair<Boolean, String> {
        return repository.uploadUserImage(
            uri = uri,
            userUid = userUid
        )
    }
}