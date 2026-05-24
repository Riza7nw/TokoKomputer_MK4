package com.example.tokomputer.data.local

import android.content.Context
import android.content.SharedPreferences

object SessionManager {

    private const val PREF_NAME    = "tokomputer_prefs"
    private const val KEY_TOKEN    = "auth_token"
    private const val KEY_NAME     = "user_name"
    private const val KEY_EMAIL    = "user_email"

    private var prefs: SharedPreferences? = null

    // Dipanggil sekali di MainActivity atau Application
    fun init(context: Context) {
        if (prefs == null) {
            prefs = context.applicationContext
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    fun saveToken(token: String) {
        prefs?.edit()?.putString(KEY_TOKEN, token)?.apply()
    }

    fun getToken(): String? {
        return prefs?.getString(KEY_TOKEN, null)
    }

    fun isLoggedIn(): Boolean {
        return !getToken().isNullOrEmpty()
    }

    fun saveUserName(name: String) {
        prefs?.edit()?.putString(KEY_NAME, name)?.apply()
    }

    fun saveUserEmail(email: String) {
        prefs?.edit()?.putString(KEY_EMAIL, email)?.apply()
    }

    fun getUserName(): String? {
        return prefs?.getString(KEY_NAME, null)
    }

    fun getUserEmail(): String? {
        return prefs?.getString(KEY_EMAIL, null)
    }

    fun clearLogin() {
        prefs?.edit()?.clear()?.apply()
    }
}