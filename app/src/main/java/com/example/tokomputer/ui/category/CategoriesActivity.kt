package com.example.tokomputer.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.tokomputer.R
import com.example.tokomputer.data.local.SessionManager
import com.example.tokomputer.ui.about.AboutActivity
import com.example.tokomputer.ui.auth.LoginActivity
import com.example.tokomputer.ui.history.TransactionHistoryActivity
import com.example.tokomputer.ui.main.MainActivity
import com.example.tokomputer.ui.member.MemberActivity
import com.example.tokomputer.ui.order.OrderActivity
import com.example.tokomputer.utils.Extras
import com.example.tokomputer.utils.Resource
import com.google.android.material.navigation.NavigationView

class CategoriesActivity : AppCompatActivity() {

    private val viewModel: CategoriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        val drawer      = findViewById<DrawerLayout>(R.id.drawerLayoutCat)
        val navView     = findViewById<NavigationView>(R.id.navigationViewCat)
        val btnMenu     = findViewById<ImageButton>(R.id.btnMenu3)
        val btnCart     = findViewById<ImageButton>(R.id.btnCartCat)
        val container   = findViewById<LinearLayout>(R.id.categoryContainer)
        val progressBar = findViewById<ProgressBar>(R.id.progressBarCat)

        val layoutEmpty = findViewById<LinearLayout>(R.id.layoutEmptyCat)
        val tvEmptyMsg  = findViewById<TextView>(R.id.tvEmptyMsgCat)

        btnMenu.setOnClickListener { drawer.openDrawer(navView) }

        btnCart.setOnClickListener {
            if (SessionManager.isLoggedIn()) {
                startActivity(Intent(this, OrderActivity::class.java))
            } else {
                Toast.makeText(this, "Login dulu", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        viewModel.categoriesState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    container.visibility   = View.GONE
                    layoutEmpty.visibility = View.GONE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    val categories = state.data ?: emptyList()
                    if (categories.isEmpty()) {
                        layoutEmpty.visibility = View.VISIBLE
                        tvEmptyMsg.text        = "Tidak ada kategori"
                        container.visibility   = View.GONE
                    } else {
                        layoutEmpty.visibility = View.GONE
                        container.visibility   = View.VISIBLE
                        buildCategoryCards(container, categories)
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    layoutEmpty.visibility = View.VISIBLE
                    tvEmptyMsg.text        = state.message
                }
            }
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home       -> startActivity(Intent(this, MainActivity::class.java))
                R.id.nav_categories -> { /* sudah di sini */ }
                R.id.nav_brands     -> startActivity(Intent(this, BrandsActivity::class.java))
                R.id.nav_history    -> {
                    if (SessionManager.isLoggedIn()) {
                        startActivity(Intent(this, TransactionHistoryActivity::class.java))
                    } else {
                        Toast.makeText(this, "Login dulu untuk melihat history", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                }
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
            drawer.closeDrawers()
            true
        }
    }

    private fun buildCategoryCards(container: LinearLayout, categories: List<String>) {
        container.removeAllViews()
        categories.forEach { category ->
            val card = layoutInflater.inflate(R.layout.item_category_card, container, false)
            card.findViewById<TextView>(R.id.tvCategoryName).text = category
            card.setOnClickListener {
                val intent = Intent(this, CategoryListActivity::class.java)
                intent.putExtra(Extras.CATEGORY, category)
                startActivity(intent)
            }
            container.addView(card)
        }
    }
}