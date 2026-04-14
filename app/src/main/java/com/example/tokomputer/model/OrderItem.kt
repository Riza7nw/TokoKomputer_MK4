package com.example.tokomputer.model

import java.io.Serializable

data class OrderItem(
    val id: Int,
    val name: String,
    var unitPrice: Double,
    var quantity: Int = 1,
    val imageRes: Int? = null
) : Serializable {
    fun totalPrice(): Double = unitPrice * quantity
}
