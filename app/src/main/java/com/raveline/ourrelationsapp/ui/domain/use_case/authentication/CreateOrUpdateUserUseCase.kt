package com.raveline.ourrelationsapp.ui.domain.use_case.authentication

import com.raveline.ourrelationsapp.ui.domain.interfaces.UserAuthenticationRepository
import javax.inject.Inject

class CreateOrUpdateUserUseCase @Inject constructor(private val repository: UserAuthenticationRepository) {
    suspend operator fun invoke(
        name: String? = null,
        email: String? = null,
        bio: String? = null,
        imageUrl: String? = null,
        encryptedPassword: String? = null

    ):Pair<Boolean,String> {
        return repository.createOrUpdateUser(
            name = name,
            email = email,
            bio = bio,
            imageUrl = imageUrl,
            encryptedPassword = encryptedPassword
        )
    }
}