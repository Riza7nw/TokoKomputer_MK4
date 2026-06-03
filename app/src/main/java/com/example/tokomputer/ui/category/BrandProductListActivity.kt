package com.example.tokomputer.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tokomputer.R
import com.example.tokomputer.adapter.ProductAdapter
import com.example.tokomputer.data.local.SessionManager
import com.example.tokomputer.ui.about.AboutActivity
import com.example.tokomputer.ui.auth.LoginActivity
import com.example.tokomputer.ui.history.TransactionHistoryActivity
import com.example.tokomputer.ui.main.MainActivity
import com.example.tokomputer.ui.member.MemberActivity
import com.example.tokomputer.ui.product.ProductDetailActivity
import com.example.tokomputer.utils.Extras
import com.example.tokomputer.utils.Resource
import com.google.android.material.navigation.NavigationView

class BrandProductListActivity : AppCompatActivity() {

    private lateinit var rvProducts: RecyclerView
    private lateinit var tvTitle: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmpty: TextView
    private lateinit var productAdapter: ProductAdapter

    private val viewModel: BrandListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brand_product_list)

        val brand   = intent.getStringExtra(Extras.BRAND) ?: "Brand"
        val drawer  = findViewById<DrawerLayout>(R.id.drawerLayoutBrandList)
        val navView = findViewById<NavigationView>(R.id.navigationViewBrandList)
        val btnMenu = findViewById<ImageButton>(R.id.btnMenuBrandList)

        tvTitle     = findViewById(R.id.tvBrandTitle)
        rvProducts  = findViewById(R.id.rvBrandProducts)
        progressBar = findViewById(R.id.progressBarBrandList)
        tvEmpty     = findViewById(R.id.tvEmptyBrandList)

        tvTitle.text = brand

        btnMenu.setOnClickListener { drawer.openDrawer(navView) }

        productAdapter = ProductAdapter(emptyList()) { product ->
            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                putExtra(Extras.PRODUCT_ID,    product.id)
                putExtra(Extras.PRODUCT_NAME,  product.name)
                putExtra(Extras.PRODUCT_BRAND, product.brand)
                putExtra(Extras.PRODUCT_PRICE, product.price)
                putExtra(Extras.PRODUCT_IMAGE, product.image)
                putExtra(Extras.PRODUCT_DESC,  product.description)
            }
            startActivity(intent)
        }
        rvProducts.layoutManager = GridLayoutManager(this, 2)
        rvProducts.adapter = productAdapter

        viewModel.fetchByBrand(brand)
        observeViewModel()

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home       -> startActivity(Intent(this, MainActivity::class.java))
                R.id.nav_categories -> startActivity(Intent(this, CategoriesActivity::class.java))
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

    private fun observeViewModel() {
        viewModel.productsState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    rvProducts.visibility  = View.GONE
                    tvEmpty.visibility     = View.GONE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    val products = state.data ?: emptyList()
                    if (products.isEmpty()) {
                        rvProducts.visibility = View.GONE
                        tvEmpty.visibility    = View.VISIBLE
                        tvEmpty.text          = "Tidak ada produk untuk brand ini"
                    } else {
                        rvProducts.visibility = View.VISIBLE
                        tvEmpty.visibility    = View.GONE
                        productAdapter.updateData(products)
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    tvEmpty.visibility     = View.VISIBLE
                    tvEmpty.text           = state.message
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}