package com.example.tokomputer.model

data class CartItem(
    val productId: Int,
    val productName: String,
    val productImage: String?,
    val price: Double,
    var quantity: Int = 1
) {
    val subtotal: Double get() = price * quantity
}