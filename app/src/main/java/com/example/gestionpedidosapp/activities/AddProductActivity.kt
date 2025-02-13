package com.example.gestionpedidosapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestionpedidosapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddProductActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference  // Referencia a Firebase Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        database = FirebaseDatabase.getInstance().reference.child("productos")  // Referencia a la carpeta "productos"

        val buttonCancel: Button = findViewById(R.id.buttonCancel)
        val etProductName = findViewById<EditText>(R.id.etProductName)
        val buttonAddProduct = findViewById<Button>(R.id.buttonAddProduct)

        buttonCancel.setOnClickListener {
            val intent = Intent(this, ProductsActivity::class.java)
            startActivity(intent)
        }

        buttonAddProduct.setOnClickListener {
            val productName = etProductName.text.toString().trim()

            if (productName.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese un nombre de producto", Toast.LENGTH_SHORT).show()
            } else {
                addProductToDatabase(productName)
            }
        }
    }

    private fun addProductToDatabase(productName: String) {
        val productKey = database.push().key  // Genera una clave única para el producto

        if (productKey != null) {
            val productData = mapOf("nombre" to productName)  // Se guarda con el atributo "nombre"

            database.child(productKey).setValue(productData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ProductsActivity::class.java))  // Regresa a la lista de productos
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al agregar el producto", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Error al generar la clave del producto", Toast.LENGTH_SHORT).show()
        }
    }
}
