package com.example.tokomputer.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val imageRes: Int,
    val desc: String? = null,
    val specsResId: Int? = null,
    val specsText: String? = null,

    // 🔥 tambahan (opsional, aman)
    val category: String? = null,
    val subcategory: String? = null,
    val brand: String? = null
)