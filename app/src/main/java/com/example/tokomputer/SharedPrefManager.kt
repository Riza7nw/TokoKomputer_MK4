package com.example.tokomputer

import android.content.Context
import android.content.SharedPreferences

object SharedPrefManager {
    private const val PREF_NAME = "user_prefs"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setLogin(name: String?, email: String?) {
        if (!::prefs.isInitialized) return
        prefs.edit()
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .putString(KEY_USER_NAME, name ?: "")
            .putString(KEY_USER_EMAIL, email ?: "")
            .apply()
    }

    fun clearLogin() {
        if (!::prefs.isInitialized) return
        prefs.edit()
            .putBoolean(KEY_IS_LOGGED_IN, false)
            .putString(KEY_USER_NAME, "")
            .putString(KEY_USER_EMAIL, "")
            .apply()
    }

    fun isLoggedIn(): Boolean {
        if (!::prefs.isInitialized) return false
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getName(): String? {
        if (!::prefs.isInitialized) return null
        return prefs.getString(KEY_USER_NAME, "")
    }

    fun getEmail(): String? {
        if (!::prefs.isInitialized) return null
        return prefs.getString(KEY_USER_EMAIL, "")
    }
}

