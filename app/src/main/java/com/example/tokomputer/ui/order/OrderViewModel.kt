package com.example.tokomputer.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {
    val orders: LiveData<MutableList<OrderItem>> = OrderRepository.orders

    fun addItem(item: OrderItem) = OrderRepository.addItem(item)
    fun updateQuantity(itemId: Int, qty: Int) = OrderRepository.updateItemQuantity(itemId, qty)
    fun removeItem(itemId: Int) = OrderRepository.removeItem(itemId)
    fun clearOrders() = OrderRepository.clear()
    fun totalAmount(): Double = OrderRepository.totalAmount()
}

