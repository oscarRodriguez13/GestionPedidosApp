package com.example.gestionpedidosapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionpedidosapp.R
import com.example.gestionpedidosapp.adapters.OrderAdapter
import com.example.gestionpedidosapp.domain.Order
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeAdminActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private val listaSolicitudes = mutableListOf<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_admin) // Asegúrate de que el layout contiene el RecyclerView

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this) // Usa un LinearLayoutManager para la lista

        // Inicializar el adaptador con la lista y un manejador de clics
        orderAdapter = OrderAdapter(listaSolicitudes) { solicitud ->
            val intent = Intent(this, OrderDetailsActivity::class.java).apply {
                putExtra("fecha", solicitud.fecha)
                putExtra("hora", solicitud.hora)
                putExtra("estado", solicitud.estado)
            }
            startActivity(intent)
        }
        recyclerView.adapter = orderAdapter

        // Cargar datos de prueba
        cargarSolicitudes()

        val fabAdd: FloatingActionButton = findViewById(R.id.fab_add)
        fabAdd.setOnClickListener {
            val intent = Intent(this, AddOrderActivity::class.java)
            startActivity(intent)
        }

        val buttonProducts: Button = findViewById(R.id.buttonProductos)
        buttonProducts.setOnClickListener {
            val intent = Intent(this, ProductsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cargarSolicitudes() {
    }
}