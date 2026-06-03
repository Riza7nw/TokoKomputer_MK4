package com.example.tokomputer.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tokomputer.R
import com.example.tokomputer.data.local.SessionManager
import com.example.tokomputer.ui.about.AboutActivity
import com.example.tokomputer.ui.auth.LoginActivity
import com.example.tokomputer.ui.category.BrandsActivity
import com.example.tokomputer.ui.category.CategoriesActivity
import com.example.tokomputer.ui.main.MainActivity
import com.example.tokomputer.ui.member.MemberActivity
import com.example.tokomputer.utils.Resource
import com.google.android.material.navigation.NavigationView

class TransactionHistoryActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var rvTransactions: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmpty: TextView

    private lateinit var adapter: TransactionHistoryAdapter
    private val viewModel: TransactionHistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions_history)

        initViews()
        setupDrawer()
        setupRecyclerView()
        observeViewModel()
    }

    private fun initViews() {
        drawerLayout   = findViewById(R.id.drawerLayoutList)
        navigationView = findViewById(R.id.navigationViewList)
        rvTransactions = findViewById(R.id.rvCategoryProducts)
        progressBar    = findViewById(R.id.progressBar)
        tvEmpty        = findViewById(R.id.tvEmpty)

        // Tombol hamburger buka drawer
        findViewById<android.widget.ImageButton>(R.id.btnMenu2).setOnClickListener {
            drawerLayout.openDrawer(navigationView)
        }
    }

    private fun setupDrawer() {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home       -> startActivity(Intent(this, MainActivity::class.java))
                R.id.nav_categories -> startActivity(Intent(this, CategoriesActivity::class.java))
                R.id.nav_brands     -> startActivity(Intent(this, BrandsActivity::class.java))
                R.id.nav_history    -> { /* Sudah di halaman ini */ }
                R.id.nav_member     -> {
                    if (SessionManager.isLoggedIn())
                        startActivity(Intent(this, MemberActivity::class.java))
                    else
                        startActivity(Intent(this, LoginActivity::class.java))
                }
                R.id.nav_tentang -> startActivity(Intent(this, AboutActivity::class.java))
                R.id.nav_logout  -> {
                    SessionManager.clearLogin()
                    Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun setupRecyclerView() {
        adapter = TransactionHistoryAdapter(
            items       = emptyList(),
            onItemClick = { transaction ->
                // Opsional: navigate ke detail transaksi
                Toast.makeText(
                    this,
                    "Transaksi #${transaction.id}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
        rvTransactions.layoutManager = LinearLayoutManager(this)
        rvTransactions.adapter       = adapter
    }

    private fun observeViewModel() {
        viewModel.transactionsState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    progressBar.visibility    = View.VISIBLE
                    rvTransactions.visibility = View.GONE
                    tvEmpty.visibility        = View.GONE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    val data = state.data
                    if (data.isNullOrEmpty()) {
                        rvTransactions.visibility = View.GONE
                        tvEmpty.visibility        = View.VISIBLE
                        tvEmpty.text              = "Belum ada transaksi"
                    } else {
                        rvTransactions.visibility = View.VISIBLE
                        tvEmpty.visibility        = View.GONE
                        adapter.updateData(data)
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility    = View.GONE
                    rvTransactions.visibility = View.GONE
                    tvEmpty.visibility        = View.VISIBLE
                    tvEmpty.text              = state.message ?: "Terjadi kesalahan"
                }
            }
        }
    }
}