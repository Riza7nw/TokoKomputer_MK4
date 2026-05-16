package com.example.tokomputer.model

import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("id")          val id: Int,
    @SerializedName("name")        val name: String,
    @SerializedName("price")       val price: Double,
    @SerializedName("category")    val category: String?,
    @SerializedName("stock")       val stock: Int,
    @SerializedName("description") val description: String?,
    @SerializedName("image")       val image: String?,
    @SerializedName("created_at")  val createdAt: String?
)