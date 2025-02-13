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
import com.google.firebase.database.*

class AddOrderActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnConfirmarPedido: Button
    private lateinit var orderProductsAdapter: OrderProductsAdapter
    private val productList = mutableListOf<Product>()

    private lateinit var database: DatabaseReference  // Firebase Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)

        recyclerView = findViewById(R.id.recyclerViewProductos)
        btnConfirmarPedido = findViewById(R.id.btnConfirmarPedido)

        // Configuramos Firebase
        database = FirebaseDatabase.getInstance().getReference("productos")

        // Configuramos el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        orderProductsAdapter = OrderProductsAdapter(productList) { producto ->
            actualizarCantidad(producto)
        }
        recyclerView.adapter = orderProductsAdapter

        // Cargar productos desde Firebase
        cargarProductos()

        // Botón para confirmar pedido
        btnConfirmarPedido.setOnClickListener {
            confirmarPedido()
        }
    }

    private fun cargarProductos() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                for (productoSnapshot in snapshot.children) {
                    val product = productoSnapshot.getValue(Product::class.java)
                    if (product != null) {
                        productList.add(product)
                    }
                }
                orderProductsAdapter.notifyDataSetChanged()  // Notificar cambios al adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AddOrderActivity, "Error al cargar productos", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun actualizarCantidad(producto: Product) {
        // Puedes hacer algo cuando se actualiza la cantidad de productos, por ejemplo, actualizar Firebase si lo deseas
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
