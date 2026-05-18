package com.example.tokomputer.ui.member

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tokomputer.R
import com.example.tokomputer.model.UserModel
import com.example.tokomputer.ui.auth.LoginActivity
import com.example.tokomputer.ui.main.MainActivity
import com.example.tokomputer.utils.Resource

class MemberActivity : AppCompatActivity() {

    private lateinit var tvAvatar: TextView
    private lateinit var tvMemberName: TextView
    private lateinit var tvMemberEmail: TextView
    private lateinit var tvMemberId: TextView
    private lateinit var tvMemberNameDetail: TextView
    private lateinit var tvMemberEmailDetail: TextView
    private lateinit var tvMemberPhone: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var layoutContent: LinearLayout
    private lateinit var tvError: TextView
    private lateinit var btnLogout: Button
    private lateinit var btnBack: Button

    private val viewModel: MemberViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)

        initViews()
        observeViewModel()
        setupClickListeners()
    }

    private fun initViews() {
        tvAvatar            = findViewById(R.id.tvAvatar)
        tvMemberName        = findViewById(R.id.tvMemberName)
        tvMemberEmail       = findViewById(R.id.tvMemberEmail)
        tvMemberId          = findViewById(R.id.tvMemberId)
        tvMemberNameDetail  = findViewById(R.id.tvMemberNameDetail)
        tvMemberEmailDetail = findViewById(R.id.tvMemberEmailDetail)
        tvMemberPhone       = findViewById(R.id.tvMemberPhone)
        progressBar         = findViewById(R.id.progressBar)
        layoutContent       = findViewById(R.id.layoutContent)
        tvError             = findViewById(R.id.tvError)
        btnLogout           = findViewById(R.id.btnLogout)
        btnBack             = findViewById(R.id.btnBack)
    }

    private fun setupClickListeners() {

        btnBack.setOnClickListener {
            finish()
        }

        btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun observeViewModel() {

        // Observe profile
        viewModel.profileState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    progressBar.visibility  = View.VISIBLE
                    layoutContent.visibility = View.GONE
                    tvError.visibility      = View.GONE
                }
                is Resource.Success -> {
                    progressBar.visibility   = View.GONE
                    layoutContent.visibility = View.VISIBLE
                    tvError.visibility       = View.GONE
                    state.data?.let { bindProfile(it) }
                }
                is Resource.Error -> {
                    progressBar.visibility   = View.GONE
                    layoutContent.visibility = View.GONE
                    tvError.visibility       = View.VISIBLE
                    tvError.text             = state.message

                    // Kalau 401 → token expired → paksa login ulang
                    if (state.message?.contains("401") == true) {
                        Toast.makeText(
                            this,
                            "Sesi habis, silakan login ulang",
                            Toast.LENGTH_SHORT
                        ).show()
                        goToLogin()
                    }
                }
            }
        }

        // Observe logout
        viewModel.logoutState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    btnLogout.isEnabled = false
                    btnLogout.text      = "Loading..."
                }
                is Resource.Success -> {
                    Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show()
                    goToLogin()
                }
                is Resource.Error -> {
                    // Tetap logout lokal meski server error
                    Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show()
                    goToLogin()
                }
            }
        }
    }

    private fun bindProfile(user: UserModel) {
        // Avatar → huruf pertama dari nama
        val initial = user.name.firstOrNull()?.uppercaseChar()?.toString() ?: "?"
        tvAvatar.text           = initial
        tvMemberName.text       = user.name
        tvMemberEmail.text      = user.email
        tvMemberId.text         = "#${user.id}"
        tvMemberNameDetail.text = user.name
        tvMemberEmailDetail.text = user.email
        tvMemberPhone.text      = user.phone ?: "-"
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}