package com.example.gestionpedidosapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionpedidosapp.R
import com.example.gestionpedidosapp.adapters.ProductsAdapter
import com.example.gestionpedidosapp.domain.Product
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProductsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductsAdapter
    private val listaSolicitudes = mutableListOf<Product>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this) // Usa un LinearLayoutManager para la lista

        // Inicializar el adaptador con la lista y un manejador de clics
        productAdapter = ProductsAdapter(listaSolicitudes) { product ->
            // Acción cuando se hace clic en un elemento
            // Aquí podrías abrir otra actividad o mostrar detalles
        }
        recyclerView.adapter = productAdapter

        // Cargar datos de prueba
        cargarProductos()

        val fabAdd: FloatingActionButton = findViewById(R.id.fab_add)
        fabAdd.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }

        val buttonPedidos: Button = findViewById(R.id.buttonPedidos)
        buttonPedidos.setOnClickListener {
            val intent = Intent(this, HomeAdminActivity::class.java)
            startActivity(intent)
        }

    }

    private fun cargarProductos() {
        val datosPrueba = listOf(
            Product("Sagu", 0)
        )

        productAdapter.actualizarLista(datosPrueba)
    }

}