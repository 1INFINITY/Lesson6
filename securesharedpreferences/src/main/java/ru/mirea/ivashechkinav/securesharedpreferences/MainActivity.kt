package ru.mirea.ivashechkinav.securesharedpreferences

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private var keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private var mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val secureSharedPreferences = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            mainKeyAlias,
            baseContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        try {
            secureSharedPreferences.edit().putString("secure", "МОЙ ЛЮБИМЫЙ АКТЕР!!!").apply()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        val result: String? = secureSharedPreferences.getString("secure", "ЛЮБИМЫЙ АКТЕР")
    }
}