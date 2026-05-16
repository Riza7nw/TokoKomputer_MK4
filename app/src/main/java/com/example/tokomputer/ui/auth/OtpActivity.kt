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

class OtpActivity : AppCompatActivity() {

    private lateinit var tvEmailInfo: TextView
    private lateinit var tvTimer: TextView
    private lateinit var tvResendOtp: TextView
    private lateinit var tvBackToLogin: TextView
    private lateinit var etOtp: EditText
    private lateinit var btnVerify: Button
    private lateinit var progressBar: ProgressBar

    private val viewModel: OtpViewModel by viewModels()

    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        // Ambil email dari intent
        email = intent.getStringExtra("email") ?: ""

        initViews()
        observeViewModel()
        setupClickListeners()

        // Mulai timer saat activity dibuka
        viewModel.startTimer()
    }

    private fun initViews() {
        tvEmailInfo  = findViewById(R.id.tvEmailInfo)
        tvTimer      = findViewById(R.id.tvTimer)
        tvResendOtp  = findViewById(R.id.tvResendOtp)
        tvBackToLogin = findViewById(R.id.tvBackToLogin)
        etOtp        = findViewById(R.id.etOtp)
        btnVerify    = findViewById(R.id.btnVerify)
        progressBar  = findViewById(R.id.progressBar)

        // Tampilkan email user
        tvEmailInfo.text = "Kode OTP telah dikirim ke\n$email"
    }

    private fun setupClickListeners() {

        // Tombol Verifikasi
        btnVerify.setOnClickListener {
            val otp = etOtp.text.toString().trim()
            viewModel.verifyOtp(email, otp)
        }

        // Kirim ulang OTP
        tvResendOtp.setOnClickListener {
            if (viewModel.isTimerRunning.value == true) {
                Toast.makeText(
                    this,
                    "Tunggu hingga timer habis",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.resendOtp(email)
                Toast.makeText(
                    this,
                    "OTP dikirim ulang ke $email",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Kembali ke Login
        tvBackToLogin.setOnClickListener {
            goToLogin()
        }
    }

    private fun observeViewModel() {

        // Observe verify OTP
        viewModel.otpState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    btnVerify.isEnabled    = false
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    btnVerify.isEnabled    = true
                    Toast.makeText(
                        this,
                        "Email berhasil diverifikasi!",
                        Toast.LENGTH_SHORT
                    ).show()
                    goToLogin()
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    btnVerify.isEnabled    = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        // Observe timer
        viewModel.timerSeconds.observe(this) { seconds ->
            val menit  = seconds / 60
            val detik  = seconds % 60
            tvTimer.text = "Kode berlaku selama %02d:%02d".format(menit, detik)
        }

        // Observe timer running — ubah warna resend
        viewModel.isTimerRunning.observe(this) { isRunning ->
            tvResendOtp.setTextColor(
                if (isRunning) {
                    resources.getColor(android.R.color.darker_gray, null)
                } else {
                    resources.getColor(android.R.color.holo_blue_light, null)
                }
            )
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}