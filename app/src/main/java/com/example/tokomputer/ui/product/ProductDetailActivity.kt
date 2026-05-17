package com.example.tokomputer.ui.product

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tokomputer.R
import com.example.tokomputer.data.local.SessionManager
import com.example.tokomputer.ui.auth.LoginActivity
import com.example.tokomputer.ui.order.OrderActivity

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var imgProduct: ImageView
    private lateinit var tvProductName: TextView
    private lateinit var tvProductPrice: TextView
    private lateinit var tvProductDesc: TextView
    private lateinit var tvProductSpecs: TextView
    private lateinit var btnBuyNow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        initViews()
        loadProductData()
    }

    private fun initViews() {
        imgProduct     = findViewById(R.id.imgProduct)
        tvProductName  = findViewById(R.id.tvProductName)
        tvProductPrice = findViewById(R.id.tvProductPrice)
        tvProductDesc  = findViewById(R.id.tvProductDesc)
        tvProductSpecs = findViewById(R.id.tvProductSpecs)
        btnBuyNow      = findViewById(R.id.btnBuyNow)
    }

    private fun loadProductData() {
        // Ambil data dari intent
        val productId    = intent.getIntExtra("product_id", 0)
        val productName  = intent.getStringExtra("product_name") ?: "-"
        val productPrice = intent.getDoubleExtra("product_price", 0.0)
        val productImage = intent.getStringExtra("product_image")
        val productDesc  = intent.getStringExtra("product_desc") ?: "-"

        // Set data ke UI
        tvProductName.text  = productName
        tvProductPrice.text = "Rp ${String.format("%,.0f", productPrice)}"
        tvProductDesc.text  = productDesc
        tvProductSpecs.text = "" // kosong dulu, bisa diisi dari API nanti

        // Load gambar
        Glide.with(this)
            .load(productImage)
            .placeholder(R.drawable.ic_computer)
            .error(R.drawable.ic_computer)
            .centerCrop()
            .into(imgProduct)

        // Tombol beli
        btnBuyNow.setOnClickListener {
            if (SessionManager.isLoggedIn()) {
                val intent = Intent(this, OrderActivity::class.java).apply {
                    putExtra("product_id",    productId)
                    putExtra("product_name",  productName)
                    putExtra("product_price", productPrice)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Login dulu untuk membeli", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}