package com.example.tokomputer.ui.member

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tokomputer.data.local.SessionManager
import com.example.tokomputer.ui.main.MainActivity

class MemberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Resolve layout resource dynamically to avoid static R reference issues
        val layoutId = resources.getIdentifier("activity_member", "layout", packageName)
        if (layoutId == 0) {
            // Layout not found, finish gracefully
            finish()
            return
        }
        setContentView(layoutId)

        // Initialize SharedPrefManager
        SessionManager.init(this)

        // Lookup view IDs dynamically
        val tvNameId = resources.getIdentifier("tvMemberName", "id", packageName)
        val tvEmailId = resources.getIdentifier("tvMemberEmail", "id", packageName)
        val btnLogoutId = resources.getIdentifier("btnLogout", "id", packageName)
        val btnHomeId = resources.getIdentifier("btnhomelog", "id", packageName)

        val tvName = if (tvNameId != 0) findViewById<TextView>(tvNameId) else null
        val tvEmail = if (tvEmailId != 0) findViewById<TextView>(tvEmailId) else null
        val btnLogout = if (btnLogoutId != 0) findViewById<Button>(btnLogoutId) else null
        val btnHome = if (btnHomeId != 0) findViewById<Button>(btnHomeId) else null

        tvName?.text = SessionManager.getName() ?: getString(resources.getIdentifier("dash", "string", packageName))
        tvEmail?.text = SessionManager.getEmail() ?: getString(resources.getIdentifier("dash", "string", packageName))

        btnLogout?.setOnClickListener {
            SessionManager.clearLogin()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        btnHome?.setOnClickListener {
            // Navigate to MainActivity (home) when clicked
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
