package com.example.tokomputer

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.tokomputer.data.local.SessionManager

class App : Application() {

    private var activityCount = 0

    override fun onCreate() {
        super.onCreate()
        SessionManager.init(this)

        // Cek apakah ini fresh start (setelah kill/crash/install ulang)
        SessionManager.checkFreshStart()

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityStarted(activity: Activity) {
                activityCount++
            }

            override fun onActivityStopped(activity: Activity) {
                activityCount--
                if (activityCount == 0) {
                    // App ditutup normal → tandai closed + clear token
                    SessionManager.setAppClosed()
                    SessionManager.clearLogin()
                }
            }

            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }
}