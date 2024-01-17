package com.raveline.ourrelationsapp.ui.common.utils

import android.annotation.SuppressLint
import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@SuppressLint("GetInstance")
fun encryptString(inputString: String, secretKey: String): String {
    val cipher = Cipher.getInstance("AES")
    val key = SecretKeySpec(secretKey.toByteArray(), "AES")
    cipher.init(Cipher.ENCRYPT_MODE, key)
    val encrypted = cipher.doFinal(inputString.toByteArray(Charsets.UTF_8))
    return Base64.encodeToString(encrypted, Base64.DEFAULT)
}

@SuppressLint("GetInstance")
fun decryptString(encrypted: String, key: String): String {
    val cipher = Cipher.getInstance("AES")
    val secretKey = SecretKeySpec(key.toByteArray(), "AES")

    cipher.init(Cipher.DECRYPT_MODE, secretKey)
    val decoded = Base64.decode(encrypted, Base64.DEFAULT)
    val decrypted = cipher.doFinal(decoded)
    return String(decrypted, Charsets.UTF_8)
}