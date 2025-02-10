package com.example.gestionpedidosapp.activities

import android.content.Intent
import android.os.Bundle
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
            // Acción cuando se hace clic en un elemento
            // Aquí podrías abrir otra actividad o mostrar detalles
        }
        recyclerView.adapter = orderAdapter

        // Cargar datos de prueba
        cargarSolicitudes()

        val fabAdd: FloatingActionButton = findViewById(R.id.fab_add)
        fabAdd.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cargarSolicitudes() {
        val datosPrueba = listOf(
            Order("08:00 AM", "05/10/2025", 2),
            Order("10:00 AM", "05/10/2025", 1),
            Order("12:00 PM", "05/10/2025", 3)
        )

        orderAdapter.actualizarLista(datosPrueba)
    }
}