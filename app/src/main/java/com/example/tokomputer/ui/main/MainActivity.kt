package com.example.tokomputer.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tokomputer.R
import com.example.tokomputer.adapter.ProductAdapter
import com.example.tokomputer.data.local.SessionManager
import com.example.tokomputer.model.ProductModel
import com.example.tokomputer.ui.about.AboutActivity
import com.example.tokomputer.ui.auth.LoginActivity
import com.example.tokomputer.ui.category.CategoriesActivity
import com.example.tokomputer.ui.member.MemberActivity
import com.example.tokomputer.ui.order.OrderActivity
import com.example.tokomputer.ui.product.ProductDetailActivity
import com.example.tokomputer.utils.Resource
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var rvProducts: RecyclerView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var btnMenu: ImageButton
    private lateinit var btnCart: ImageButton
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmpty: TextView

    private lateinit var productAdapter: ProductAdapter
    private val viewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SessionManager.init(this)
        setContentView(R.layout.activity_main)

        initViews()
        setupRecyclerView()
        observeViewModel()
        setupNavigation()
    }

    private fun initViews() {
        rvProducts   = findViewById(R.id.rvProducts)
        drawerLayout = findViewById(R.id.drawerLayout)
        navView      = findViewById(R.id.navigationView)
        btnMenu      = findViewById(R.id.btnMenu3)
        btnCart      = findViewById(R.id.btnCart)
        progressBar  = findViewById(R.id.progressBar)
        tvEmpty      = findViewById(R.id.tvEmpty)
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(emptyList()) { product ->
            goToProductDetail(product)
        }
        rvProducts.layoutManager = GridLayoutManager(this, 2)
        rvProducts.setHasFixedSize(true)
        rvProducts.adapter = productAdapter
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
                    } else {
                        rvProducts.visibility = View.VISIBLE
                        tvEmpty.visibility    = View.GONE
                        productAdapter.updateData(products)
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    rvProducts.visibility  = View.GONE
                    tvEmpty.visibility     = View.VISIBLE
                    tvEmpty.text           = state.message
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setupNavigation() {

        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(navView)
        }

        btnCart.setOnClickListener {
            if (SessionManager.isLoggedIn()) {
                startActivity(Intent(this, OrderActivity::class.java))
            } else {
                Toast.makeText(this, "Login dulu untuk melihat keranjang", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    drawerLayout.closeDrawers()
                }
                R.id.nav_member -> {
                    if (SessionManager.isLoggedIn()) {
                        startActivity(Intent(this, MemberActivity::class.java))
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                }
                R.id.nav_categories -> {
                    startActivity(Intent(this, CategoriesActivity::class.java))
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
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun goToProductDetail(product: ProductModel) {
        val intent = Intent(this, ProductDetailActivity::class.java).apply {
            putExtra("product_id",    product.id)
            putExtra("product_name",  product.name)
            putExtra("product_price", product.price)
            putExtra("product_image", product.image)
            putExtra("product_desc",  product.description)
        }
        startActivity(intent)
    }
}