package com.example.tokomputer.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tokomputer.R
import com.example.tokomputer.data.local.SessionManager
import com.example.tokomputer.ui.main.MainActivity
import com.example.tokomputer.utils.Resource

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnLoginTab: Button
    private lateinit var btnRegisterTab: Button
    private lateinit var tvRegisterNow: TextView
    private lateinit var tvForgotPassword: TextView
    private lateinit var progressBar: ProgressBar

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Kalau sudah login → langsung ke MainActivity
        if (SessionManager.isLoggedIn()) {
            goToMain()
            return
        }

        setContentView(R.layout.activity_login)

        initViews()
        observeViewModel()
        setupClickListeners()
    }

    private fun initViews() {
        etEmail          = findViewById(R.id.etEmail)
        etPassword       = findViewById(R.id.etPassword)
        btnLogin         = findViewById(R.id.btnLogin)
        btnLoginTab      = findViewById(R.id.btnLoginTab)
        btnRegisterTab   = findViewById(R.id.btnRegisterTab)
        tvRegisterNow    = findViewById(R.id.tvRegisterNow)
        tvForgotPassword = findViewById(R.id.tvForgotPassword)

        // Tambahkan ProgressBar ke layout (lihat catatan XML di bawah)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun setupClickListeners() {

        // Tombol Login
        btnLogin.setOnClickListener {
            val email    = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            viewModel.login(email, password)
        }

        // Tab → pindah ke Register
        btnRegisterTab.setOnClickListener {
            goToRegister()
        }

        // Link → pindah ke Register
        tvRegisterNow.setOnClickListener {
            goToRegister()
        }

        // Lupa password (opsional, bisa diisi nanti)
        tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Fitur belum tersedia", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    btnLogin.isEnabled     = false
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    btnLogin.isEnabled     = true
                    Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
                    goToMain()
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    btnLogin.isEnabled     = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun goToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}