package com.example.tokomputer.ui.order

data class OrderItem(
    val id: Int,
    val name: String,
    val image: String,
    var unitPrice: Double,
    var quantity: Int
) {
    fun totalPrice(): Double = unitPrice * quantity
}