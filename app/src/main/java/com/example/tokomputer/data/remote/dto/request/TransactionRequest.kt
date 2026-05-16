package com.example.tokomputer.data.remote.dto.request

data class TransactionRequest(
    val name: String,
    val phone: String,
    val items: List<TransactionItemRequest>
)

data class TransactionItemRequest(
    val product_id: Int,
    val quantity: Int
)