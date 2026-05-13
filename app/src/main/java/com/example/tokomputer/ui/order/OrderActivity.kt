package com.example.tokomputer.ui.order

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tokomputer.R

class OrderActivity : AppCompatActivity() {

    private val vm: OrderViewModel by viewModels()
    private lateinit var rvOrders: RecyclerView
    private lateinit var tvTotalAmount: TextView
    private lateinit var btnPay: Button
    private lateinit var adapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        rvOrders = findViewById(R.id.rvOrders)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        btnPay = findViewById(R.id.btnPay)

        adapter = OrderAdapter(mutableListOf(), onQtyChanged = { item ->
            vm.updateQuantity(item.id, item.quantity)
            refreshTotal()
        }, onRemove = { item ->
            vm.removeItem(item.id)
            refreshTotal()
        })

        rvOrders.layoutManager = LinearLayoutManager(this)
        rvOrders.adapter = adapter

        vm.orders.observe(this) { list ->
            adapter.updateList(list)
            refreshTotal()
        }

        btnPay.setOnClickListener {
            if (vm.totalAmount() <= 0.0) {
                AlertDialog.Builder(this)
                    .setTitle("Pesanan kosong")
                    .setMessage("Silakan tambahkan produk terlebih dahulu")
                    .setPositiveButton("OK", null)
                    .show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Pembayaran Berhasil")
                .setMessage("Terima kasih, pembayaran Anda berhasil.")
                .setPositiveButton("OK") { _, _ ->
                    vm.clearOrders()
                    // Start PaymentSuccessActivity by class name to avoid static reference
                    val className = "com.example.tokomputer.ui.transaction.PaymentSuccessActivity"
                    val intent = Intent()
                    intent.setClassName(this, className)
                    startActivity(intent)
                    finish()
                }
                .show()
        }
    }

    private fun refreshTotal() {
        val total = vm.totalAmount()
        tvTotalAmount.text = formatRupiah(total)
    }

    private fun formatRupiah(value: Double): String {
        return "Rp" + String.format(java.util.Locale("id", "ID"), "%,.0f", value)
    }
}
