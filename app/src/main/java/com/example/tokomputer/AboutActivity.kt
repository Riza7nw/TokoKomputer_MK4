package com.example.tokomputer

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val drawer = findViewById<androidx.drawerlayout.widget.DrawerLayout?>(R.id.drawerLayoutAbout)
        val navView = findViewById<NavigationView?>(R.id.navigationViewAbout)

        // menu button (open drawer)
        val btnMenu = findViewById<ImageButton?>(R.id.btnMenuAbout)
        btnMenu?.setOnClickListener {
            drawer?.openDrawer(GravityCompat.START)
        }

        // cart button

        // navigation menu handler
        navView?.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> startActivity(Intent(this, MainActivity::class.java))
                R.id.nav_komputer -> {
                    val i = Intent(this, CategoryListActivity::class.java)
                    i.putExtra("category", "komputer")
                    startActivity(i)
                }
                R.id.nav_laptop -> {
                    val i = Intent(this, CategoryListActivity::class.java)
                    i.putExtra("category", "laptop")
                    startActivity(i)
                }
                R.id.nav_komponen -> {
                    val i = Intent(this, CategoryListActivity::class.java)
                    i.putExtra("category", "komponen")
                    startActivity(i)
                }
                R.id.nav_member -> startActivity(Intent(this, MemberActivity::class.java))
                R.id.nav_tentang -> {
                    // already on AboutActivity
                }
                R.id.nav_logout -> {
                    SharedPrefManager.clearLogin()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
            drawer?.closeDrawers()
            true
        }
    }
}
