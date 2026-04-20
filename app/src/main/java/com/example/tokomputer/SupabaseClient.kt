package com.example.tokomputer

import android.util.Log
import com.example.tokomputer.model.Product
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from

val supabase = createSupabaseClient(
        supabaseUrl = "https://ecoacsrqogjzevpjwdhd.supabase.co",
        supabaseKey = "sb_publishable_Fbri75v_mE-qY7EgId5wOw_g7kZsSXp"
      ) {
        install(Postgrest)
    }

suspend fun testConnection() {
    val products = supabase
        .from("products")
        .select()
        .decodeList<Product>()

    if (products.isEmpty()) {
        Log.d("SUPABASE_TEST", "Koneksi berhasil, tapi tabel 'products' kosong.")
        Log.d("SUPABASE_TEST", "Jumlah data: ${products.size}")
    } else {
        Log.d("SUPABASE_TEST", "Data ditemukan: ${products.size}")
        Log.d("SUPABASE_TEST", products.toString())
    }
}