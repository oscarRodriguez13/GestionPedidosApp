package com.example.gestionpedidosapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestionpedidosapp.R

class AddProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val buttonCancel: Button = findViewById(R.id.buttonCancel)
        buttonCancel.setOnClickListener {
            val intent = Intent(this, ProductsActivity::class.java)
            startActivity(intent)
        }

        val etProductName = findViewById<EditText>(R.id.etProductName)
        val buttonAddProduct = findViewById<Button>(R.id.buttonAddProduct)

        buttonAddProduct.setOnClickListener {
            val productName = etProductName.text.toString().trim()

            if (productName.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese un nombre de producto", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, ProductsActivity::class.java)
                startActivity(intent)
            }
        }

    }
}