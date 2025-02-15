package com.example.gestionpedidosapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionpedidosapp.R
import com.example.gestionpedidosapp.adapters.OrderCompleteAdapter
import com.example.gestionpedidosapp.adapters.PendingOrdersAdapter
import com.example.gestionpedidosapp.domain.Order
import com.example.gestionpedidosapp.domain.Product
import com.google.firebase.database.*

class HomeEmployeeActivity : AppCompatActivity() {

    private lateinit var recyclerViewPendientes: RecyclerView
    private lateinit var recyclerViewCompletados: RecyclerView
    private lateinit var pendingOrdersAdapter: PendingOrdersAdapter
    private lateinit var orderCompleteAdapter: OrderCompleteAdapter
    private val listaSolicitudesPendientes = mutableListOf<Order>()
    private val listaSolicitudesCompletadas = mutableListOf<Order>()
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_employee)

        recyclerViewPendientes = findViewById(R.id.recyclerView)
        recyclerViewPendientes.layoutManager = LinearLayoutManager(this)
        pendingOrdersAdapter = PendingOrdersAdapter(listaSolicitudesPendientes) { solicitud ->
            abrirDetallesOrdenPendiente(solicitud)
        }
        recyclerViewPendientes.adapter = pendingOrdersAdapter

        recyclerViewCompletados = findViewById(R.id.recyclerView2)
        recyclerViewCompletados.layoutManager = LinearLayoutManager(this)
        orderCompleteAdapter = OrderCompleteAdapter(listaSolicitudesCompletadas) { solicitud ->
            abrirDetallesOrdenCompletada(solicitud)
        }
        recyclerViewCompletados.adapter = orderCompleteAdapter

        database = FirebaseDatabase.getInstance().getReference("pedidos")
        cargarSolicitudes()
    }

    private fun cargarSolicitudes() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaSolicitudesPendientes.clear()
                listaSolicitudesCompletadas.clear()

                for (pedidoSnapshot in snapshot.children) {
                    // Verificar si hay información dentro de cada pedido
                    val fecha = pedidoSnapshot.child("fecha").getValue(String::class.java) ?: ""
                    val hora = pedidoSnapshot.child("hora").getValue(String::class.java) ?: ""
                    val estado = pedidoSnapshot.child("estado").getValue(String::class.java) ?: ""
                    val productosList = mutableListOf<Product>()

                    // Obtener productos del pedido
                    val productosSnapshot = pedidoSnapshot.child("productos")
                    if (productosSnapshot.exists()) {
                        for (productoSnapshot in productosSnapshot.children) {
                            val producto = productoSnapshot.getValue(Product::class.java)
                            if (producto != null) {
                                productosList.add(producto)
                            }
                        }
                    }

                    val pedido = Order(fecha, hora, estado, productosList)

                    if (estado == "Pendiente") {
                        listaSolicitudesPendientes.add(pedido)
                    } else {
                        listaSolicitudesCompletadas.add(pedido)
                    }
                }
                // Notificar cambios en los adaptadores
                pendingOrdersAdapter.notifyDataSetChanged()
                orderCompleteAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HomeEmployeeActivity, "Error al cargar pedidos", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun abrirDetallesOrdenPendiente(solicitud: Order) {
        val intent = Intent(this, PendingOrdersDetailsActivity::class.java).apply {
            putExtra("fecha", solicitud.fecha)
            putExtra("hora", solicitud.hora)
            putExtra("estado", solicitud.estado)
            putExtra("productos", ArrayList(solicitud.productos))
        }
        startActivity(intent)
    }


    private fun abrirDetallesOrdenCompletada(solicitud: Order) {
        val intent = Intent(this, CompleteOrderActivity::class.java).apply {
            putExtra("fecha", solicitud.fecha)
            putExtra("hora", solicitud.hora)
            putExtra("estado", solicitud.estado)
            putExtra("productos", ArrayList(solicitud.productos))
        }
        startActivity(intent)
    }

}
