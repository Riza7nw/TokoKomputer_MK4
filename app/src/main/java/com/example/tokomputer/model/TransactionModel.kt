package com.example.tokomputer.model

import com.google.gson.annotations.SerializedName

data class TransactionModel(
    @SerializedName("id")          val id: Int,
    @SerializedName("user")        val user: UserModel?,
    @SerializedName("name")        val name: String,
    @SerializedName("phone")       val phone: String?,
    @SerializedName("total_price") val totalPrice: Double,
    @SerializedName("created_at")  val createdAt: String?,
    @SerializedName("items")       val items: List<TransactionItemModel>?
)

data class TransactionItemModel(
    @SerializedName("product")  val product: TransactionProductModel,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("price")    val price: Double,
    @SerializedName("subtotal") val subtotal: Double
)

data class TransactionProductModel(
    @SerializedName("id")    val id: Int,
    @SerializedName("name")  val name: String,
    @SerializedName("price") val price: Double
)