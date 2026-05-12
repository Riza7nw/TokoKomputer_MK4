package com.example.tokomputer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val image: Int,
    val category: String = "",
    val brand: String = "",
    val subcategory: String = "",
    val desc: String = "",
    val specsText: String = "",
    val specsResId: Int? = null   // tambah ini
) : Parcelable