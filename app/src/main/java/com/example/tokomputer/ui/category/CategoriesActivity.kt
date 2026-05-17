package com.example.tokomputer.ui.category

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.cardview.widget.CardView
import com.example.tokomputer.R
import com.example.tokomputer.data.local.SessionManager
import com.example.tokomputer.ui.about.AboutActivity
import com.example.tokomputer.ui.auth.LoginActivity
import com.example.tokomputer.ui.main.MainActivity
import com.example.tokomputer.ui.member.MemberActivity
import com.example.tokomputer.ui.order.OrderActivity
import com.example.tokomputer.utils.Extras
import com.google.android.material.navigation.NavigationView

class CategoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        val drawer  = findViewById<DrawerLayout>(R.id.drawerLayoutCat)
        val navView = findViewById<NavigationView>(R.id.navigationViewCat)
        val btnMenu = findViewById<ImageButton>(R.id.btnMenu3)
        val btnCart = findViewById<ImageButton>(R.id.btnCartCat)

        // Buka drawer
        btnMenu.setOnClickListener {
            drawer.openDrawer(navView)
        }

        // Cart
        btnCart.setOnClickListener {
            if (SessionManager.isLoggedIn()) {
                startActivity(Intent(this, OrderActivity::class.java))
            } else {
                Toast.makeText(this, "Login dulu", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        // Komputer → langsung ke list produk
        findViewById<CardView>(R.id.cardKomputer).setOnClickListener {
            startCategoryList("Komputer")
        }

        // Laptop → langsung ke list produk
        findViewById<CardView>(R.id.cardLaptop).setOnClickListener {
            startCategoryList("Laptop")
        }

        // Komponen → langsung ke list produk
        findViewById<CardView>(R.id.cardKomponen).setOnClickListener {
            startCategoryList("Komponen")
        }

        // Navigation drawer
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                R.id.nav_categories -> {
                    // sudah di sini, tutup drawer saja
                }
                R.id.nav_member -> {
                    if (SessionManager.isLoggedIn()) {
                        startActivity(Intent(this, MemberActivity::class.java))
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                }
                R.id.nav_tentang -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                }
                R.id.nav_logout -> {
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

    private fun startCategoryList(category: String) {
        val intent = Intent(this, CategoryListActivity::class.java)
        intent.putExtra(Extras.CATEGORY, category)
        startActivity(intent)
    }
}