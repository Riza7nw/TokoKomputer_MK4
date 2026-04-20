package com.example.tokomputer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tokomputer.adapter.ProductAdapter
import com.example.tokomputer.model.Product
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var rvProducts: RecyclerView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var btnMenu: ImageButton
    private lateinit var btnCart: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testingSupabase()

        // Initialize SharedPrefManager
        SharedPrefManager.init(this)

        // Inisialisasi komponen
        rvProducts = findViewById(R.id.rvProducts)
        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navigationView)
        btnMenu = findViewById(R.id.btnMenu)
        btnCart = findViewById(R.id.btnCart)

        // Setup RecyclerView
        rvProducts.layoutManager = GridLayoutManager(this, 2)
        rvProducts.setHasFixedSize(true)

        val products = sampleProducts()
        val adapter = ProductAdapter(products) { product ->
            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                putExtra("id", product.id)
                putExtra("name", product.name)
                putExtra("price", product.price)
                putExtra("image", product.imageRes)
                product.desc?.let { putExtra("desc", it) }
                product.specsResId?.let { putExtra("specsResId", it) }
                product.specsText?.let { putExtra("specsText", it) }
            }
            startActivity(intent)
        }
        rvProducts.adapter = adapter

        // Tombol menu membuka drawer
        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(navView)
        }

        // Tombol cart membuka order activity
        btnCart.setOnClickListener {
            val intent = Intent(this, com.example.tokomputer.order.OrderActivity::class.java)
            startActivity(intent)
        }

        // Aksi saat menu dipilih: navigate to proper screens
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_member -> {
                    if (SharedPrefManager.isLoggedIn()) {
                        startActivity(Intent(this, MemberActivity::class.java))
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                }
                R.id.nav_home -> {
                    // we're already on main; just close drawer
                }
                R.id.nav_komputer -> {
                    val i = Intent(this, CategoryListActivity::class.java)
                    i.putExtra("category", "komputer")
                    startActivity(i)
                }
                R.id.nav_laptop -> {
                    val i = Intent(this, CategoryListActivity::class.java)
                    i.putExtra("category", "laptop")
                    startActivity(i)
                }
                R.id.nav_komponen -> {
                    val i = Intent(this, CategoryListActivity::class.java)
                    i.putExtra("category", "komponen")
                    startActivity(i)
                }
                R.id.nav_tentang -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                }
                R.id.nav_logout -> {
                    SharedPrefManager.clearLogin()
                    Toast.makeText(this, "Logout Berhasil", Toast.LENGTH_SHORT).show()
                }
            }

            drawerLayout.closeDrawers()
            true
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun testingSupabase() {
        Log.d("SUPABASE_TEST", "Memulai koneksi...") // Log awal untuk tes
        lifecycleScope.launch {
            try {
                testConnection()
                Log.d("SUPABASE_TEST", "Fungsi testConnection() selesai dipanggil")
            } catch (e: Exception) {
                // Ini akan menangkap error koneksi, internet, atau library missing
                Log.e("SUPABASE_TEST", "Koneksi Gagal: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    private fun sampleProducts(): List<Product> {
        return listOf(
            Product(1, "ASUS ROG STRIX G16", "Rp25.575.999", R.drawable.laptop,
                desc = "Laptop gaming ASUS ROG Strix G16 dengan performa tinggi untuk gaming dan content creation.",
                specsResId = R.string.specs_asus_rog_strix_g16
            ),
            Product(2, "Thermaltake", "Rp200.000.000", R.drawable.thermal1,
                desc = "Cocok buat: gaming 4K/1440p ultra, rendering, editing, streaming.",
                specsText = """Cocok buat: gaming 4K/1440p ultra, rendering, editing, streaming.

                Casing: Thermaltake View 51 / Thermaltake Level 20 (tempered glass, front 2×200mm fans)

                CPU: AMD Ryzen 9 7900X3D atau Intel Core i9-14900K

                CPU Cooler: AIO 360mm RGB (Thermaltake Floe RC360 / Corsair iCUE H150i Elite)

                Motherboard: ATX X670E (untuk Ryzen) atau Z790/Z890 (untuk Intel) — fitur: PCIe 5.0, 2.5Gb LAN, Wifi 6E

                RAM: 32 GB (2×16) DDR5-6000 (XMP/EXPO) — upgradeable ke 64 GB

                GPU: NVIDIA GeForce RTX 4080 / RTX 4070 Ti (tergantung budget)

                Storage (OS): 1 TB NVMe PCIe 4.0 SSD (Samsung 990 Pro / WD Black SN850X)

                Storage (extra): 2 TB SATA SSD atau NVMe tambahan untuk data

                PSU: 850 W - 1000 W Gold (Seasonic / Corsair RMx / Thermaltake Toughpower)

                Case Fans: 3×120/3×140 ARGB front + 2×120 top (sesuaikan radiator)

                Front I/O: USB-C, USB-A, audio jack

                OS: Windows 11 Pro

                Perkiraan harga (IDR): Rp 45.000.000 – 80.000.000 (tergantung GPU & CPU)"""
            ),
            Product(3, "Monitor MSI MAG 25\" FHD", "Rp1.645.000", R.drawable.monitor251,
                desc = "Monitor 25 inch FHD, 144Hz, ideal untuk gaming.",
                specsText = "Resolution: 1920x1080\nRefresh: 144Hz\nResponse Time: 1ms"
            ),
            Product(4, "Monitor Gaming Asus 27\"", "Rp1.599.000", R.drawable.monitor27,
                desc = "Monitor gaming 27 inch dengan harga terjangkau.",
                specsText = "Resolution: 1920x1080\nRefresh: 165Hz\nPanel: IPS"
            ),
            Product(5, "Intel Core i9-13900KS", "Rp12.606.912", R.drawable.i9_13900ks,
                desc = "CPU high-end untuk desktop enthusiast.",
                specsText = "Cores: 24\nBase/Boost: 3.0/5.8 GHz"
            ),
            Product(6, "ASUS ROG RTX 5090 32GB", "Rp57.489.000", R.drawable.rtx50901,
                desc = "GPU flagship untuk gaming 4K dan AI workloads.",
                specsText = "Memory: 32GB GDDR7\nCUDA Cores: 20000+"
            ),
            Product(7, "after effect", "Rp1.500.000", R.drawable.after_effect,
                desc = "editing video profesional.",
                specsText = "Version: 2024\nLicense: Full"
            ),
        )
    }


}
