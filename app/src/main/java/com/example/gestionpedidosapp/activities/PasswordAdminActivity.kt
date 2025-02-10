package com.example.gestionpedidosapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestionpedidosapp.R

class PasswordAdminActivity : AppCompatActivity() {
    private val defaultPassword = "admin123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_admin)

        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val inputPassword = etPassword.text.toString()
            if (inputPassword == defaultPassword) {
                Toast.makeText(this, "Acceso concedido", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeAdminActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
            }
        }
    }
}