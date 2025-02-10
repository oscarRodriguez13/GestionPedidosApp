package com.example.gestionpedidosapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.gestionpedidosapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user1: ImageView = findViewById(R.id.user1)
        val user2: ImageView = findViewById(R.id.user2)

        user1.setOnClickListener {
            val intent = Intent(this, PasswordAdminActivity::class.java)
            startActivity(intent)
        }

        user2.setOnClickListener {
            val intent = Intent(this, PasswordEmployeeActivity::class.java)
            startActivity(intent)
        }
    }
}
