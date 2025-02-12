package com.example.gestionpedidosapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.Toast
import com.example.gestionpedidosapp.R
import com.example.gestionpedidosapp.adapters.OrderProductsAdapter
import com.example.gestionpedidosapp.domain.Product

class AddOrderActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnConfirmarPedido: Button
    private lateinit var orderProductsAdapter: OrderProductsAdapter
    private val productList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)

        recyclerView = findViewById(R.id.recyclerViewProductos)
        btnConfirmarPedido = findViewById(R.id.btnConfirmarPedido)

        // Llenamos la lista con productos de prueba
        cargarProductos()

        // Configuramos el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        orderProductsAdapter = OrderProductsAdapter(productList) { producto ->
            actualizarCantidad(producto)
        }
        recyclerView.adapter = orderProductsAdapter

        // Botón para confirmar pedido
        btnConfirmarPedido.setOnClickListener {
            confirmarPedido()
        }
    }

    private fun cargarProductos() {
        productList.add(Product("Producto 1", 0))
        productList.add(Product("Producto 2", 0))
        productList.add(Product("Producto 3", 0))
    }

    private fun actualizarCantidad(producto: Product) {
        // Aquí podrías hacer alguna acción adicional cuando se actualice la cantidad de productos

    }

    private fun confirmarPedido() {
        val resumenPedido = productList.filter { it.cantidad > 0 }
            .joinToString("\n") { "${it.nombre}: ${it.cantidad}" }

        if (resumenPedido.isNotEmpty()) {
            Toast.makeText(this, "Pedido confirmado:\n$resumenPedido", Toast.LENGTH_LONG).show()
            val intent = Intent(this, HomeAdminActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "No has seleccionado productos", Toast.LENGTH_SHORT).show()
        }
    }
}
