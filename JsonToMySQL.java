/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dendi.uaspbo;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonToMySQL {
    public static void main(String[] args) {
        try {
            // Tambahkan dependensi MySQL Connector dan Gson
            // Buat koneksi ke database MySQL
            String url = "jdbc:mysql://localhost:3306/tugas_uas_pbo";
            String username = "root";
            String password = "";

            Connection conn = DriverManager.getConnection(url, username, password);
            
            // Baca file JSON dan simpan dalam objek Java
            Gson gson = new Gson();
            JsonArray json = gson.fromJson(new FileReader("category.json"), JsonArray.class);
            
            // Iterasikan melalui objek JSON dan masukkan data ke dalam tabel MySQL
            for (int i = 0; i < json.size(); i++) {
                JsonObject obj = json.get(i).getAsJsonObject();
                String sql = "INSERT INTO category (id, name) VALUES (?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, obj.get("id").getAsString());
                statement.setString(2, obj.get("name").getAsString());
                statement.executeUpdate();
            }
            
            // Tutup koneksi ke database
            conn.close();
            
            System.out.println("Data berhasil diimpor.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

