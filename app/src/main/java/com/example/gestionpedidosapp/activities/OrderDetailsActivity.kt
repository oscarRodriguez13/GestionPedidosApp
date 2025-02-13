package com.example.gestionpedidosapp.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gestionpedidosapp.R
import com.example.gestionpedidosapp.domain.Product

class OrderDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        val fecha = intent.getStringExtra("fecha") ?: ""
        val hora = intent.getStringExtra("hora") ?: ""
        val estado = intent.getStringExtra("estado") ?: ""
        val productos = intent.getSerializableExtra("productos") as? ArrayList<Product> ?: arrayListOf()

        val textFecha: TextView = findViewById(R.id.textFecha)
        val textHora: TextView = findViewById(R.id.textHora)
        val textEstado: TextView = findViewById(R.id.textEstado)

        textFecha.text = "Fecha: $fecha"
        textHora.text = "Hora: $hora"
        textEstado.text = "Estado: $estado"

    }
}
