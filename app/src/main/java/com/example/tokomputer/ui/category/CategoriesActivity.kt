package com.example.tokomputer.ui.category

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.tokomputer.utils.Extras
import com.example.tokomputer.ui.auth.LoginActivity
import com.example.tokomputer.ui.main.MainActivity
import com.example.tokomputer.ui.member.MemberActivity
import com.example.tokomputer.R
import com.example.tokomputer.data.local.SessionManager
import com.example.tokomputer.ui.about.AboutActivity
import com.example.tokomputer.ui.order.OrderActivity
import com.google.android.material.navigation.NavigationView

class CategoriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        val drawer = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawerLayoutCat)
        val navView = findViewById<NavigationView>(R.id.navigationViewCat)
        val btnCart = findViewById<ImageButton>(R.id.btnCartCat)

        // Card click listeners
        findViewById<androidx.cardview.widget.CardView>(R.id.cardKomputer).setOnClickListener {
            val i = Intent(this, CategoryListActivity::class.java)
            i.putExtra(Extras.CATEGORY,"komputer")
            startActivity(i)
        }
        findViewById<androidx.cardview.widget.CardView>(R.id.cardLaptop).setOnClickListener {
            // Show brands for laptops first
            val i = Intent(this, BrandListActivity::class.java)
            i.putExtra(Extras.CATEGORY,"laptop")
            startActivity(i)
        }
        findViewById<androidx.cardview.widget.CardView>(R.id.cardKomponen).setOnClickListener {
            // For komponen, show subcategories first
            val i = Intent(this, SubCategoryListActivity::class.java)
            startActivity(i)
        }

        btnCart.setOnClickListener {
            startActivity(Intent(this, OrderActivity::class.java))
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_categories -> {
                    // already on CategoriesActivity; do nothing
                }
                R.id.nav_home -> {
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                }
                R.id.nav_member -> {
                    val i = Intent(this, MemberActivity::class.java)
                    startActivity(i)
                }
                R.id.nav_logout -> {
                    SessionManager.clearLogin()
                    val i = Intent(this, LoginActivity::class.java)
                    startActivity(i)
                }
                R.id.nav_tentang -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                }
            }
            drawer.closeDrawers()
            true
        }
    }
}
