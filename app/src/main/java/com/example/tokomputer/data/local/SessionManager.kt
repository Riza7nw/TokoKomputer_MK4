package com.example.tokomputer.data.local

import android.content.Context
import android.content.SharedPreferences

object SessionManager {

    private const val PREF_NAME = "tokomputer_session"
    private const val KEY_TOKEN = "auth_token"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"

    private lateinit var prefs: SharedPreferences

    // Dipanggil sekali di MainActivity
    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // Simpan token setelah login
    fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    // Ambil token
    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    // Cek sudah login atau belum
    fun isLoggedIn(): Boolean {
        return getToken() != null
    }

    // Simpan data user
    fun saveUserName(name: String) {
        prefs.edit().putString(KEY_USER_NAME, name).apply()
    }

    fun getUserName(): String? {
        return prefs.getString(KEY_USER_NAME, null)
    }

    fun saveUserEmail(email: String) {
        prefs.edit().putString(KEY_USER_EMAIL, email).apply()
    }

    fun getUserEmail(): String? {
        return prefs.getString(KEY_USER_EMAIL, null)
    }

    // Hapus semua saat logout
    fun clearLogin() {
        prefs.edit().clear().apply()
    }
}