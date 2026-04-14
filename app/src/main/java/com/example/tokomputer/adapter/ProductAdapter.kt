package com.example.tokomputer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tokomputer.R
import com.example.tokomputer.model.Product

class ProductAdapter(
    private val items: List<Product>,
    private val onViewClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.VH>() {

    private var lastPosition = -1

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv: ImageView = itemView.findViewById(R.id.ivProduct)
        val name: TextView = itemView.findViewById(R.id.tvName)
        val price: TextView = itemView.findViewById(R.id.tvPrice)
        val btnView: Button = itemView.findViewById(R.id.btnView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = items[position]
        holder.name.text = p.name
        holder.price.text = p.price

        // load image dari resource drawable dengan placeholder/error
        Glide.with(holder.itemView.context)
            .load(p.imageRes)
            .placeholder(R.drawable.monitor251)
            .error(R.drawable.monitor251)
            .centerInside()
            .into(holder.iv)

        holder.btnView.setOnClickListener { onViewClick(p) }

        // animasi fade-in saat muncul
        val currentPosition = holder.adapterPosition
        if (currentPosition != RecyclerView.NO_POSITION && currentPosition > lastPosition) {
            val anim = AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.fade_in
            )
            holder.itemView.startAnimation(anim)
            lastPosition = currentPosition
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onViewDetachedFromWindow(holder: VH) {
        holder.itemView.clearAnimation()
    }
}
