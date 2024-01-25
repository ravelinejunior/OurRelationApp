package com.raveline.ourrelationsapp.ui.common.utils

import android.annotation.SuppressLint
import android.util.Base64
import java.util.Locale
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

fun customCapitalize(value: String): String {
    return value.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault())
        else it.toString()
    }
}

@SuppressLint("GetInstance")
fun encryptString(inputString: String, secretKey: String): String {
    val cipher = Cipher.getInstance("AES")
    val key = SecretKeySpec(secretKey.toByteArray(), "AES")
    cipher.init(Cipher.ENCRYPT_MODE, key)
    val encrypted = cipher.doFinal(inputString.toByteArray(Charsets.UTF_8))
    return Base64.encodeToString(encrypted, Base64.DEFAULT)
}