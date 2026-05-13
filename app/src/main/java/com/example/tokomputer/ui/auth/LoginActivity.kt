package com.example.tokomputer.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tokomputer.utils.Extras
import com.example.tokomputer.ui.main.MainActivity
import com.example.tokomputer.R
import com.example.tokomputer.data.local.SessionManager

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize SharedPrefManager
        SessionManager.init(this)

        val btnLoginTab = findViewById<Button>(R.id.btnLoginTab)
        val btnRegisterTab = findViewById<Button>(R.id.btnRegisterTab)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegisterNow = findViewById<TextView>(R.id.tvRegisterNow)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        // Support extras from RegisterActivity
        val registeredName = intent.getStringExtra(Extras.REGISTERED_NAME)
        val registeredEmail = intent.getStringExtra(Extras.REGISTERED_EMAIL)
        val prefill = registeredEmail ?: intent.getStringExtra(Extras.PREFILL_EMAIL)
        if (!prefill.isNullOrEmpty()) {
            etEmail.setText(prefill)
        }

        // Contoh interaksi sederhana
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
                // Simpan status login: gunakan registeredName jika tersedia, otherwise keep existing saved name or empty
                val nameToSave = when {
                    !registeredName.isNullOrEmpty() -> registeredName
                    !SessionManager.getName().isNullOrEmpty() -> SessionManager.getName()
                    else -> ""
                }
                SessionManager.setLogin(nameToSave, email)
                // Buka MainActivity dan bersihkan back stack
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }

        tvRegisterNow.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLoginTab.setOnClickListener {
            btnLoginTab.backgroundTintList = getColorStateList(android.R.color.holo_blue_light)
            btnRegisterTab.backgroundTintList = getColorStateList(android.R.color.darker_gray)
        }

        btnRegisterTab.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}