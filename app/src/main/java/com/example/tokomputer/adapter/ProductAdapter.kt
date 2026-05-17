package com.example.tokomputer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tokomputer.R
import com.example.tokomputer.model.ProductModel

class ProductAdapter(
    private var products: List<ProductModel>,
    private val onItemClick: (ProductModel) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProduct: ImageView = itemView.findViewById(R.id.ivProduct)
        val tvName: TextView     = itemView.findViewById(R.id.tvName)
        val tvPrice: TextView    = itemView.findViewById(R.id.tvPrice)
        val btnView: Button      = itemView.findViewById(R.id.btnView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        holder.tvName.text  = product.name
        holder.tvPrice.text = "Rp ${String.format("%,.0f", product.price)}"

        // Load gambar dari URL pakai Glide
        Glide.with(holder.itemView.context)
            .load(product.image)
            .placeholder(R.drawable.ic_computer)
            .error(R.drawable.ic_computer)
            .centerCrop()
            .into(holder.ivProduct)

        holder.btnView.setOnClickListener { onItemClick(product) }
        holder.itemView.setOnClickListener { onItemClick(product) }
    }

    override fun getItemCount() = products.size

    // Update data dari luar (search, refresh)
    fun updateData(newProducts: List<ProductModel>) {
        products = newProducts
        notifyDataSetChanged()
    }
}