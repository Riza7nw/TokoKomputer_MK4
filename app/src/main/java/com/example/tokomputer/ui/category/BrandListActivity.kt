package com.example.tokomputer.ui.category

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tokomputer.utils.Extras

// BrandListActivity sekarang langsung redirect ke CategoryListActivity
// Dipertahankan agar tidak ada referensi yang error
class BrandListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val category = intent.getStringExtra(Extras.CATEGORY) ?: "Produk"

        // Langsung redirect ke CategoryListActivity
        val intent = Intent(this, CategoryListActivity::class.java)
        intent.putExtra(Extras.CATEGORY, category)
        startActivity(intent)
        finish()
    }
}