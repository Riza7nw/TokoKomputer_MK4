package com.example.tokomputer

import android.app.Application
import com.example.tokomputer.data.local.SessionManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // Init SessionManager di sini agar siap sebelum Activity manapun dibuka
        SessionManager.init(this)
    }
}