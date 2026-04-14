package com.example.tokomputer

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tokomputer.adapter.ProductAdapter
import com.example.tokomputer.model.Product
import com.google.android.material.navigation.NavigationView

class CategoryListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_list)

        val category = intent.getStringExtra("category") ?: ""
        val tvTitle = findViewById<TextView>(R.id.tvCategoryTitle)
        val rv = findViewById<RecyclerView>(R.id.rvCategoryProducts)
        val drawer = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawerLayoutList)
        val navView = findViewById<NavigationView>(R.id.navigationViewList)
        val btnMenu2 = findViewById<android.widget.ImageButton>(R.id.btnMenu2)

        btnMenu2.setOnClickListener {
            drawer.openDrawer(navView)
        }

        tvTitle.text = when (category) {
            "komputer" -> "Komputer"
            "laptop" -> "Laptop"
            "komponen" -> "Komponen"
            else -> "Produk"
        }

        rv.layoutManager = GridLayoutManager(this, 2)
        val products = sampleProductsForCategory(category)
        rv.adapter = ProductAdapter(products) { product ->
            val i = Intent(this, ProductDetailActivity::class.java)
            i.putExtra("id", product.id)
            i.putExtra("name", product.name)
            i.putExtra("price", product.price)
            i.putExtra("image", product.imageRes)
            product.desc?.let { i.putExtra("desc", it) }
            product.specsText?.let { i.putExtra("specsText", it) }
            startActivity(i)
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> startActivity(Intent(this, MainActivity::class.java))
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
                R.id.nav_member -> startActivity(Intent(this, MemberActivity::class.java))
                R.id.nav_logout -> {
                    SharedPrefManager.clearLogin()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                R.id.nav_tentang -> startActivity(Intent(this, AboutActivity::class.java))
            }
            drawer.closeDrawers()
            true
        }
    }

    private fun sampleProductsForCategory(cat: String): List<Product> {
        // Reuse some sample products, in real app you'd filter or request from server
        return when (cat) {
            "komputer" -> listOf(
                Product(
                    101,
                    "Thermaltake",
                    "Rp80.000.000",
                    R.drawable.thermal1,
                    desc = "Cocok buat: gaming 4K/1440p ultra, rendering, editing, streaming.",
                    specsText = """Casing: Thermaltake View 51 / Thermaltake Level 20 (tempered glass, front 2×200mm fans)
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
                Product(
                    102,
                    "MXZ High‑Performance Gaming PC (R7 7700 + RTx4070)",
                    "Rp50.045.200",
                    R.drawable.pcgamingmx,
                    desc = "High-performance gaming PC built around Ryzen 7 7700 + RTX 4070.",
                    specsText = """Komponen	Spesifikasi
CPU	AMD Ryzen 7 7700
- 8 core / 16 thread
- Kecepatan dasar dan boost di ~3.8 GHz → ~5.3 GHz (menurut deskripsi toko)
GPU / Graphics	NVIDIA GeForce RTX 4070-12GB GDDR6X
- Versi “SUPER” (12GB) ada di varian lain
RAM	32 GB DDR5, frekuensi 6000 MHz
Storage	1 TB NVMe SSD (M.2)
Motherboard	Chipset B650-WIFI (disebut di spesifikasi MXZ)
Power Supply (PSU)	700W 80+ (varian reguler)
Di varian “SUPER” PSU-nya 800W 80+
Case	Case model MXZ-S11 (menurut spesifikasi dari MXZ)
Cooling / Fan	6 fan (atau 5 RGB fan tergantung varian)
Dimensi	20.79″ × 19.09″ × 11.7″ (~52.8 cm × 48.5 cm × 29.7 cm)
Operating System	Windows 11 Pro siap pakai (pre-installed)
Garansi / Support	1 Tahun Parts & Labour + Lifetime Technical Support (menurut situs MXZ)
Sumber: mxzgaming.com"""
                ),
                Product(
                    103,
                    "Custom Build Gaming PC (R5 7600X + RTX 4060 Ti)",
                    "Rp30.500.000",
                    R.drawable.costumbuildp,
                    desc = "Custom build gaming PC dengan AMD Ryzen 5 7600X & RTX 4060 Ti.",
                    specsText = """Komponen	Spesifikasi
CPU	AMD Ryzen 5 7600X
- 6 core / 12 thread
- Kecepatan dasar dan boost di ~4.7 GHz → ~5.3 GHz (menurut deskripsi toko)
GPU / Graphics	NVIDIA GeForce RTX 4060 Ti-8GB GDDR6
RAM	16 GB DDR5, frekuensi 6000 MHz
Storage	512 GB NVMe SSD (M.2)
Motherboard	Chipset B650 (disebut di spesifikasi toko)
Power Supply (PSU)	650W 80+ Bronze
Case	Case model mid-tower (menurut spesifikasi dari toko)
Cooling / Fan	4 fan (menurut spesifikasi dari toko)
Dimensi	Standar mid-tower (tidak disebutkan ukuran spesifik)
Operating System	Windows 11 Home siap pakai (pre-installed)
Garansi / Support	1 Tahun Parts & Labour + Lifetime Technical Support (menurut situs toko)
Sumber: custompc.id"""
                ),
            )

            "laptop" -> listOf(
                Product(201, "ASUS ROG Strix", "Rp25.575.999", R.drawable.laptop, desc = "Laptop gaming premium"),
                Product(202, "ASUS ZenBook 14 OLED (UX3405 / UX3405CA)", "Rp19.999.000", R.drawable.zenbook141,
                    desc = "Content creator mobile: layar OLED akurat & portabel.",
                    specsText = """CPU: Intel Core Ultra (atau AMD Ryzen AI 7 di varian lain).
GPU: Integrated (Intel ARC / AMD integrated).
RAM: up to 32 GB LPDDR5x.
Storage: up to 1 TB PCIe 4.0 SSD.
Layar: 14" OLED 3K (atau 2.8K) 120 Hz, HDR, akurasi warna tinggi.
Battery: diklaim >12 jam pemakaian ringan tergantung konfigurasi.
Port & konektivitas: Thunderbolt 4 / USB-C, Wi-Fi 6E.
Berat: ~1.1–1.2 kg (sangat portabel).
Kelebihan: layar OLED tajam, opsi CPU AI-centric, nilai untuk creator mobile.
Cocok untuk: content creator yang butuh layar akurat & portabilitas."""
                ),
                Product(203, "ASUS TUF Gaming F15 FX506HF", "Rp11.499.000", R.drawable.tufgamingf15,
                    desc = "Laptop gaming terjangkau dengan performa solid untuk gaming 1080p.",
                    specsText = """CPU: Intel Core i5-11400H (6 core / 12 thread, hingga 4.5 GHz).
GPU: NVIDIA GeForce GTX 2050 4GB GDDR6.
RAM: 8 GB DDR4 (upgradeable).
Storage: 512 GB NVMe SSD.
Layar: 15.6" FHD 144 Hz IPS.
Konektivitas: Wi-Fi 6, Bluetooth 5.2.
Port: USB-C, USB-A, HDMI 2.0b, audio combo jack.
Baterai: 48 Wh (dengan pengisian cepat).
Dimensi & Berat: ~359 x 256 x 24.9 mm, ~2.3 kg.
Kelebihan: performa gaming solid untuk harga terjangkau, build kokoh.
Cocok untuk: gamer kasual yang butuh laptop gaming budget-friendly."""
                ),
            )

            "komponen" -> listOf(
                Product(301, "NVIDIA RTX 5090", "Rp57.499.00", R.drawable.rtx50901,
                    desc = "Flagship GPU berbasis arsitektur Blackwell 2.0",
                    specsText = """Arsitektur GPU	Blackwell 2.0 (GPU “GB202")
Sumber: TechPowerUp / Tekno Kompas

Proses Pembuatan (Node)	5 nm
Sumber: TechPowerUp

CUDA / Shader Cores	21.760 core
Sumber: TechPowerUp

Tensor Cores	680 (versi AI & machine learning)
Sumber: TechPowerUp

RT (Ray Tracing) Cores	170, Gen-4
Sumber: NVIDIA

Base Clock	~ 2,017 MHz
Sumber: TechPowerUp

Boost Clock	~ 2,407 MHz
Sumber: TechPowerUp

Memori	32 GB GDDR7
Sumber: Tekno Kompas

Bus Memori	512-bit
Sumber: TechPowerUp

Bandwidth Memori	~ 1,79 TB/s (dari spec TechPowerUp)
Sumber: TechPowerUp

Interface PCIe	PCIe 5.0 ×16
Sumber: TechPowerUp

TDP / Daya	575 W (menurut NVIDIA & bocoran)
Sumber: TechPowerUp

Rekomendasi PSU	Minimal 1000 W (menurut NVIDIA)
Sumber: NVIDIA

Konektor Daya	1× konektor 16-pin (PCIe Gen 5) — atau adaptor kabel sesuai PSU
Sumber: NVIDIA

Output Display	1× HDMI 2.1b, 3× DisplayPort 2.1b
Sumber: TechPowerUp

Dimensi Fisik	304 mm × 137 mm × 40 mm (2-slot)
Sumber: TechPowerUp

Suhu Maksimum	~90°C (ditulis di spesifikasi NVIDIA)
Sumber: NVIDIA

Teknologi & Fitur	- DirectX 12 Ultimate
- DLSS 4 (termasuk Frame Generation generasi baru)
- NVIDIA Reflex 2
- Arsitektur AI & ray tracing generasi baru
- Pendinginan dual-slot (Founders Edition)
Sumber: NVIDIA / TechPowerUp
"""),
                Product(
                    302,
                    "Intel Core i9-13900KS",
                    "Rp12.606.912",
                    R.drawable.i9_13900ks,
                    desc = "Intel Core i9-13900KS — 13th Gen Raptor Lake-S, high-frequency flagship.",
                    specsText = """Arsitektur / Generasi	13th Gen Intel Core ("Raptor Lake-S")
Sumber: TechPowerUp / Intel

Jumlah Core / Thread	24 core total: 8 Performance-Cores (P-Core) + 16 Efficient-Cores (E-Core) → 32 thread
Sumber: TechPowerUp

Frekuensi / Clock	- Base P-Core: 3.20 GHz (Intel)
	- Base E-Core: 2.40 GHz (NanoReview.net)
	- Turbo maksimal (Thermal Velocity Boost): 6.00 GHz (Intel)
	- Turbo lainnya: P-Core Turbo 5.8 GHz, P-Core max 5.4 GHz, E-Core boost hingga ~4.3 GHz (B&H Photo Video)

Cache	- L3 (Smart Cache): 36 MB (Intel)
	- Total L2: 32 MB (TechPowerUp)

TDP & Daya	- Base Power (PL1): 150 W (Intel)
	- Maximum Turbo Power (PL2): 253 W (B&H Photo Video)

Proses Produksi	Intel 7 (beberapa sumber menyebut 10nm penamaan Intel) — TechPowerUp

Socket	LGA 1700 (B&H Photo Video)

Memori / RAM	- Mendukung DDR5 hingga 5600 MT/s
	- Mendukung DDR4 hingga 3200 MT/s
	- Max memory size: 192 GB (2 channel)
	- ECC memory: didukung
	Sumber: Intel

Grafis Terintegrasi (iGPU)	Intel UHD Graphics 770
	- Frekuensi dasar: ~300 MHz
	- Frekuensi maksimal: ~1.65 GHz
	- Output: eDP 1.4b / DP 1.4a / HDMI 2.1
	- Unit eksekusi: 32 EU
	Sumber: Intel

Ekspansi / PCIe	Mendukung PCIe Gen 5 (16 lanes) & PCIe Gen 4 (4 lanes)
"""
                ),
                Product (
                    303,
                    "Samsung 990 Pro 1TB",
                    "Rp2.499.000",
                    R.drawable.samsung_990_pro,
                    desc = "NVMe SSD tercepat dari Samsung untuk gaming & content creation.",
                    specsText = """Tipe		M.2 NVMe SSD
Kapasitas		1 TB
Antarmuka		PCIe Gen 4.0 x4, NVMe 1.4)
Form Factor		M.2 2280
Controller		Samsung Elpis
NAND Flash		Samsung V-NAND 3-bit MLC
Kecepatan Baca		hingga 7.450 MB/s
Kecepatan Tulis		hingga 6.900 MB/s
IOPS Baca Acak		hingga 1.500.000 IOPS
IOPS Tulis Acak		hingga 1.550.000 IOPS
Daya Aktif		5 mW (idle) hingga 8.2 W (aktif)
Daya Idle		30 mW (PS3) / 50 mW (PS4)
Suhu Operasi		0°C hingga 70°C
Dimensi		80.15 x 22.15 x 2.38 mm
Berat		~8 gram
Garansi		5 tahun atau hingga 600 TBW
Fitur Tambahan		Dynamic Thermal Guard, AES 256-bit Encryption, Samsung Magician Software
Sumber: Samsung"""
                ),
                Product (
                    304,
                    "Corsair Vengeance DDR5 32GB (2x16GB) 6000MHz",
                    "Rp2.999.000",
                    R.drawable.corsair_vengeance_ddr5,
                    desc = "RAM DDR5 cepat dari Corsair untuk gaming & multitasking.",
                    specsText = """Tipe		DDR5 SDRAM
Kapasitas		32 GB (2x16 GB)
Kecepatan		6000 MHz (PC5-48000)
Latency		CL36 (36-36-36-76)
Tegangan		1.35 V
Format		288-pin DIMM
Dukungan XMP		Ya, Intel XMP 3.0
Dukungan EXPO		Ya, AMD EXPO
Garansi		Seumur hidup terbatas
Fitur Tambahan		Heat Spreader aluminium, kompatibel dengan motherboard DDR5
Sumber: Corsair"""),
            )
            else -> emptyList()
        }
    }
}
