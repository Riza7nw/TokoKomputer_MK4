package com.example.tokomputer.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object OrderRepository {
    private val _orders = MutableLiveData<MutableList<OrderItem>>(mutableListOf())
    val orders: LiveData<MutableList<OrderItem>> = _orders

    fun addItem(item: OrderItem) {
        val list = _orders.value ?: mutableListOf()
        // if item exists (same id), increment quantity and update price if necessary
        val existing = list.find { it.id == item.id }
        if (existing != null) {
            // update unitPrice if incoming price is > 0 and different
            if (item.unitPrice > 0.0 && existing.unitPrice != item.unitPrice) {
                existing.unitPrice = item.unitPrice
            }
            existing.quantity += item.quantity
        } else {
            list.add(item)
        }
        _orders.value = list
    }

    fun updateItemQuantity(itemId: Int, qty: Int) {
        val list = _orders.value ?: return
        val existing = list.find { it.id == itemId } ?: return
        existing.quantity = qty.coerceAtLeast(1)
        _orders.value = list
    }

    fun removeItem(itemId: Int) {
        val list = _orders.value ?: return
        list.removeAll { it.id == itemId }
        _orders.value = list
    }

    fun clear() {
        _orders.value = mutableListOf()
    }

    fun totalAmount(): Double {
        val list = _orders.value ?: return 0.0
        return list.sumOf { it.totalPrice() }
    }
}
