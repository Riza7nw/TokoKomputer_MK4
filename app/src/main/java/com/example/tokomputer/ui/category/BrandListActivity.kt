package com.example.tokomputer.ui.category

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tokomputer.utils.Extras
import com.example.tokomputer.R
import com.example.tokomputer.adapter.SimpleListAdapter
import com.example.tokomputer.data.repository.DataRepository

class BrandListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brand_list)

        val category = intent.getStringExtra(Extras.CATEGORY)
        val subcategory = intent.getStringExtra(Extras.SUBCATEGORY)

        val rv = findViewById<RecyclerView>(R.id.rvBrands)
        rv.layoutManager = LinearLayoutManager(this)

        // Decide which brands to show based on category/subcategory
        val brands = when (category) {
            "laptop" -> DataRepository.getLaptopBrands()
            "komputer" -> DataRepository.getKomputerBrands()
            "komponen" -> {
                if (!subcategory.isNullOrBlank()) DataRepository.getBrandsForSubcategory(subcategory)
                else emptyList()
            }
            else -> emptyList()
        }

        rv.adapter = SimpleListAdapter(brands) { brand ->
            // When brand clicked -> open product list (reuse CategoryListActivity)
            val i = Intent(this, CategoryListActivity::class.java)
            i.putExtra(Extras.CATEGORY, category)
            subcategory?.let { i.putExtra(Extras.SUBCATEGORY, it) }
            i.putExtra(Extras.BRAND, brand)
            startActivity(i)
        }
    }
}
