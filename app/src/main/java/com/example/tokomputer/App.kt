package com.example.tokomputer

import android.app.Application
import com.example.tokomputer.data.local.SessionManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SessionManager.init(this)
        SessionManager.checkFreshStart()
    }
}