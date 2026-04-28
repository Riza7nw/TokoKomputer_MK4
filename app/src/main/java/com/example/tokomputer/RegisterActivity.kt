package com.example.tokomputer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize SharedPrefManager
        SharedPrefManager.init(this)

        val btnLoginTab = findViewById<Button>(R.id.btnLoginTab)
        val btnRegisterTab = findViewById<Button>(R.id.btnRegisterTab)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvLoginNow = findViewById<TextView>(R.id.tvLoginNow)

        btnRegister.setOnClickListener {
            val name = findViewById<EditText>(R.id.etName).text.toString()
            val email = findViewById<EditText>(R.id.etEmail).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            val confirmPassword = findViewById<EditText>(R.id.etConfirmPassword).text.toString()

            when {
                name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ->
                    Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                password != confirmPassword ->
                    Toast.makeText(this, "Password tidak cocok", Toast.LENGTH_SHORT).show()
                else -> {
                    Toast.makeText(this, "Registrasi berhasil! Silakan login.", Toast.LENGTH_SHORT).show()
                    // Jangan otomatis login. Arahkan ke LoginActivity agar user memasukkan email & password.
                    val intent = Intent(this, LoginActivity::class.java)
                    // Kirim data pendaftaran untuk prefill (opsional)
                    intent.putExtra(Extras.REGISTERED_NAME, name)
                    intent.putExtra(Extras.REGISTERED_EMAIL, email)
                    // jangan kirim password jika tidak perlu; jika ingin prefilling, bisa ditambahkan:
                    // intent.putExtra("registered_password", password)
                    startActivity(intent)
                    finish()
                }
            }
        }

        // Tab navigasi
        btnLoginTab.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnRegisterTab.setOnClickListener {
            // Sudah di halaman daftar, tidak melakukan apa-apa
        }

        tvLoginNow.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}