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

class SubCategoryListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subcategory_list)

        // This activity is intended for "komponen" category
        val rv = findViewById<RecyclerView>(R.id.rvSubCategories)
        rv.layoutManager = LinearLayoutManager(this)

        val subcats = DataRepository.getSubCategoriesForKomponen()
        rv.adapter = SimpleListAdapter(subcats) { subcat ->
            // Open BrandListActivity filtered by subcategory
            val i = Intent(this, BrandListActivity::class.java)
            i.putExtra(Extras.CATEGORY, "komponen")
            i.putExtra(Extras.SUBCATEGORY, subcat)
            startActivity(i)
        }
    }
}
