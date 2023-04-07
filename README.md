# Vlazma-Store
UAS Java Lanjutan
========================
- Vlazma Store
======================================================
- Aplikasi Ini Berjalan Di Spring Boot V 3.05
- Aplikasi Ini Berjalan Di Java Versi 17
- Aplikasi Ini Menggunakan MySql sebagai databasenya
  Maka harap pastikan XAMPP anda terinstall dan port
  MySql nya dinyalakan
======================================================
 PENTING!!!
 Sebelum Menggunakan Aplikasi Ini Harap Lakukan :
- Aplikasi Ini memiliki akses terhadap API RajaOngkir
  Sehingga memerlukan IP Server Address
  Hubungi 082113923231 untuk mengirim IP Address Anda,
  Kami akan mendaftarkan IP Address Anda Sebagai Perujuk
  guna Akses ke Api Raja Ongkir.
- Apabila End Point tidak bisa diakses, harap menambahkan
  Ekstensi Allow Cors di Google Chrome Anda
=========================================================
    INFORMASI!
- Semua Dokumentasi API bisa diakses di:
  http://localhost:8080/swagger-ui/index.html
  termasuk API Raja Ongkir (Di Bagian Ekspedisi)
==========================================================
    Petunjuk Penggunaan
- Hal pertama yang dilakukan adalah menambahkan Role
  Anda dapat menggunakan Endpoint
  vlazma/role/
  Dengan methode post
  (terdapat di Swagger)
  Data yang dibutuhkan adalah
  - ADMIN
  - CUSTOMER
  Isi secara berurutan
- Untuk Registrasi Dan Otentikasi
  Anda dapat menggunakan Endpoint
  vlazma/auth/register  ---> untuk registrasi
  vllazma/auth/authentication ---> untuk otentikasi
- Saat Menambahkan Address
  Untuk Province & City
  Silahkan result dari Endpoint yang ada di Ekspedisi Controller:
  Province : vlazma/province
  City : vlazma/city
  Masukan Ambil Nilai :
  Province : {prodvince_id}|{provcince}
  contoh -> 9|Jawa Barat
  Province : {city_id}|{city_name}
  contoh -> 104|Kabupaten Cianjer
  




