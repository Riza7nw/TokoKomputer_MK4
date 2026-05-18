package com.example.tokomputer.ui.payment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tokomputer.R
import com.example.tokomputer.ui.main.MainActivity

class PaymentSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)

        val transactionId = intent.getIntExtra("transaction_id", 0)
        val totalPrice    = intent.getDoubleExtra("total_price", 0.0)

        // Tampilkan info transaksi jika ada
        findViewById<TextView>(R.id.tvSuccessMessage).text =
            "Transaksi #$transactionId berhasil!\n" +
                    "Total: Rp ${String.format("%,.0f", totalPrice)}\n\n" +
                    "Struk telah dikirim via WhatsApp 🎉"

        findViewById<Button>(R.id.btnDone).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}