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
import com.example.tokomputer.utils.Extras

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
        val productId    = intent.getIntExtra(Extras.PRODUCT_ID, 0)
        val productName  = intent.getStringExtra(Extras.PRODUCT_NAME) ?: "-"
        val productPrice = intent.getDoubleExtra(Extras.PRODUCT_PRICE, 0.0)
        val productImage = intent.getStringExtra(Extras.PRODUCT_IMAGE)
        val productDesc  = intent.getStringExtra(Extras.PRODUCT_DESC)
            ?: "Tidak ada deskripsi tersedia"

        tvProductName.text  = productName
        tvProductPrice.text = "Rp ${String.format("%,.0f", productPrice)}"
        tvProductDesc.text  = productDesc
        tvProductSpecs.text = ""

        Glide.with(this)
            .load(productImage)
            .placeholder(R.drawable.ic_computer)
            .error(R.drawable.ic_computer)
            .centerCrop()
            .into(imgProduct)

        btnBuyNow.setOnClickListener {
            if (!SessionManager.isLoggedIn()) {
                Toast.makeText(this, "Login dulu untuk membeli", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                return@setOnClickListener
            }
            // Kirim semua data termasuk image ke OrderActivity
            val intent = Intent(this, OrderActivity::class.java).apply {
                putExtra(Extras.PRODUCT_ID,    productId)
                putExtra(Extras.PRODUCT_NAME,  productName)
                putExtra(Extras.PRODUCT_PRICE, productPrice)
                putExtra(Extras.PRODUCT_IMAGE, productImage) // ← fix: image ikut dikirim
            }
            startActivity(intent)
        }
    }
}