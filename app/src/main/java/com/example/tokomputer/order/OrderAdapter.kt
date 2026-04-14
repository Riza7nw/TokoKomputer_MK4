package com.example.tokomputer.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tokomputer.R
import com.example.tokomputer.model.OrderItem
import java.util.Locale
import kotlin.math.max

class OrderAdapter(
    private var items: MutableList<OrderItem>,
    private val onQtyChanged: (OrderItem) -> Unit,
    private val onRemove: (OrderItem) -> Unit
) : RecyclerView.Adapter<OrderAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv: ImageView = itemView.findViewById(R.id.ivOrderImage)
        val tvName: TextView = itemView.findViewById(R.id.tvOrderName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvOrderPrice)
        val tvQty: TextView = itemView.findViewById(R.id.tvQuantity)
        val btnDec: Button = itemView.findViewById(R.id.btnDecrease)
        val btnInc: Button = itemView.findViewById(R.id.btnIncrease)
        val tvItemTotal: TextView = itemView.findViewById(R.id.tvItemTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        // use provided imageRes if available, otherwise use safe fallback drawable
        val imgRes = item.imageRes ?: R.drawable.laptop
        holder.iv.setImageResource(imgRes)
        holder.tvName.text = item.name
        holder.tvPrice.text = formatRupiah(item.unitPrice)
        holder.tvQty.text = item.quantity.toString()
        holder.tvItemTotal.text = formatRupiah(item.totalPrice())

        holder.btnInc.setOnClickListener {
            item.quantity += 1
            holder.tvQty.text = item.quantity.toString()
            holder.tvItemTotal.text = formatRupiah(item.totalPrice())
            onQtyChanged(item)
        }
        holder.btnDec.setOnClickListener {
            item.quantity = max(1, item.quantity - 1)
            holder.tvQty.text = item.quantity.toString()
            holder.tvItemTotal.text = formatRupiah(item.totalPrice())
            onQtyChanged(item)
        }

        holder.itemView.setOnLongClickListener {
            onRemove(item)
            true
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: MutableList<OrderItem>) {
        items = newList
        notifyDataSetChanged()
    }

    private fun formatRupiah(value: Double): String {
        return "Rp" + String.format(Locale("id", "ID"), "%,.0f", value)
    }
}
