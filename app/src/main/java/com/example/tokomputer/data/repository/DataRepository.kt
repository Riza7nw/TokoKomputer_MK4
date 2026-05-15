package com.example.tokomputer.data.repository

import com.example.tokomputer.R

object DataRepository {

    private val allProducts = listOf(

        // ================= LAPTOP ASUS =================
        Product(1, "ASUS ROG Strix G16", "Rp25.575.999", R.drawable.laptop, desc = "laptop kencang", specsText = "dkod", category="laptop", brand="ASUS"),
        Product(2, "ASUS TUF Gaming F15", "Rp15.000.000", R.drawable.asus, desc = "laptop Gaming", specsText = "dkod" , category="laptop", brand="ASUS"),
        Product(3, "ASUS ZenBook 14 OLED", "Rp18.500.000", R.drawable.zenbook14, desc = "laptop oled" , specsText = "dkod" , category="laptop", brand="ASUS"),
        Product(4, "ASUS VivoBook 15", "Rp9.000.000", R.drawable.vivobookgo, desc = "laptop dana pelajar" , specsText = "dkod" , category="laptop", brand="ASUS"),
        Product(5, "ASUS ROG Zephyrus G14", "Rp22.000.000", R.drawable.rogzephyrusg14, desc = "laptop gaming 14 inci terkuat/ter-powerful ", specsText = "dkod" , category="laptop", brand="ASUS"),

        // ================= LAPTOP MSI =================
        Product(10, "MSI Katana GF66", "Rp16.000.000", R.drawable.msikatana, desc = "laptop gaming kelas terjangkau yang powerful", specsText = "dkod",  category="laptop", brand="MSI"),
        Product(11, "MSI Stealth 15M", "Rp22.000.000", R.drawable.msistealthgs77, desc = "laptop gaming ultraportable tertipis", specsText = "dkod", category="laptop", brand="MSI"),
        Product(12, "MSI Pulse GL66", "Rp19.000.000", R.drawable.msipulsegl66, desc = "laptop gaming dengan Core i9 termurah", specsText = "dkod", category="laptop", brand="MSI"),
        Product(13, "MSI GF63 Thin", "Rp13.000.000", R.drawable.msigf63thin, desc = "Laptop gaming tertipis dan teringan di kelas entry-level/menengah", specsText = "dkod", category="laptop", brand="MSI"),
        Product(14, "MSI Creator Z16", "Rp28.000.000", R.drawable.msicreatorz16, desc = "laptop konten kreator profesional yang tercantik dan tercanggih", specsText = "dkod", category="laptop", brand="MSI"),

        // ================= LAPTOP LENOVO =================
        Product(20, "Lenovo Legion 5", "Rp17.500.000", R.drawable.legion5, desc = "laptop gaming yang dingin dan kencang", specsText = "dkod", category="laptop", brand="Lenovo"),
        Product(21, "Lenovo Legion 7", "Rp25.000.000", R.drawable.legion7, desc = "laptop gaming 16 inci paling bertenaga", specsText = "dkod", category="laptop", brand="Lenovo"),
        Product(22, "Lenovo IdeaPad Gaming 3", "Rp12.000.000", R.drawable.ideapadgaming3, desc = "laptop Gaming Terjangkau", specsText = "dkod",  category="laptop", brand="Lenovo"),
        Product(23, "Lenovo ThinkPad X1 Carbon", "Rp30.000.000", R.drawable.thinkpadx1carbon, desc = "laptop bisnis premium terbaik", specsText = "dkod", category="laptop", brand="Lenovo"),
        Product(24, "Lenovo Yoga Slim 7", "Rp18.000.000", R.drawable.yogaslim7, desc = "laptop OLED 14 inci paling ringan di dunia", specsText = "dkod", category="laptop", brand="Lenovo"),

        // ================= LAPTOP HP =================
        Product(30, "HP Victus 15", "Rp14.000.000", R.drawable.victus15, desc = "laptop Terjangkau", specsText = "dkod", category="laptop", brand="HP"),
        Product(31, "HP Omen 16", "Rp20.000.000", R.drawable.omen16, desc = "laptop terkencang atau teradem di kelas menengah", specsText = "dkod",  category="laptop", brand="HP"),
        Product(32, "HP Pavilion Gaming", "Rp13.000.000", R.drawable.pavilion, desc = "laptop gaming entry-level yang kencang, lengkap, dan terjangkau", specsText = "dkod", category="laptop", brand="HP"),
        Product(33, "HP Spectre x360", "Rp25.000.000", R.drawable.spectrex360, desc = "laptop 2-in-1 konvertibel premium terbaik", specsText = "dkod", category="laptop", brand="HP"),
        Product(34, "HP Envy 13", "Rp18.000.000", R.drawable.envy3, desc = "laptop premium yang lebih terjangkau", specsText = "dkod", category="laptop", brand="HP"),

        // ================= LAPTOP DELL =================
        Product(40, "Dell G15 Gaming", "Rp15.000.000", R.drawable.dellg15, desc = "laptop premium yang lebih terjangkau", specsText = "dkod", category="laptop", brand="Dell"),
        Product(41, "Dell Alienware M16", "Rp35.000.000", R.drawable.laptop, category="laptop", brand="Dell"),
        Product(42, "Dell XPS 13", "Rp28.000.000", R.drawable.laptop, category="laptop", brand="Dell"),
        Product(43, "Dell Inspiron 15", "Rp10.000.000", R.drawable.laptop, category="laptop", brand="Dell"),
        Product(44, "Dell Latitude 7420", "Rp20.000.000", R.drawable.laptop, category="laptop", brand="Dell"),

        // ================= LAPTOP APPLE =================
        Product(50, "MacBook Air M1", "Rp14.000.000", R.drawable.laptop, category="laptop", brand="Apple"),
        Product(51, "MacBook Air M2", "Rp18.000.000", R.drawable.laptop, category="laptop", brand="Apple"),
        Product(52, "MacBook Pro 13 M2", "Rp22.000.000", R.drawable.laptop, category="laptop", brand="Apple"),
        Product(53, "MacBook Pro 14 M3", "Rp32.000.000", R.drawable.laptop, category="laptop", brand="Apple"),
        Product(54, "MacBook Pro 16 M3", "Rp40.000.000", R.drawable.laptop, category="laptop", brand="Apple"),

        // ================= KOMPUTER =================
        Product(999, "Custom Gaming PC", "Rp45.000.000", R.drawable.thermal1, category="komputer", brand="Custom"),

        // ================= RAM CORSAIR =================
        Product(100, "Corsair Vengeance DDR4 16GB", "Rp1.200.000", R.drawable.corsair_vengeance_ddr5, category="komponen", subcategory="ram", brand="Corsair"),
        Product(101, "Corsair Vengeance DDR5 32GB", "Rp2.999.000", R.drawable.corsair_vengeance_ddr5, category="komponen", subcategory="ram", brand="Corsair"),
        Product(102, "Corsair Dominator Platinum 16GB", "Rp2.000.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Corsair"),
        Product(103, "Corsair Dominator Platinum 32GB", "Rp3.500.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Corsair"),
        Product(104, "Corsair Vengeance RGB Pro 16GB", "Rp1.500.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Corsair"),

        // ================= RAM KINGSTON =================
        Product(110, "Kingston Fury Beast 16GB", "Rp1.199.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Kingston"),
        Product(111, "Kingston Fury Beast 32GB", "Rp2.300.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Kingston"),
        Product(112, "Kingston Fury Renegade 16GB", "Rp1.600.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Kingston"),
        Product(113, "Kingston Fury Renegade 32GB", "Rp3.200.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Kingston"),
        Product(114, "Kingston ValueRAM 8GB", "Rp500.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Kingston"),

        // ================= RAM G.SKILL =================
        Product(120, "G.SKILL Trident Z RGB 16GB", "Rp1.800.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="G.SKILL"),
        Product(121, "G.SKILL Trident Z RGB 32GB", "Rp3.400.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="G.SKILL"),
        Product(122, "G.SKILL Ripjaws V 16GB", "Rp1.300.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="G.SKILL"),
        Product(123, "G.SKILL Ripjaws V 32GB", "Rp2.600.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="G.SKILL"),
        Product(124, "G.SKILL Trident Z5 DDR5 32GB", "Rp3.800.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="G.SKILL"),

        // ================= RAM CRUCIAL =================
        Product(130, "Crucial DDR4 8GB", "Rp450.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Crucial"),
        Product(131, "Crucial DDR4 16GB", "Rp900.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Crucial"),
        Product(132, "Crucial Ballistix 16GB", "Rp1.200.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Crucial"),
        Product(133, "Crucial Ballistix 32GB", "Rp2.500.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Crucial"),
        Product(134, "Crucial DDR5 32GB", "Rp2.800.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Crucial"),

        // ================= RAM PATRIOT =================
        Product(140, "Patriot Viper Steel 16GB", "Rp1.100.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Patriot"),
        Product(141, "Patriot Viper Steel 32GB", "Rp2.200.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Patriot"),
        Product(142, "Patriot Viper RGB 16GB", "Rp1.300.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Patriot"),
        Product(143, "Patriot Signature 8GB", "Rp400.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Patriot"),
        Product(144, "Patriot Viper DDR5 32GB", "Rp2.900.000", R.drawable.generic_product, category="komponen", subcategory="ram", brand="Patriot"),

        // ================= CPU INTEL =================
        Product(200, "Intel Core i5-13400F", "Rp3.200.000", R.drawable.i9_13900ks, category="komponen", subcategory="cpu", brand="Intel"),
        Product(201, "Intel Core i7-13700K", "Rp6.500.000", R.drawable.i9_13900ks, category="komponen", subcategory="cpu", brand="Intel"),
        Product(202, "Intel Core i9-13900KS", "Rp12.606.912", R.drawable.i9_13900ks, category="komponen", subcategory="cpu", brand="Intel"),
        Product(203, "Intel Core i3-12100F", "Rp1.800.000", R.drawable.i9_13900ks, category="komponen", subcategory="cpu", brand="Intel"),
        Product(204, "Intel Core i5-12400F", "Rp2.800.000", R.drawable.i9_13900ks, category="komponen", subcategory="cpu", brand="Intel"),

        // ================= CPU AMD =================
        Product(300, "AMD Ryzen 5 5600X", "Rp2.800.000", R.drawable.i9_13900ks, category="komponen", subcategory="cpu", brand="AMD"),
        Product(301, "AMD Ryzen 7 5800X", "Rp4.500.000", R.drawable.i9_13900ks, category="komponen", subcategory="cpu", brand="AMD"),
        Product(302, "AMD Ryzen 9 5900X", "Rp7.500.000", R.drawable.i9_13900ks, category="komponen", subcategory="cpu", brand="AMD"),
        Product(303, "AMD Ryzen 5 7600X", "Rp3.800.000", R.drawable.i9_13900ks, category="komponen", subcategory="cpu", brand="AMD"),
        Product(304, "AMD Ryzen 9 7950X", "Rp10.500.000", R.drawable.i9_13900ks, category="komponen", subcategory="cpu", brand="AMD"),

        // ================= GPU NVIDIA =================
        Product(400, "NVIDIA RTX 4060", "Rp6.500.000", R.drawable.rtx50901, category="komponen", subcategory="gpu", brand="NVIDIA"),
        Product(401, "NVIDIA RTX 4070", "Rp10.000.000", R.drawable.rtx50901, category="komponen", subcategory="gpu", brand="NVIDIA"),
        Product(402, "NVIDIA RTX 4080", "Rp20.000.000", R.drawable.rtx50901, category="komponen", subcategory="gpu", brand="NVIDIA"),
        Product(403, "NVIDIA RTX 4090", "Rp30.000.000", R.drawable.rtx50901, category="komponen", subcategory="gpu", brand="NVIDIA"),
        Product(404, "NVIDIA RTX 5090", "Rp57.489.000", R.drawable.rtx50901, category="komponen", subcategory="gpu", brand="NVIDIA"),

        // ================= GPU AMD =================
        Product(500, "AMD RX 7600", "Rp5.500.000", R.drawable.rtx50901, category="komponen", subcategory="gpu", brand="AMD"),
        Product(501, "AMD RX 7700 XT", "Rp7.500.000", R.drawable.rtx50901, category="komponen", subcategory="gpu", brand="AMD"),
        Product(502, "AMD RX 7800 XT", "Rp9.000.000", R.drawable.rtx50901, category="komponen", subcategory="gpu", brand="AMD"),
        Product(503, "AMD RX 7900 XT", "Rp12.000.000", R.drawable.rtx50901, category="komponen", subcategory="gpu", brand="AMD"),
        Product(504, "AMD RX 7900 XTX", "Rp15.000.000", R.drawable.rtx50901, category="komponen", subcategory="gpu", brand="AMD"),

        // ================= MOTHERBOARD ASUS =================
        Product(1000, "ASUS ROG Strix B550-F Gaming", "Rp3.500.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="ASUS"),
        Product(1001, "ASUS TUF Gaming B550M-Plus", "Rp2.800.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="ASUS"),
        Product(1002, "ASUS Prime B660M-A", "Rp2.400.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="ASUS"),
        Product(1003, "ASUS ROG Maximus Z790 Hero", "Rp9.500.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="ASUS"),
        Product(1004, "ASUS Prime A520M-K", "Rp1.300.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="ASUS"),

        // ================= MOTHERBOARD MSI =================
        Product(1100, "MSI B550 Tomahawk", "Rp3.200.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="MSI"),
        Product(1101, "MSI B660M Mortar", "Rp2.900.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="MSI"),
        Product(1102, "MSI MPG Z790 Carbon", "Rp8.000.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="MSI"),
        Product(1103, "MSI A520M-A Pro", "Rp1.200.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="MSI"),
        Product(1104, "MSI B450 Gaming Plus", "Rp1.800.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="MSI"),

        // ================= MOTHERBOARD GIGABYTE =================
        Product(1200, "Gigabyte B550 Aorus Elite", "Rp3.000.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="Gigabyte"),
        Product(1201, "Gigabyte B660M DS3H", "Rp2.200.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="Gigabyte"),
        Product(1202, "Gigabyte Z790 Aorus Master", "Rp9.000.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="Gigabyte"),
        Product(1203, "Gigabyte A520M S2H", "Rp1.200.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="Gigabyte"),
        Product(1204, "Gigabyte B450M DS3H", "Rp1.500.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="Gigabyte"),

        // ================= MOTHERBOARD ASROCK =================
        Product(1300, "ASRock B550 Steel Legend", "Rp2.900.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="ASRock"),
        Product(1301, "ASRock B660M Pro RS", "Rp2.400.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="ASRock"),
        Product(1302, "ASRock Z790 Taichi", "Rp8.500.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="ASRock"),
        Product(1303, "ASRock A520M HDV", "Rp1.100.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="ASRock"),
        Product(1304, "ASRock B450 Steel Legend", "Rp1.700.000", R.drawable.generic_product, category="komponen", subcategory="motherboard", brand="ASRock"),

        // ================= STORAGE SAMSUNG =================
        Product(600, "Samsung 970 EVO Plus 1TB", "Rp1.800.000", R.drawable.samsung_990_pro, category="komponen", subcategory="storage", brand="Samsung"),
        Product(601, "Samsung 980 1TB", "Rp1.800.000", R.drawable.samsung_990_pro, category="komponen", subcategory="storage", brand="Samsung"),
        Product(602, "Samsung 990 Pro 1TB", "Rp2.499.000", R.drawable.samsung_990_pro, category="komponen", subcategory="storage", brand="Samsung"),
        Product(603, "Samsung 870 EVO 1TB", "Rp1.600.000", R.drawable.samsung_990_pro, category="komponen", subcategory="storage", brand="Samsung"),
        Product(604, "Samsung 860 EVO 500GB", "Rp900.000", R.drawable.samsung_990_pro, category="komponen", subcategory="storage", brand="Samsung"),

        // ================= STORAGE WESTERN DIGITAL =================
        Product(610, "WD Blue SN570 1TB", "Rp1.400.000", R.drawable.generic_product, category="komponen", subcategory="storage", brand="Western Digital"),
        Product(611, "WD Black SN770 1TB", "Rp1.900.000", R.drawable.generic_product, category="komponen", subcategory="storage", brand="Western Digital"),
        Product(612, "WD Black SN850X 1TB", "Rp2.300.000", R.drawable.generic_product, category="komponen", subcategory="storage", brand="Western Digital"),
        Product(613, "WD Blue HDD 2TB", "Rp900.000", R.drawable.generic_product, category="komponen", subcategory="storage", brand="Western Digital"),
        Product(614, "WD Red Plus 4TB", "Rp1.800.000", R.drawable.generic_product, category="komponen", subcategory="storage", brand="Western Digital"),

        // ================= STORAGE SEAGATE =================
        Product(620, "Seagate Barracuda 1TB", "Rp700.000", R.drawable.generic_product, category="komponen", subcategory="storage", brand="Seagate"),
        Product(621, "Seagate Barracuda 2TB", "Rp1.200.000", R.drawable.generic_product, category="komponen", subcategory="storage", brand="Seagate"),
        Product(622, "Seagate FireCuda 520 1TB", "Rp2.000.000", R.drawable.generic_product, category="komponen", subcategory="storage", brand="Seagate"),
        Product(623, "Seagate IronWolf 4TB", "Rp2.000.000", R.drawable.generic_product, category="komponen", subcategory="storage", brand="Seagate"),
        Product(624, "Seagate SkyHawk 2TB", "Rp1.100.000", R.drawable.generic_product, category="komponen", subcategory="storage", brand="Seagate"),

        // ================= STORAGE CRUCIAL =================
        Product(630, "Crucial P3 1TB", "Rp1.400.000", R.drawable.generic_product, category="komponen", subcategory="storage", brand="Crucial"),
        Product(631, "Crucial P5 Plus 1TB", "Rp1.900.000", R.drawable.generic_product, category="komponen", subcategory="storage", brand="Crucial"),
        Product(632, "Crucial MX500 1TB", "Rp1.500.000", R.drawable.generic_product, category="komponen", subcategory="storage", brand="Crucial"),
        Product(633, "Crucial BX500 480GB", "Rp700.000", R.drawable.generic_product, category="komponen", subcategory="storage", brand="Crucial"),
        Product(634, "Crucial P2 500GB", "Rp800.000", R.drawable.generic_product, category="komponen", subcategory="storage", brand="Crucial"),

        // ================= PSU CORSAIR =================
        Product(700, "Corsair RM650", "Rp1.500.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="Corsair"),
        Product(701, "Corsair RM750", "Rp1.800.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="Corsair"),
        Product(702, "Corsair RM850", "Rp2.200.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="Corsair"),
        Product(703, "Corsair HX1000", "Rp3.500.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="Corsair"),
        Product(704, "Corsair CV550", "Rp900.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="Corsair"),

        // ================= PSU SEASONIC =================
        Product(710, "Seasonic S12III 550", "Rp1.000.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="Seasonic"),
        Product(711, "Seasonic Focus GX-650", "Rp1.800.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="Seasonic"),
        Product(712, "Seasonic Focus GX-750", "Rp2.000.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="Seasonic"),
        Product(713, "Seasonic Prime TX-850", "Rp3.200.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="Seasonic"),
        Product(714, "Seasonic Prime PX-1000", "Rp3.800.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="Seasonic"),

        // ================= PSU EVGA =================
        Product(720, "EVGA 600 W1", "Rp900.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="EVGA"),
        Product(721, "EVGA 650 B5", "Rp1.300.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="EVGA"),
        Product(722, "EVGA 750 G5", "Rp1.900.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="EVGA"),
        Product(723, "EVGA 850 G5", "Rp2.500.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="EVGA"),
        Product(724, "EVGA 1000 G6", "Rp3.200.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="EVGA"),

        // ================= PSU COOLER MASTER =================
        Product(730, "Cooler Master MWE 550", "Rp900.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="Cooler Master"),
        Product(731, "Cooler Master MWE 650", "Rp1.200.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="Cooler Master"),
        Product(732, "Cooler Master MWE 750", "Rp1.500.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="Cooler Master"),
        Product(733, "Cooler Master V850 Gold", "Rp2.400.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="Cooler Master"),
        Product(734, "Cooler Master V1000 Platinum", "Rp3.800.000", R.drawable.generic_product, category="komponen", subcategory="power supply", brand="Cooler Master"),

        // ================= CASING THERMALTAKE =================
        Product(800, "Thermaltake View 51", "Rp2.200.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="Thermaltake"),
        Product(801, "Thermaltake Core P3", "Rp1.800.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="Thermaltake"),
        Product(802, "Thermaltake Versa H18", "Rp900.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="Thermaltake"),
        Product(803, "Thermaltake Divider 300", "Rp1.500.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="Thermaltake"),
        Product(804, "Thermaltake S300 TG", "Rp1.300.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="Thermaltake"),

        // ================= CASING NZXT =================
        Product(810, "NZXT H510", "Rp1.500.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="NZXT"),
        Product(811, "NZXT H510 Flow", "Rp1.700.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="NZXT"),
        Product(812, "NZXT H710", "Rp2.200.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="NZXT"),
        Product(813, "NZXT H5 Elite", "Rp2.000.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="NZXT"),
        Product(814, "NZXT H9 Flow", "Rp2.800.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="NZXT"),

        // ================= CASING CORSAIR =================
        Product(820, "Corsair 4000D Airflow", "Rp1.700.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="Corsair"),
        Product(821, "Corsair 5000D Airflow", "Rp2.500.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="Corsair"),
        Product(822, "Corsair iCUE 220T", "Rp1.800.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="Corsair"),
        Product(823, "Corsair Crystal 680X", "Rp3.500.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="Corsair"),
        Product(824, "Corsair Carbide 275R", "Rp1.400.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="Corsair"),

        // ================= CASING COOLER MASTER =================
        Product(830, "Cooler Master TD500 Mesh", "Rp1.600.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="Cooler Master"),
        Product(831, "Cooler Master MasterBox MB511", "Rp1.300.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="Cooler Master"),
        Product(832, "Cooler Master HAF 700", "Rp4.500.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="Cooler Master"),
        Product(833, "Cooler Master NR200P", "Rp1.800.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="Cooler Master"),
        Product(834, "Cooler Master Q300L", "Rp900.000", R.drawable.thermal1, category="komponen", subcategory="casing", brand="Cooler Master"),

        // ================= MONITOR MSI =================
        Product(900, "MSI MAG 251RX 24.5\"", "Rp2.200.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="MSI"),
        Product(901, "MSI G2412 24\"", "Rp2.000.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="MSI"),
        Product(902, "MSI Optix G273QF 27\"", "Rp3.800.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="MSI"),
        Product(903, "MSI MPG ARTYMIS 273CQR", "Rp5.500.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="MSI"),
        Product(904, "MSI MAG 274QRF-QD", "Rp6.000.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="MSI"),

        // ================= MONITOR ASUS =================
        Product(910, "ASUS TUF VG249Q1A", "Rp2.800.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="ASUS"),
        Product(911, "ASUS VG248QG", "Rp2.600.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="ASUS"),
        Product(912, "ASUS TUF VG27AQ", "Rp4.500.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="ASUS"),
        Product(913, "ASUS ROG Swift PG259QN", "Rp8.000.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="ASUS"),
        Product(914, "ASUS ProArt PA278QV", "Rp5.000.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="ASUS"),

        // ================= MONITOR LG =================
        Product(920, "LG UltraGear 24GN600", "Rp2.700.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="LG"),
        Product(921, "LG UltraGear 27GL850", "Rp6.400.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="LG"),
        Product(922, "LG 24MP400", "Rp1.800.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="LG"),
        Product(923, "LG UltraGear 27GP850", "Rp7.000.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="LG"),
        Product(924, "LG 32GN650-B", "Rp5.200.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="LG"),

        // ================= MONITOR SAMSUNG =================
        Product(930, "Samsung Odyssey G3", "Rp2.500.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="Samsung"),
        Product(931, "Samsung Odyssey G5", "Rp4.500.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="Samsung"),
        Product(932, "Samsung Odyssey G7", "Rp8.500.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="Samsung"),
        Product(933, "Samsung Smart Monitor M5", "Rp3.000.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="Samsung"),
        Product(934, "Samsung Odyssey Neo G7", "Rp12.000.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="Samsung"),

        // ================= MONITOR AOC =================
        Product(940, "AOC 24G2 24\"", "Rp2.500.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="AOC"),
        Product(941, "AOC C24G2 Curved", "Rp2.800.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="AOC"),
        Product(942, "AOC 27G2 27\"", "Rp3.500.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="AOC"),
        Product(943, "AOC CQ32G1 32\"", "Rp5.500.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="AOC"),
        Product(944, "AOC AGON Pro AG274QG", "Rp9.000.000", R.drawable.monitor251, category="komponen", subcategory="monitor", brand="AOC"),
    )


    fun getSubCategoriesForKomponen() = listOf(
        "ram", "cpu", "gpu", "motherboard",
        "storage", "power supply", "casing", "cooling", "monitor"
    )

    fun getBrandsForSubcategory(sub: String): List<String> {
        return allProducts
            .filter { it.subcategory.equals(sub, true) }
            .mapNotNull { it.brand }
            .distinct()
    }

    // 🔹 BRAND LAPTOP
    fun getLaptopBrands(): List<String> {
        return allProducts
            .filter { it.category.equals("laptop", true) }
            .mapNotNull { it.brand }
            .distinct()
    }

    // 🔹 BRAND KOMPUTER
    fun getKomputerBrands(): List<String> {
        return allProducts
            .filter { it.category.equals("komputer", true) }
            .mapNotNull { it.brand }
            .distinct()
    }

    fun getProducts(category: String?, subcategory: String?, brand: String?): List<Product> {
        return allProducts.filter { p ->
            val matchCategory = category == null || p.category.equals(category, true)
            val matchSub = subcategory == null || p.subcategory.equals(subcategory, true)
            val matchBrand = brand == null || p.brand.equals(brand, true)

            matchCategory && matchSub && matchBrand
        }
    }
}