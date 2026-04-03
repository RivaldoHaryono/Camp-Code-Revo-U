# Rencana Implementasi: Digital Kasir (POS System)

## Ikhtisar

Implementasi aplikasi POS berbasis web menggunakan HTML, CSS, dan JavaScript murni (tanpa framework). Arsitektur SPA dengan hash-based router, LocalStorage sebagai penyimpanan data, dan dua peran pengguna (Admin & Kasir). Semua file output berada di folder `src/`, dengan entry point `index.html` di root.

## Tasks

- [x] 1. Setup struktur proyek dan infrastruktur inti
  - Buat folder `src/` dengan subfolder: `core/`, `models/`, `views/`, `components/`, `styles/`
  - Buat `index.html` di root sebagai entry point tunggal SPA
  - Implementasi `src/core/utils.js`: helper `formatCurrency`, `formatDate`, `generateId` (nanoid-style), `formatTransactionNumber`, `debounce`
  - Implementasi `src/core/store.js`: abstraksi LocalStorage dengan `get`, `set`, `remove`, `getAll`, `importAll`, `getUsage`
  - Implementasi `src/core/eventBus.js`: pub/sub sederhana dengan `on`, `emit`, `off`
  - Implementasi `src/core/auth.js`: `login`, `logout`, `currentUser`, `isLoggedIn`, `hasRole`, `guard` — sesi di sessionStorage
  - Implementasi `src/core/router.js`: hash-based router dengan `register`, `navigate`, `init`, dan guard `requiredRole`
  - Inisialisasi data default: akun admin awal, pengaturan toko default
  - _Requirements: 1.1, 1.7, 1.8, 1.9, 16.1_

  - [x]* 1.1 Tulis property test untuk autentikasi konsisten (Property 1)
    - **Property 1: Autentikasi Konsisten dengan Validitas Credential**
    - **Validates: Requirements 1.2, 1.3**

  - [x]* 1.2 Tulis property test untuk persistensi sesi login (Property 2)
    - **Property 2: Persistensi Sesi Login**
    - **Validates: Requirements 1.7**

  - [x]* 1.3 Tulis property test untuk guard route (Property 3)
    - **Property 3: Guard Route Melindungi Semua Route Terproteksi**
    - **Validates: Requirements 1.8**

- [x] 2. Design system dan CSS
  - Implementasi `src/styles/base.css`: CSS reset, variabel CSS (warna, tipografi, spacing), font Inter
  - Implementasi `src/styles/themes.css`: variabel light mode dan dark mode, class `[data-theme="dark"]`
  - Implementasi `src/styles/layout.css`: grid utama, sidebar (240px/64px collapsed), header, area konten
  - Implementasi `src/styles/components.css`: button variants, card, badge, form elements, table, modal, toast
  - Implementasi `src/styles/print.css`: stylesheet khusus cetak untuk struk dan laporan
  - _Requirements: 18.1, 18.3, 18.4, 20.2_

- [x] 3. Komponen UI reusable
  - Implementasi `src/components/sidebar.js`: navigasi sidebar dengan menu per peran, toggle collapse, highlight aktif
  - Implementasi `src/components/modal.js`: `Modal.show({ title, content, onConfirm, onCancel })`, `Modal.close()`
  - Implementasi `src/components/toast.js`: `Toast.success`, `Toast.error`, `Toast.warning` dengan auto-dismiss 3 detik
  - Implementasi `src/components/table.js`: tabel data reusable dengan sorting dan pagination
  - Implementasi `src/components/chart.js`: `Chart.bar`, `Chart.line`, `Chart.donut` menggunakan Canvas API native
  - _Requirements: 17.1, 18.5, 18.6_

- [ ] 4. Model data
  - [x] 4.1 Implementasi `src/models/user.model.js`
    - CRUD pengguna, hash password (SHA-256 via SubtleCrypto atau btoa+salt), validasi username unik
    - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5, 2.6_

  - [ ]* 4.2 Tulis property test untuk keunikan username (Property 4)
    - **Property 4: Username Pengguna Bersifat Unik**
    - **Validates: Requirements 2.2, 2.3**

  - [ ]* 4.3 Tulis property test untuk invariant minimal satu admin aktif (Property 5)
    - **Property 5: Invariant Minimal Satu Admin Aktif**
    - **Validates: Requirements 2.6**

  - [x] 4.4 Implementasi `src/models/category.model.js`
    - CRUD kategori, validasi nama unik (case-insensitive), hitung jumlah produk per kategori
    - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.5_

  - [ ]* 4.5 Tulis property test untuk keunikan nama kategori (Property 6)
    - **Property 6: Nama Kategori Bersifat Unik**
    - **Validates: Requirements 3.3**

  - [ ]* 4.6 Tulis property test untuk konsistensi jumlah produk per kategori (Property 7)
    - **Property 7: Jumlah Produk per Kategori Konsisten**
    - **Validates: Requirements 3.5**

  - [x] 4.7 Implementasi `src/models/product.model.js`
    - CRUD produk, generate SKU otomatis, validasi SKU unik, soft delete, riwayat harga, filter & pencarian
    - _Requirements: 4.1, 4.2, 4.3, 4.4, 4.5, 4.6, 4.7, 4.8_

  - [ ]* 4.8 Tulis property test untuk keunikan SKU produk (Property 8)
    - **Property 8: SKU Produk Bersifat Unik**
    - **Validates: Requirements 4.3**

  - [ ]* 4.9 Tulis property test untuk soft delete produk (Property 9)
    - **Property 9: Soft Delete Produk yang Ada di Transaksi**
    - **Validates: Requirements 4.7**

  - [x] 4.10 Implementasi `src/models/voucher.model.js`
    - CRUD voucher, validasi kode unik, cek kadaluarsa, cek kuota, `incrementUsage`, `decrementUsage`
    - _Requirements: 19.1, 19.2, 19.3, 19.4, 19.5_

  - [ ]* 4.11 Tulis property test untuk validasi voucher komprehensif (Property 20)
    - **Property 20: Validasi Voucher Komprehensif**
    - **Validates: Requirements 19.2, 19.3**

  - [x] 4.12 Implementasi `src/models/shift.model.js`
    - Buka/tutup shift, hitung `expectedBalance`, `balanceDiff`, ringkasan shift, riwayat shift
    - _Requirements: 14.1, 14.2, 14.3, 14.4, 14.5_

  - [ ]* 4.13 Tulis property test untuk kalkulasi selisih kas shift (Property 21)
    - **Property 21: Kalkulasi Selisih Kas Shift Selalu Akurat**
    - **Validates: Requirements 14.4**

  - [x] 4.14 Implementasi `src/models/transaction.model.js`
    - Buat transaksi, `processTransaction` (kurangi stok, update voucher, emit event), void, refund, query by date/shift/kasir
    - _Requirements: 8.1, 8.5, 8.6, 8.7, 8.8, 11.1, 11.4, 11.5, 11.6_

  - [ ] 4.15 Implementasi `src/models/settings.model.js`
    - Get/set pengaturan toko, nilai default, emit `settings:changed` saat berubah
    - _Requirements: 15.1, 15.2, 15.3, 15.4, 15.5, 15.6, 15.7_

- [ ] 5. Checkpoint — Pastikan semua model dan infrastruktur inti berfungsi
  - Pastikan semua tests lulus, tanyakan kepada user jika ada pertanyaan.

- [ ] 6. Halaman login dan autentikasi
  - Implementasi `src/views/login/login.view.js` dan `src/views/login/login.html` (template)
  - Form login dengan validasi, tampilkan pesan error "Username atau password salah"
  - Redirect ke `#/dashboard` (Admin) atau `#/kasir` (Kasir) setelah login berhasil
  - Tombol logout di sidebar yang mengakhiri sesi dan redirect ke `#/login`
  - _Requirements: 1.1, 1.2, 1.3, 1.4, 1.5, 1.6_

- [ ] 7. Dashboard bisnis
  - Implementasi `src/views/dashboard/dashboard.view.js`
  - Tampilkan KPI hari ini: total omzet, jumlah transaksi, rata-rata nilai transaksi, total item terjual
  - Grafik penjualan 7 hari terakhir menggunakan `Chart.line`
  - Daftar 5 produk terlaris hari ini
  - Widget stok rendah dengan tautan ke halaman stok
  - Ringkasan metode pembayaran hari ini menggunakan `Chart.donut`
  - Subscribe ke event `transaction:completed` untuk update KPI real-time tanpa refresh
  - _Requirements: 13.1, 13.2, 13.3, 13.4, 13.5, 13.6_

- [ ] 8. Manajemen kategori
  - Implementasi `src/views/categories/categories.view.js`
  - Tabel kategori dengan jumlah produk per kategori
  - Form tambah/edit kategori dengan validasi nama unik
  - Konfirmasi hapus kategori yang masih memiliki produk
  - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.5_

- [ ] 9. Manajemen produk
  - Implementasi `src/views/products/products.view.js` (daftar) dan `products-form.view.js` (tambah/edit)
  - Grid/list produk dengan filter kategori dan status stok
  - Pencarian produk real-time (debounce < 300ms) berdasarkan nama atau SKU
  - Form produk: nama, SKU (auto-generate + manual), kategori, harga jual, harga beli, stok, satuan, deskripsi, upload gambar (JPG/PNG/WebP, maks 2MB → Base64)
  - Soft delete untuk produk yang ada di riwayat transaksi
  - _Requirements: 4.1, 4.2, 4.3, 4.4, 4.5, 4.6, 4.7, 4.8_

- [ ] 10. Antarmuka kasir (POS interface)
  - [ ] 10.1 Implementasi `src/views/kasir/kasir.view.js` — layout dua panel (produk 60% + keranjang 40%)
    - Grid produk aktif, search bar dengan debounce, filter tab kategori
    - Simulasi barcode scan: SKU exact match langsung tambah ke keranjang
    - _Requirements: 5.1, 5.9, 6.1, 6.2, 6.3, 6.4, 6.5_

  - [ ] 10.2 Implementasi logika keranjang belanja
    - Tambah produk (akumulatif), ubah kuantitas (validasi stok), hapus item, kosongkan keranjang (konfirmasi)
    - Panel total real-time: subtotal, diskon item, diskon transaksi, PPN, total
    - Input kode voucher dengan validasi
    - Tombol "Proses Pembayaran" aktif jika keranjang tidak kosong
    - _Requirements: 5.2, 5.3, 5.4, 5.5, 5.6, 5.7, 5.8, 5.10, 7.1, 7.2, 7.3, 7.7_

  - [ ]* 10.3 Tulis property test untuk penambahan produk ke keranjang akumulatif (Property 10)
    - **Property 10: Penambahan Produk ke Keranjang Akumulatif**
    - **Validates: Requirements 5.2, 5.3, 5.4**

  - [ ]* 10.4 Tulis property test untuk kuantitas keranjang tidak melebihi stok (Property 11)
    - **Property 11: Kuantitas Keranjang Tidak Melebihi Stok**
    - **Validates: Requirements 5.5**

  - [ ]* 10.5 Tulis property test untuk kalkulasi total keranjang (Property 12)
    - **Property 12: Kalkulasi Total Keranjang Selalu Akurat**
    - **Validates: Requirements 5.8, 7.1**

  - [ ]* 10.6 Tulis property test untuk diskon tidak membuat total negatif (Property 13)
    - **Property 13: Diskon Tidak Boleh Membuat Total Negatif**
    - **Validates: Requirements 7.6**

  - [ ]* 10.7 Tulis property test untuk produk stok nol tidak bisa ditambah ke keranjang (Property 16)
    - **Property 16: Produk Stok Nol Tidak Bisa Ditambah ke Keranjang**
    - **Validates: Requirements 10.7**

- [ ] 11. Proses pembayaran dan struk digital
  - [ ] 11.1 Implementasi modal pembayaran di `src/views/kasir/payment.view.js`
    - Pilih metode pembayaran: Tunai, Kartu, Transfer, QRIS
    - Input jumlah bayar tunai → hitung kembalian real-time
    - Validasi pembayaran kurang dari total
    - Dukungan split payment (dua metode)
    - Cek shift aktif sebelum proses; tampilkan peringatan jika belum buka shift
    - _Requirements: 8.1, 8.2, 8.3, 8.4, 8.7, 14.6_

  - [ ]* 11.2 Tulis property test untuk kalkulasi kembalian tunai (Property 14)
    - **Property 14: Kalkulasi Kembalian Tunai Selalu Benar**
    - **Validates: Requirements 8.2, 8.3**

  - [ ]* 11.3 Tulis property test untuk konsistensi stok setelah transaksi dan void (Property 15)
    - **Property 15: Konsistensi Stok Setelah Transaksi dan Void**
    - **Validates: Requirements 8.5, 10.2, 11.4**

  - [ ] 11.4 Implementasi `src/components/receipt.js`
    - `Receipt.render(transaction)` → HTML string struk lengkap (nama toko, alamat, nomor transaksi, item, total, kembalian, logo jika ada)
    - `Receipt.print(transaction)` → `window.print()` dengan print CSS
    - `Receipt.downloadPDF(transaction)` → print-to-PDF via browser
    - `Receipt.copyText(transaction)` → `navigator.clipboard.writeText()` + Toast konfirmasi
    - _Requirements: 9.1, 9.2, 9.3, 9.4, 9.5, 9.6_

- [ ] 12. Checkpoint — Pastikan alur transaksi end-to-end berfungsi
  - Pastikan semua tests lulus, tanyakan kepada user jika ada pertanyaan.

- [ ] 13. Manajemen stok dan riwayat pergerakan
  - Implementasi `src/views/stock/stock.view.js`
  - Daftar produk dengan stok terkini, indikator stok rendah (badge warning)
  - Form penyesuaian stok manual: jumlah, alasan, simpan ke `StockMovement`
  - Halaman riwayat pergerakan stok per produk (filter by produk)
  - Emit `stock:low` saat stok ≤ `minStock` → badge notifikasi di sidebar
  - _Requirements: 10.1, 10.3, 10.4, 10.5, 10.6, 10.7_

- [ ] 14. Riwayat transaksi (void & refund)
  - Implementasi `src/views/transactions/transactions.view.js` (daftar) dan `transaction-detail.view.js` (detail)
  - Tabel transaksi dengan filter: rentang tanggal, kasir, metode pembayaran, status
  - Detail transaksi: semua item, total, metode bayar, status
  - Void transaksi: konfirmasi + input alasan → kembalikan stok → update status
  - Refund: buat transaksi refund terpisah yang terhubung ke transaksi asal
  - Cetak ulang struk dari riwayat
  - _Requirements: 11.1, 11.2, 11.3, 11.4, 11.5, 11.6, 11.7_

  - [ ]* 14.1 Tulis property test untuk void transaksi bersifat idempoten (Property 17)
    - **Property 17: Void Transaksi Bersifat Idempoten (Tidak Bisa Void Dua Kali)**
    - **Validates: Requirements 11.5**

- [ ] 15. Laporan penjualan dengan grafik
  - Implementasi `src/views/reports/reports.view.js`
  - Filter periode: harian, mingguan, bulanan, rentang tanggal kustom
  - KPI: total omzet, jumlah transaksi, rata-rata nilai transaksi, produk terlaris
  - Grafik tren penjualan (`Chart.line`), per kategori (`Chart.bar`), per metode pembayaran (`Chart.donut`)
  - Laporan performa kasir: transaksi dan omzet per kasir
  - Ekspor CSV transaksi sesuai filter aktif
  - Ekspor daftar produk ke CSV
  - Tombol cetak laporan dengan print CSS (header: nama toko, periode, tanggal cetak)
  - _Requirements: 12.1, 12.2, 12.3, 12.4, 12.5, 12.6, 12.7, 12.8, 20.1, 20.2, 20.3, 20.4_

  - [ ]* 15.1 Tulis property test untuk round-trip ekspor CSV transaksi (Property 22)
    - **Property 22: Round-Trip Ekspor CSV Transaksi**
    - **Validates: Requirements 12.6**

- [ ] 16. Manajemen shift kasir
  - Implementasi `src/views/shifts/shifts.view.js`
  - Form buka shift: input saldo kas awal, simpan waktu mulai dan kasir
  - Form tutup shift: input saldo kas akhir, tampilkan ringkasan (total transaksi, omzet, per metode bayar, selisih kas)
  - Tabel riwayat semua shift
  - _Requirements: 14.1, 14.2, 14.3, 14.4, 14.5, 14.6_

- [ ] 17. Manajemen voucher dan kupon
  - Implementasi `src/views/vouchers/vouchers.view.js`
  - CRUD voucher: kode, tipe diskon (persen/nominal), nilai, min pembelian, maks diskon, tanggal berlaku, tanggal kadaluarsa, batas penggunaan
  - Daftar voucher aktif dengan sisa kuota
  - _Requirements: 19.1, 19.2, 19.3, 19.4, 19.5_

- [ ] 18. Manajemen pengguna
  - Implementasi `src/views/users/users.view.js`
  - Tabel pengguna dengan status aktif/nonaktif
  - Form tambah/edit pengguna: username, password, peran, nama lengkap
  - Nonaktifkan pengguna (cegah jika admin terakhir)
  - Ubah password pengguna
  - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5, 2.6_

- [ ] 19. Pengaturan toko
  - Implementasi `src/views/settings/settings.view.js`
  - Form pengaturan: nama toko, alamat, telepon, email, logo (upload → Base64), pesan footer struk
  - Konfigurasi pajak: aktif/nonaktif, persentase PPN, nama pajak
  - Konfigurasi batas stok minimum default
  - Konfigurasi format nomor transaksi
  - Konfigurasi metode pembayaran aktif/nonaktif
  - Simpan dan emit `settings:changed` untuk update seluruh sistem
  - _Requirements: 15.1, 15.2, 15.3, 15.4, 15.5, 15.6, 15.7_

- [ ] 20. Backup & restore data
  - Implementasi di halaman pengaturan atau halaman tersendiri `src/views/settings/backup.view.js`
  - Tombol "Backup Data" → `Store.getAll()` → unduh file JSON
  - Upload file JSON → validasi schema → konfirmasi → `Store.importAll(data)` → reload
  - Tampilkan info ukuran data LocalStorage dan persentase kapasitas
  - Peringatan saat kapasitas mencapai 80%
  - _Requirements: 16.1, 16.2, 16.3, 16.4, 16.5, 16.6_

  - [ ]* 20.1 Tulis property test untuk round-trip backup dan restore data (Property 18)
    - **Property 18: Round-Trip Backup dan Restore Data**
    - **Validates: Requirements 16.2, 16.4**

- [ ] 21. Notifikasi dan alert
  - Implementasi `src/views/notifications/notifications.view.js` (panel notifikasi)
  - Badge notifikasi di sidebar untuk stok rendah (subscribe `stock:low`)
  - Panel/halaman notifikasi: daftar alert stok rendah yang belum ditangani
  - Tandai notifikasi sebagai "Ditangani" → hapus dari daftar aktif
  - Error handler untuk `storage:quota_exceeded` → tampilkan Toast error
  - _Requirements: 17.1, 17.2, 17.3, 17.4, 17.5_

- [ ] 22. Dark/light mode dan preferensi tema
  - Toggle dark/light mode di header atau sidebar
  - Simpan preferensi ke `settings.theme` di Store
  - Terapkan `data-theme="dark"` pada `<html>` saat inisialisasi berdasarkan preferensi tersimpan
  - _Requirements: 18.1, 18.2_

  - [ ]* 22.1 Tulis property test untuk persistensi preferensi tema (Property 19)
    - **Property 19: Persistensi Preferensi Tema**
    - **Validates: Requirements 18.1, 18.2**

- [ ] 23. Responsive design dan aksesibilitas
  - Pastikan layout responsif untuk tablet (768px+) dan desktop (1024px+) menggunakan CSS media queries
  - Sidebar collapse otomatis di layar kecil
  - Navigasi keyboard untuk elemen interaktif utama (tombol, form, tabel)
  - Indikator loading (spinner/skeleton) untuk operasi > 500ms
  - Kontras warna memadai di kedua mode tampilan
  - _Requirements: 18.3, 18.4, 18.5, 18.6_

- [ ] 24. Integrasi dan wiring akhir
  - Daftarkan semua route di `router.js` dengan `requiredRole` yang tepat
  - Pasang semua event listener EventBus antar modul (transaksi → dashboard, stok → notifikasi)
  - Pastikan inisialisasi data default berjalan saat pertama kali dibuka
  - Verifikasi guard navigasi bekerja untuk semua route terproteksi
  - _Requirements: 1.4, 1.5, 1.8, 13.6_

- [ ] 25. Checkpoint akhir — Pastikan semua tests lulus
  - Pastikan semua tests lulus, tanyakan kepada user jika ada pertanyaan.

## Catatan

- Tasks bertanda `*` bersifat opsional dan dapat dilewati untuk MVP yang lebih cepat
- Setiap task mereferensikan persyaratan spesifik untuk keterlacakan
- Property test menggunakan library [fast-check](https://github.com/dubzzz/fast-check) dengan minimum 100 iterasi per properti
- Unit test dan property test bersifat komplementer — keduanya direkomendasikan
- Checkpoint memastikan validasi inkremental di setiap fase utama
