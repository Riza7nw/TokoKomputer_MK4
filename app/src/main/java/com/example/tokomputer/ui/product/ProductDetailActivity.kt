package com.example.tokomputer.ui.product

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tokomputer.R
import com.example.tokomputer.data.local.SessionManager
import com.example.tokomputer.model.CartItem
import com.example.tokomputer.ui.auth.LoginActivity
import com.example.tokomputer.ui.order.OrderActivity
import com.example.tokomputer.ui.order.OrderViewModel
import com.example.tokomputer.utils.Extras

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var imgProduct: ImageView
    private lateinit var tvProductName: TextView
    private lateinit var tvProductBrand: TextView
    private lateinit var tvProductPrice: TextView
    private lateinit var tvProductDesc: TextView
    private lateinit var btnBuyNow: Button
    private lateinit var btnAddToCart: Button
    private lateinit var btnBack: ImageButton

    private var productId    = 0
    private var productName  = ""
    private var productPrice = 0.0
    private var productImage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        initViews()
        loadProductData()
    }

    private fun initViews() {
        imgProduct     = findViewById(R.id.imgProduct)
        tvProductName  = findViewById(R.id.tvProductName)
        tvProductBrand = findViewById(R.id.tvProductBrand)
        tvProductPrice = findViewById(R.id.tvProductPrice)
        tvProductDesc  = findViewById(R.id.tvProductDesc)
        btnBuyNow      = findViewById(R.id.btnBuyNow)
        btnAddToCart   = findViewById(R.id.btnAddToCart)
        btnBack        = findViewById(R.id.btnBack)
    }

    private fun loadProductData() {
        productId    = intent.getIntExtra(Extras.PRODUCT_ID, 0)
        productName  = intent.getStringExtra(Extras.PRODUCT_NAME) ?: "-"
        productPrice = intent.getDoubleExtra(Extras.PRODUCT_PRICE, 0.0)
        productImage = intent.getStringExtra(Extras.PRODUCT_IMAGE)
        val productDesc = intent.getStringExtra(Extras.PRODUCT_DESC)
            ?: "Tidak ada deskripsi tersedia"

        val productBrand = intent.getStringExtra(Extras.PRODUCT_BRAND)

        tvProductBrand.text = if (!productBrand.isNullOrEmpty()) "Brand: $productBrand" else ""
        tvProductName.text  = productName
        tvProductPrice.text = "Rp ${String.format("%,.0f", productPrice)}"
        tvProductDesc.text  = productDesc

        Glide.with(this)
            .load(productImage)
            .placeholder(R.drawable.ic_computer)
            .error(R.drawable.ic_computer)
            .centerCrop()
            .into(imgProduct)

        btnBack.setOnClickListener { finish() }

        // Add to Cart — STAY di halaman ini
        btnAddToCart.setOnClickListener {
            if (!SessionManager.isLoggedIn()) {
                Toast.makeText(this, "Login dulu untuk menambah ke keranjang", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                return@setOnClickListener
            }
            OrderViewModel.addToStaticCart(
                CartItem(
                    productId    = productId,
                    productName  = productName,
                    productImage = productImage,
                    price        = productPrice
                )
            )
            Toast.makeText(this, "✓ $productName ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
        }

        // Beli Sekarang — langsung ke OrderActivity
        btnBuyNow.setOnClickListener {
            if (!SessionManager.isLoggedIn()) {
                Toast.makeText(this, "Login dulu untuk membeli", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                return@setOnClickListener
            }
            val intent = Intent(this, OrderActivity::class.java).apply {
                putExtra(Extras.PRODUCT_ID,    productId)
                putExtra(Extras.PRODUCT_NAME,  productName)
                putExtra(Extras.PRODUCT_PRICE, productPrice)
                putExtra(Extras.PRODUCT_IMAGE, productImage)
            }
            startActivity(intent)
        }
    }
}