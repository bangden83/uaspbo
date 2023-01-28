/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.dendi.uaspbo;

/*
import librari yang diperlukan
*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class Uaspbo {
    
    // Menyiapkan paramter JDBC untuk koneksi ke datbase
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/tugas_uas_pbo";
    static final String USER = "root";
    static final String PASS = "";

    // Menyiapkan objek yang diperlukan untuk mengelola database
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    
    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);

    public static void main(String[] args) {
        
        try {
        // register driver
        Class.forName(JDBC_DRIVER);

        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = conn.createStatement();

        while (!conn.isClosed()) {
            tampilkanMenu();
        }

        stmt.close();
        conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*
    membuat method untuk menampilkan menu
    */
    static void tampilkanMenu() {
    System.out.println("\n========= MENU UTAMA =========");
    System.out.println("1. Tambah Produk");
    System.out.println("2. Tampilkan Produk");
    System.out.println("3. Edit Data Produk");
    System.out.println("4. Hapus Data Produk");
    System.out.println("0. Keluar");
    System.out.println("");
    System.out.print("PILIHAN> ");

    try {
        int pilihan = Integer.parseInt(input.readLine());

        switch (pilihan) {
            case 0:
                System.exit(0);
                break;
            case 1:
                insertProduk();
                break;
            case 2:
                tampilkanProduk();
                break;
            case 3:
                editProduk();
                break;
            case 4:
                hapusProduk();
                break;
            default:
                System.out.println("Pilihan salah!");
        }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
    /*
    membuat method untuk menampilkan data
    */
    static void tampilkanProduk() {
    String sql = "SELECT * FROM product p JOIN category c ON p.category_id = c.id ORDER BY p.id DESC";
    try {
        rs = stmt.executeQuery(sql);
        
        System.out.println("+--------------------------------+");
        System.out.println("|           DAFTAR PRODUK        |");
        System.out.println("+--------------------------------+");
        System.out.println("|   NAMA PRODUK   | KATEGORI | KETERANGAN |   HARGA  |   STATUS  |");
        System.out.println("==================================================================");
        while (rs.next()) {
            String namaProduk = rs.getString("name_brg");
            String katProduk = rs.getString("name");
            String ketProduk = rs.getString("description");
            int hargaProduk = rs.getInt("price");
            String statusProduk = rs.getString("status");
            
            System.out.println(String.format("%s | %s | %s | %d | %s |", namaProduk, katProduk, ketProduk, hargaProduk, statusProduk));
            System.out.println("-------------------------------------------------");
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
    membuat method untuk menginput data
    */
    static void insertProduk() {
    try {
        // ambil input dari user
        System.out.print("Nama Barang: ");
        String namaBarang = input.readLine().trim();
        System.out.print("Kategori id: ");
        int catId = Integer.parseInt(input.readLine());
        System.out.print("Deskripsi: ");
        String deskBarang = input.readLine().trim();
        System.out.print("Harga: ");
        int harga = Integer.parseInt(input.readLine());
        System.out.print("Status: ");
        String status = input.readLine().trim();
        
        // query simpan
        String sql = "INSERT INTO product (category_id, name_brg, description, price, status) VALUE('%s', '%s', '%s', '%s', '%s')";
        sql = String.format(sql, catId, namaBarang, deskBarang, harga, status);
        
        // simpan buku
        stmt.execute(sql);
        
        System.out.println("Berhasil menginput data barang");
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
    membuat method untuk mengupdate data
    */
    static void editProduk() {
    try {  
        // ambil input dari user
        System.out.print("ID yang mau diedit: ");
        int idProduk = Integer.parseInt(input.readLine());
        System.out.print("Nama Barang: ");
        String namaBarang = input.readLine().trim();
        System.out.print("Kategori id: ");
        int catId = Integer.parseInt(input.readLine());
        System.out.print("Deskripsi: ");
        String deskBarang = input.readLine().trim();
        System.out.print("Harga: ");
        int harga = Integer.parseInt(input.readLine());
        System.out.print("Status: ");
        String status = input.readLine().trim();

        // query update
        String sql = "UPDATE product SET name_brg='%s', category_id=%d, description='%s', price=%d, status='%s' WHERE id=%d";
        sql = String.format(sql, namaBarang, catId, deskBarang, harga, status, idProduk);

        // update data buku
        stmt.execute(sql);
        System.out.println("Data barang dengan id "+ idProduk + "berhasil dirubah");
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
    membuat method untuk menghapus data
    */
    static void hapusProduk() {
    try {
        
        // ambil input dari user
        System.out.print("ID yang mau dihapus: ");
        int idProduk = Integer.parseInt(input.readLine());
        
        // buat query hapus
        String sql = String.format("DELETE FROM product WHERE id=%d", idProduk);
        // hapus data
        stmt.execute(sql);
        
        System.out.println("Data telah terhapus...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
