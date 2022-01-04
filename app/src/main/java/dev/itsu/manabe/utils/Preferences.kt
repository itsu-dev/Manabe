package dev.itsu.manabe.utils

import android.content.Context
import android.util.Base64
import dev.itsu.manabe.MyApplication
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object Preferences {

    private val preferences = MyApplication.getInstance().getSharedPreferences("data", Context.MODE_PRIVATE)
    private const val KEY = "beafff1928374610"

    fun setCredentials(userId: String, password: String) = preferences.edit().let {
        it.putString("userId", userId)
        it.putString("password", encrypt(password))
        it.commit()
    }

    fun getCredentials(): Pair<String, String> {
        val raw = preferences.getString("password", "")!!
        return Pair(preferences.getString("userId", "")!!, if (raw.isNotEmpty()) decrypt(raw) else "")
    }

    fun clear() = preferences.edit().let {
        it.clear()
        it.commit()
    }

    private fun encrypt(passwordRaw: String): String {
        val keySpec = SecretKeySpec(KEY.toByteArray(StandardCharsets.UTF_8), "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        val encrypted = cipher.doFinal(passwordRaw.toByteArray(StandardCharsets.UTF_8))
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    private fun decrypt(encrypted: String): String {
        val keySpec = SecretKeySpec(KEY.toByteArray(StandardCharsets.UTF_8), "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        val decryptBytes = Base64.decode(encrypted, Base64.DEFAULT)
        return String(cipher.doFinal(decryptBytes))
    }

}