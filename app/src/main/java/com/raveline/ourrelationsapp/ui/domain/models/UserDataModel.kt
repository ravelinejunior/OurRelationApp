package com.raveline.ourrelationsapp.ui.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDataModel(
    var userId: String? = "",
    var name: String? = "",
    var userName: String? = "",
    var imageUrl: String? = "",
    var email: String? = "",
    var password: String? = "",
    var bio: String? = "",
    var gender: String? = "",
    var genderPreference: String? = "",

    ):Parcelable {
    fun toMap() = mapOf(
        "userId" to userId,
        "name" to name,
        "userName" to userName,
        "imageUrl" to imageUrl,
        "email" to email,
        "password" to password,
        "bio" to bio,
        "gender" to bio,
        "genderPreference" to bio,
    )
}

enum class GenderEnum{
    MALE, FEMALE, OTHER
}
