package com.example.tokomputer.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tokomputer.R
import com.example.tokomputer.model.TransactionModel
import java.text.NumberFormat
import java.util.Locale

class TransactionHistoryAdapter(
    private var items: List<TransactionModel>,
    private val onItemClick: (TransactionModel) -> Unit
) : RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>() {

    fun updateData(newItems: List<TransactionModel>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvId: TextView          = itemView.findViewById(R.id.tvTransactionId)
        private val tvDate: TextView        = itemView.findViewById(R.id.tvTransactionDate)
        private val tvTotalItem: TextView   = itemView.findViewById(R.id.tvTotalItem)
        private val tvTotalPrice: TextView  = itemView.findViewById(R.id.tvTotalPrice)

        private val llProductsContainer: LinearLayout = itemView.findViewById(R.id.llProductsContainer)

        fun bind(transaction: TransactionModel) {
            tvId.text   = "#${transaction.id}"

            tvDate.text = transaction.createdAt?.substringBefore("T") ?: "-"

            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

            tvTotalPrice.text = formatRupiah.format(transaction.totalPrice).replace("Rp", "Rp ")

            llProductsContainer.removeAllViews()
            var totalQty = 0

            transaction.items?.forEach { item ->
                totalQty += item.quantity

                // 1. Inflate the detail view and ensure it's recognized as a View
                val detailView: View = LayoutInflater.from(itemView.context)
                    .inflate(R.layout.item_transaction_detail, llProductsContainer, false)

                // 2. Reference the views inside that inflated layout
                val tvDetailQty   = detailView.findViewById<TextView>(R.id.tvDetailQty)
                val tvDetailName  = detailView.findViewById<TextView>(R.id.tvDetailName)
                val tvDetailPrice = detailView.findViewById<TextView>(R.id.tvDetailPrice)

                // 3. Set the data
                tvDetailQty.text   = "${item.quantity}x"
                tvDetailName.text  = item.product?.name ?: "Produk Dihapus/Tidak Diketahui"
                tvDetailPrice.text = formatRupiah.format(item.price * item.quantity).replace("Rp", "Rp ")

                // 4. Add to container
                llProductsContainer.addView(detailView)
            }

            tvTotalItem.text = "$totalQty item"

            itemView.setOnClickListener {
                onItemClick(transaction)
            }
        }
    }
}