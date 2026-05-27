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
import com.example.tokomputer.ui.main.MainActivity
import com.example.tokomputer.ui.member.MemberActivity
import com.example.tokomputer.ui.order.OrderActivity
import com.example.tokomputer.utils.Extras
import com.example.tokomputer.utils.Resource
import com.google.android.material.navigation.NavigationView

class BrandsActivity : AppCompatActivity() {

    private val viewModel: BrandsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brands)

        val drawer      = findViewById<DrawerLayout>(R.id.drawerLayoutBrands)
        val navView     = findViewById<NavigationView>(R.id.navigationViewBrands)
        val btnMenu     = findViewById<ImageButton>(R.id.btnMenuBrands)
        val btnCart     = findViewById<ImageButton>(R.id.btnCartBrands)
        val container   = findViewById<LinearLayout>(R.id.brandContainer)
        val progressBar = findViewById<ProgressBar>(R.id.progressBarBrands)
        val tvEmpty     = findViewById<TextView>(R.id.tvEmptyBrands)

        btnMenu.setOnClickListener { drawer.openDrawer(navView) }

        btnCart.setOnClickListener {
            if (SessionManager.isLoggedIn()) {
                startActivity(Intent(this, OrderActivity::class.java))
            } else {
                Toast.makeText(this, "Login dulu", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        viewModel.brandsState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    container.visibility   = View.GONE
                    tvEmpty.visibility     = View.GONE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    val brands = state.data ?: emptyList()
                    if (brands.isEmpty()) {
                        tvEmpty.visibility   = View.VISIBLE
                        container.visibility = View.GONE
                    } else {
                        tvEmpty.visibility   = View.GONE
                        container.visibility = View.VISIBLE
                        buildBrandCards(container, brands)
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    tvEmpty.visibility     = View.VISIBLE
                    tvEmpty.text           = state.message
                }
            }
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home       -> startActivity(Intent(this, MainActivity::class.java))
                R.id.nav_categories -> startActivity(Intent(this, CategoriesActivity::class.java))
                R.id.nav_brands     -> { /* sudah di sini */ }
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

    private fun buildBrandCards(container: LinearLayout, brands: List<String>) {
        container.removeAllViews()
        brands.forEach { brand ->
            val card = layoutInflater.inflate(R.layout.item_category_card, container, false)
            card.findViewById<TextView>(R.id.tvCategoryName).text = brand
            card.setOnClickListener {
                val intent = Intent(this, BrandProductListActivity::class.java)
                intent.putExtra(Extras.BRAND, brand)
                startActivity(intent)
            }
            container.addView(card)
        }
    }
}