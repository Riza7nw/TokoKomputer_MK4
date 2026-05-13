package com.example.tokomputer.ui.category

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tokomputer.utils.Extras
import com.example.tokomputer.ui.auth.LoginActivity
import com.example.tokomputer.ui.main.MainActivity
import com.example.tokomputer.ui.member.MemberActivity
import com.example.tokomputer.R
import com.example.tokomputer.data.local.SessionManager
import com.example.tokomputer.adapter.ProductAdapter
import com.example.tokomputer.data.repository.DataRepository
import com.example.tokomputer.ui.about.AboutActivity
import com.example.tokomputer.ui.product.ProductDetailActivity
import com.google.android.material.navigation.NavigationView

class CategoryListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_list)

        val category = intent.getStringExtra(Extras.CATEGORY) ?: ""
        val subcategory = intent.getStringExtra(Extras.SUBCATEGORY)
        val brand = intent.getStringExtra(Extras.BRAND)
        val tvTitle = findViewById<TextView>(R.id.tvCategoryTitle)
        val rv = findViewById<RecyclerView>(R.id.rvCategoryProducts)
        val drawer = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawerLayoutList)
        val navView = findViewById<NavigationView>(R.id.navigationViewList)
        val btnMenu2 = findViewById<android.widget.ImageButton>(R.id.btnMenu2)

        btnMenu2.setOnClickListener {
            drawer.openDrawer(navView)
        }

        tvTitle.text = when {
            !brand.isNullOrBlank() -> brand
            !subcategory.isNullOrBlank() -> subcategory
            category == "komputer" -> "Komputer"
            category == "laptop" -> "Laptop"
            category == "komponen" -> "Komponen"
            else -> "Produk"
        }

        rv.layoutManager = GridLayoutManager(this, 2)
        val products = DataRepository.getProducts(category.ifBlank { null }, subcategory, brand)
        rv.adapter = ProductAdapter(products) { product ->
            val i = Intent(this, ProductDetailActivity::class.java)
            i.putExtra(Extras.PRODUCT_ID, product.id)
            i.putExtra(Extras.PRODUCT_NAME, product.name)
            i.putExtra(Extras.PRODUCT_PRICE, product.price)
            i.putExtra(Extras.PRODUCT_IMAGE, product.image)      // ← imageRes → image
            product.specsText.takeIf { it.isNotEmpty() }?.let { i.putExtra(Extras.PRODUCT_SPECS_RES_ID, it) }
            product.specsResId?.let { i.putExtra(Extras.PRODUCT_SPECS_RES_ID, it) }
            product.specsResId?.let { i.putExtra(Extras.PRODUCT_SPECS_RES_ID, it) }
            startActivity(i)
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> startActivity(Intent(this, MainActivity::class.java))
                R.id.nav_categories -> startActivity(Intent(this, CategoriesActivity::class.java))
                R.id.nav_member -> startActivity(Intent(this, MemberActivity::class.java))
                R.id.nav_logout -> {
                    SessionManager.clearLogin()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                R.id.nav_tentang -> startActivity(Intent(this, AboutActivity::class.java))
            }
            drawer.closeDrawers()
            true
        }
    }
}