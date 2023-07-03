package com.example.pat;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationManager {
    private Map<String, String> users; // Contoh data pengguna, digantikan dengan database sesungguhnya

    public AuthenticationManager() {
        // Inisialisasi pengguna
        users = new HashMap<>();
        users.put("chris", "12345");
    }

    public boolean authenticate(String username, String password) {
        if (users.containsKey(username)) {
            String storedPassword = users.get(username);
            if (storedPassword.equals(password)) {
                return true; // Autentikasi berhasil
            }
        }
        return false; // Autentikasi gagal
    }
}
