package com.example.gestionpedidosapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionpedidosapp.R

class HomeAdminActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var homeAdapter: HomeAdapter
    private val listaSolicitudes = mutableListOf<SolicitudItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Asegúrate de que el layout contiene el RecyclerView

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this) // Usa un LinearLayoutManager para la lista

        // Inicializar el adaptador con la lista y un manejador de clics
        homeAdapter = HomeAdapter(listaSolicitudes) { solicitud ->
            // Acción cuando se hace clic en un elemento
            // Aquí podrías abrir otra actividad o mostrar detalles
        }
        recyclerView.adapter = homeAdapter

        // Cargar datos de prueba
        cargarSolicitudes()
    }

    private fun cargarSolicitudes() {
        val datosPrueba = listOf(
            SolicitudItem("08:00 AM", "09:00 AM", 2),
            SolicitudItem("10:00 AM", "11:00 AM", 1),
            SolicitudItem("12:00 PM", "01:00 PM", 3)
        )

        homeAdapter.actualizarLista(datosPrueba)
    }
}