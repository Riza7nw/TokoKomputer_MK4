package com.example.tokomputer.ui.order

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tokomputer.R
import com.example.tokomputer.model.CartItem
import com.example.tokomputer.ui.main.MainActivity
import com.example.tokomputer.ui.payment.PaymentSuccessActivity
import com.example.tokomputer.utils.Extras
import com.example.tokomputer.utils.Resource

class OrderActivity : AppCompatActivity() {

    private lateinit var rvOrders: RecyclerView
    private lateinit var tvTotalAmount: TextView
    private lateinit var btnPay: Button
    private lateinit var btnBack: ImageButton
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmpty: TextView

    private lateinit var orderAdapter: OrderAdapter
    private val viewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        initViews()
        setupRecyclerView()
        observeViewModel()
        handleIncomingProduct()
    }

    private fun initViews() {
        rvOrders      = findViewById(R.id.rvOrders)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        btnPay        = findViewById(R.id.btnPay)
        btnBack       = findViewById(R.id.btnBack)
        progressBar   = findViewById(R.id.progressBar)
        tvEmpty       = findViewById(R.id.tvEmpty)

        btnBack.setOnClickListener {
            // Kembali ke MainActivity
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            finish()
        }
    }

    private fun setupRecyclerView() {
        orderAdapter = OrderAdapter(
            items      = emptyList(),
            onIncrease = { item -> viewModel.increaseQuantity(item.productId) },
            onDecrease = { item -> viewModel.decreaseQuantity(item.productId) }
        )
        rvOrders.layoutManager = LinearLayoutManager(this)
        rvOrders.adapter       = orderAdapter
    }

    private fun handleIncomingProduct() {
        val productId    = intent.getIntExtra(Extras.PRODUCT_ID, 0)
        val productName  = intent.getStringExtra(Extras.PRODUCT_NAME)
        val productPrice = intent.getDoubleExtra(Extras.PRODUCT_PRICE, 0.0)
        val productImage = intent.getStringExtra(Extras.PRODUCT_IMAGE)

        if (productId != 0 && productName != null) {
            viewModel.addToCart(
                CartItem(
                    productId    = productId,
                    productName  = productName,
                    productImage = productImage,
                    price        = productPrice
                )
            )
        }

        btnPay.setOnClickListener {
            if (viewModel.isCartEmpty()) {
                Toast.makeText(this, "Keranjang masih kosong", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.checkout()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.cartItems.observe(this) { items ->
            if (items.isEmpty()) {
                rvOrders.visibility = View.GONE
                tvEmpty.visibility  = View.VISIBLE
            } else {
                rvOrders.visibility = View.VISIBLE
                tvEmpty.visibility  = View.GONE
                orderAdapter.updateData(items)
            }
        }

        viewModel.totalPrice.observe(this) { total ->
            tvTotalAmount.text = "Rp ${String.format("%,.0f", total)}"
        }

        viewModel.checkoutState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    btnPay.isEnabled       = false
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    btnPay.isEnabled       = true
                    val intent = Intent(this, PaymentSuccessActivity::class.java).apply {
                        putExtra("transaction_id", state.data?.id ?: 0)
                        putExtra("total_price",    state.data?.totalPrice ?: 0.0)
                    }
                    startActivity(intent)
                    finish()
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    btnPay.isEnabled       = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}