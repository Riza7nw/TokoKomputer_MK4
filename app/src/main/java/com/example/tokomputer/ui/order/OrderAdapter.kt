package com.example.tokomputer.ui.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tokomputer.R
import com.example.tokomputer.model.CartItem

class OrderAdapter(
    private var items: List<CartItem>,
    private val onIncrease: (CartItem) -> Unit,
    private val onDecrease: (CartItem) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivOrderImage: ImageView = itemView.findViewById(R.id.ivOrderImage)
        val tvOrderName: TextView   = itemView.findViewById(R.id.tvOrderName)
        val tvOrderPrice: TextView  = itemView.findViewById(R.id.tvOrderPrice)
        val tvQuantity: TextView    = itemView.findViewById(R.id.tvQuantity)
        val tvItemTotal: TextView   = itemView.findViewById(R.id.tvItemTotal)
        val btnIncrease: Button     = itemView.findViewById(R.id.btnIncrease)
        val btnDecrease: Button     = itemView.findViewById(R.id.btnDecrease)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = items[position]

        holder.tvOrderName.text  = item.productName
        holder.tvOrderPrice.text = "Rp ${String.format("%,.0f", item.price)}"
        holder.tvQuantity.text   = item.quantity.toString()
        holder.tvItemTotal.text  = "Rp ${String.format("%,.0f", item.subtotal)}"

        Glide.with(holder.itemView.context)
            .load(item.productImage)
            .placeholder(R.drawable.ic_computer)
            .error(R.drawable.ic_computer)
            .centerCrop()
            .into(holder.ivOrderImage)

        holder.btnIncrease.setOnClickListener { onIncrease(item) }
        holder.btnDecrease.setOnClickListener { onDecrease(item) }
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<CartItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}