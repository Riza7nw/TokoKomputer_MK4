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
import com.example.tokomputer.utils.Resource

class RegisterActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var etPhone: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnLoginTab: Button
    private lateinit var btnRegisterTab: Button
    private lateinit var tvLoginNow: TextView
    private lateinit var progressBar: ProgressBar

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initViews()
        observeViewModel()
        setupClickListeners()
    }

    private fun initViews() {
        etName            = findViewById(R.id.etName)
        etEmail           = findViewById(R.id.etEmail)
        etPassword        = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        etPhone           = findViewById(R.id.etPhone)
        btnRegister       = findViewById(R.id.btnRegister)
        btnLoginTab       = findViewById(R.id.btnLoginTab)
        btnRegisterTab    = findViewById(R.id.btnRegisterTab)
        tvLoginNow        = findViewById(R.id.tvLoginNow)
        progressBar       = findViewById(R.id.progressBar)
    }

    private fun setupClickListeners() {

        btnRegister.setOnClickListener {
            val name            = etName.text.toString().trim()
            val email           = etEmail.text.toString().trim()
            val password        = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()
            val phone           = etPhone.text.toString().trim()

            // Validasi lokal sebelum kirim ke server
            if (name.isEmpty()) {
                etName.error = "Nama wajib diisi"
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                etEmail.error = "Email wajib diisi"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                etPassword.error = "Password wajib diisi"
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                etConfirmPassword.error = "Password tidak sama"
                return@setOnClickListener
            }
            if (phone.isEmpty()) {
                etPhone.error = "Nomor HP wajib diisi"
                return@setOnClickListener
            }

            viewModel.register(name, email, password, confirmPassword, phone)
        }

        btnLoginTab.setOnClickListener { goToLogin() }
        tvLoginNow.setOnClickListener  { goToLogin() }
    }

    private fun observeViewModel() {
        viewModel.registerState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    btnRegister.isEnabled  = false
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    btnRegister.isEnabled  = true
                    Toast.makeText(
                        this,
                        "Register berhasil! Cek email untuk OTP",
                        Toast.LENGTH_LONG
                    ).show()
                    goToOtp()
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    btnRegister.isEnabled  = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun goToOtp() {
        val email = etEmail.text.toString().trim()
        val intent = Intent(this, OtpActivity::class.java).apply {
            putExtra("email", email)
        }
        startActivity(intent)
        finish()
    }
}