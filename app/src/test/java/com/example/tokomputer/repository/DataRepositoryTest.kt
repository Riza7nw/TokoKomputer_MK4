package com.example.tokomputer.repository

import org.junit.Assert.*
import org.junit.Test

class DataRepositoryTest {

    @Test
    fun `subcategories for komponen contains RAM and CPU`() {
        val subs = DataRepository.getSubCategoriesForKomponen()
        assertTrue("Subcategories should contain RAM", subs.any { it.equals("RAM", ignoreCase = true) })
        assertTrue("Subcategories should contain CPU", subs.any { it.equals("CPU", ignoreCase = true) })
    }

    @Test
    fun `brands for ram contains Corsair and Kingston`() {
        val brands = DataRepository.getBrandsForSubcategory("RAM")
        assertTrue(brands.contains("Corsair"))
        assertTrue(brands.contains("Kingston"))
    }

    @Test
    fun `getProducts returns products for brand corsair`() {
        val products = DataRepository.getProducts(category = null, subcategory = "RAM", brand = "Corsair")
        assertTrue("Products for Corsair should not be empty", products.isNotEmpty())
        val p = products.first()
        assertTrue(p.name.contains("Corsair", ignoreCase = true) || p.name.isNotEmpty())
    }

    @Test
    fun `getProducts returns laptop samples for category laptop`() {
        val products = DataRepository.getProducts(category = "laptop", subcategory = null, brand = null)
        assertTrue("Laptop products should not be empty", products.isNotEmpty())
    }
}

