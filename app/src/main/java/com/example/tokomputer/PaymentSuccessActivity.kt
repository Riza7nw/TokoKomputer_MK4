package com.example.tokomputer

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PaymentSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutId = resources.getIdentifier("activity_payment_success", "layout", packageName)
        if (layoutId == 0) {
            finish()
            return
        }
        setContentView(layoutId)

        val btnId = resources.getIdentifier("btnDone", "id", packageName)
        val btnDone = if (btnId != 0) findViewById<Button>(btnId) else null
        btnDone?.setOnClickListener {
            finish()
        }
    }
}
