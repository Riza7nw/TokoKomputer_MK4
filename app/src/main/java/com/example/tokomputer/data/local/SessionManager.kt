package com.example.tokomputer.data.local

import android.content.Context
import android.content.SharedPreferences

object SessionManager {

    private const val PREF_NAME   = "tokomputer_prefs"
    private const val KEY_TOKEN   = "auth_token"
    private const val KEY_NAME    = "user_name"
    private const val KEY_EMAIL   = "user_email"
    private const val KEY_RUNNING = "app_running"

    private var prefs: SharedPreferences? = null

    fun init(context: Context) {
        if (prefs == null) {
            prefs = context.applicationContext
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    fun checkFreshStart() {
        val wasRunning = prefs?.getBoolean(KEY_RUNNING, false) ?: false
        if (wasRunning) {
            // App di-kill / install ulang tanpa ditutup normal → clear token
            clearLogin()
        }
        // Tandai app sedang berjalan
        prefs?.edit()?.putBoolean(KEY_RUNNING, true)?.apply()
    }

    fun setAppClosed() {
        prefs?.edit()?.putBoolean(KEY_RUNNING, false)?.apply()
    }

    fun saveToken(token: String) {
        prefs?.edit()?.putString(KEY_TOKEN, token)?.apply()
    }

    fun getToken(): String? = prefs?.getString(KEY_TOKEN, null)

    fun isLoggedIn(): Boolean = !getToken().isNullOrEmpty()

    fun saveUserName(name: String) {
        prefs?.edit()?.putString(KEY_NAME, name)?.apply()
    }

    fun saveUserEmail(email: String) {
        prefs?.edit()?.putString(KEY_EMAIL, email)?.apply()
    }

    fun getUserName(): String? = prefs?.getString(KEY_NAME, null)
    fun getUserEmail(): String? = prefs?.getString(KEY_EMAIL, null)

    fun clearLogin() {
        prefs?.edit()
            ?.remove(KEY_TOKEN)
            ?.remove(KEY_NAME)
            ?.remove(KEY_EMAIL)
            ?.apply()
    }
}