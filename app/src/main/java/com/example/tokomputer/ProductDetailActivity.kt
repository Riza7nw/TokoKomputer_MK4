package com.example.tokomputer

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tokomputer.model.OrderItem
import com.example.tokomputer.order.OrderViewModel
import java.util.Locale

class ProductDetailActivity : AppCompatActivity() {

    private val orderVm: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val imgProduct = findViewById<ImageView>(R.id.imgProduct)
        val tvName = findViewById<TextView>(R.id.tvProductName)
        val tvPrice = findViewById<TextView>(R.id.tvProductPrice)
        val tvDesc = findViewById<TextView>(R.id.tvProductDesc)
        val tvSpecs = findViewById<TextView>(R.id.tvProductSpecs)
        val btnBuy = findViewById<Button>(R.id.btnBuyNow)

        val name = intent.getStringExtra("name")
        val price = intent.getStringExtra("price")
        val image = intent.getIntExtra("image", R.drawable.rtx50901)
        val id = intent.getIntExtra("id", image) // fallback

        tvName.text = name ?: getString(R.string.product_name_asus)
        tvPrice.text = price ?: getString(R.string.product_price_asus)
        imgProduct.setImageResource(image)

        tvDesc.text = intent.getStringExtra("desc") ?: getString(R.string.product_desc)

        // Load specs: priority -> specsResId (int) -> specsText (string) -> default string resource
        val specsResId = intent.getIntExtra("specsResId", 0)
        val specsText = intent.getStringExtra("specsText")

        when {
            specsResId != 0 -> tvSpecs.setText(specsResId)
            !specsText.isNullOrEmpty() -> tvSpecs.text = specsText
            else -> tvSpecs.setText(R.string.specs_asus_rog_strix_g16)
        }

        btnBuy.setOnClickListener {
            val raw = price ?: ""
            // try to match numbers with thousand separators like 25.575.999 or 1,234,567
            val sepPattern = Regex("\\d{1,3}(?:[\\.,]\\d{3})+")
            val fallbackPattern = Regex("\\d+")
            val match = sepPattern.find(raw) ?: fallbackPattern.find(raw)
            val numberStr = match?.value?.replace("[^0-9]".toRegex(), "") ?: "0"
            val unitPrice = numberStr.toDoubleOrNull() ?: 0.0

            val orderItem = OrderItem(id = id, name = tvName.text.toString(), unitPrice = unitPrice, quantity = 1, imageRes = image)
            orderVm.addItem(orderItem)
            Toast.makeText(this, "Berhasil ditambahkan ke keranjang (${formatRupiah(unitPrice)})", Toast.LENGTH_SHORT).show()
        }
    }

    private fun formatRupiah(value: Double): String {
        return "Rp" + String.format(Locale("id", "ID"), "%,.0f", value)
    }
}
