package com.example.gestionpedidosapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionpedidosapp.R
import com.example.gestionpedidosapp.adapters.OrderAdapter
import com.example.gestionpedidosapp.domain.Order
import com.example.gestionpedidosapp.domain.Product
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class HomeAdminActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private val listaSolicitudes = mutableListOf<Order>()
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_admin)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        orderAdapter = OrderAdapter(listaSolicitudes) { solicitud ->
            val intent = Intent(this, OrderDetailsActivity::class.java).apply {
                putExtra("fecha", solicitud.fecha)
                putExtra("hora", solicitud.hora)
                putExtra("estado", solicitud.estado)
                putExtra("productos", ArrayList(solicitud.productos)) // Convertimos la lista a ArrayList
            }
            startActivity(intent)
        }
        recyclerView.adapter = orderAdapter

        database = FirebaseDatabase.getInstance().getReference("pedidos")
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
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaSolicitudes.clear()
                for (pedidoSnapshot in snapshot.children) {
                    val fecha = pedidoSnapshot.child("fecha").getValue(String::class.java) ?: ""
                    val hora = pedidoSnapshot.child("hora").getValue(String::class.java) ?: ""
                    val estado = pedidoSnapshot.child("estado").getValue(String::class.java) ?: ""
                    val productosList = mutableListOf<Product>()

                    for (productoSnapshot in pedidoSnapshot.child("productos").children) {
                        val producto = productoSnapshot.getValue(Product::class.java)
                        if (producto != null) {
                            productosList.add(producto)
                        }
                    }

                    val pedido = Order(fecha, hora, estado, productosList)
                    listaSolicitudes.add(pedido)
                }

                // Ordenar la lista de más reciente a más antiguo (por fecha y hora)
                listaSolicitudes.sortWith(compareByDescending<Order> { it.fecha }.thenByDescending { it.hora })
                orderAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HomeAdminActivity, "Error al cargar pedidos", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
