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

    private lateinit var etOtp: EditText
    private lateinit var btnVerify: Button
    private lateinit var btnResendOtp: Button
    private lateinit var tvTimer: TextView
    private lateinit var tvResendInfo: TextView
    private lateinit var tvOtpSubtitle: TextView
    private lateinit var progressBar: ProgressBar

    private val viewModel: OtpViewModel by viewModels()
    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        email = intent.getStringExtra("email") ?: ""

        initViews()
        observeViewModel()
        setupClickListeners()
    }

    private fun initViews() {
        etOtp        = findViewById(R.id.etOtp)
        btnVerify    = findViewById(R.id.btnVerify)
        btnResendOtp = findViewById(R.id.btnResendOtp)
        tvTimer      = findViewById(R.id.tvTimer)
        tvResendInfo = findViewById(R.id.tvResendInfo)
        tvOtpSubtitle = findViewById(R.id.tvOtpSubtitle)
        progressBar  = findViewById(R.id.progressBar)

        tvOtpSubtitle.text = "Kode OTP telah dikirim ke\n$email"
    }

    private fun setupClickListeners() {

        btnVerify.setOnClickListener {
            val otp = etOtp.text.toString().trim()
            viewModel.verifyOtp(email, otp)
        }

        btnResendOtp.setOnClickListener {
            viewModel.resendOtp(email)
        }
    }

    private fun observeViewModel() {

        // Observe timer
        viewModel.timerSeconds.observe(this) { seconds ->
            val menit = seconds / 60
            val detik = seconds % 60
            tvTimer.text = String.format("%02d:%02d", menit, detik)
        }

        // Observe apakah timer masih jalan
        viewModel.isTimerRunning.observe(this) { isRunning ->
            if (isRunning) {
                // Timer masih jalan — tombol resend disable
                btnResendOtp.isEnabled = false
                btnResendOtp.backgroundTintList =
                    android.content.res.ColorStateList.valueOf(
                        android.graphics.Color.parseColor("#E0E0E0")
                    )
                tvResendInfo.text    = "Tombol aktif setelah timer habis"
                tvTimer.setTextColor(android.graphics.Color.parseColor("#1C3C58"))
            } else {
                // Timer habis — tombol resend aktif
                btnResendOtp.isEnabled = true
                btnResendOtp.backgroundTintList =
                    android.content.res.ColorStateList.valueOf(
                        android.graphics.Color.parseColor("#90CAF9")
                    )
                btnResendOtp.setTextColor(android.graphics.Color.WHITE)
                tvResendInfo.text    = "Kode OTP sudah kadaluarsa"
                tvTimer.text         = "Expired"
                tvTimer.setTextColor(android.graphics.Color.parseColor("#E53935"))
            }
        }

        // Observe verify state
        viewModel.verifyState.observe(this) { state ->
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
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    btnVerify.isEnabled    = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        // Observe resend state
        viewModel.resendState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    btnResendOtp.isEnabled = false
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "OTP baru telah dikirim ke $email",
                        Toast.LENGTH_LONG
                    ).show()
                    // Timer otomatis reset di ViewModel
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    btnResendOtp.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}