package com.example.gestionpedidosapp.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionpedidosapp.R
import com.example.gestionpedidosapp.adapters.OrderProductsAdapter
import com.example.gestionpedidosapp.domain.Product
import com.google.firebase.database.*

class PendingOrdersDetailsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var orderProductsAdapter: OrderProductsAdapter
    private val productList = mutableListOf<Product>()
    private lateinit var confirmarButton: Button

    private lateinit var fecha: String
    private lateinit var hora: String
    private lateinit var estado: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_orders_details)

        // Inicializar vistas
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        confirmarButton = findViewById(R.id.btnConfirmarPedido)

        // Obtener datos del Intent
        fecha = intent.getStringExtra("fecha") ?: ""
        hora = intent.getStringExtra("hora") ?: ""
        estado = intent.getStringExtra("estado") ?: ""

        // Mostrar detalles del pedido
        findViewById<TextView>(R.id.textFecha).text = "Fecha: $fecha"
        findViewById<TextView>(R.id.textHora).text = "Hora: $hora"
        findViewById<TextView>(R.id.textEstado).text = "Estado: $estado"

        // Obtener la lista de productos del Intent
        val receivedProducts = intent.getSerializableExtra("productos") as? ArrayList<Product>
        receivedProducts?.let { productList.addAll(it) }

        // Inicializar Adapter
        orderProductsAdapter = OrderProductsAdapter(productList) { product ->

        }
        recyclerView.adapter = orderProductsAdapter

        // Configurar botón de confirmación
        confirmarButton.setOnClickListener {
            confirmarPedido()
        }
    }

    private fun confirmarPedido() {
        val pedidosRef = FirebaseDatabase.getInstance().getReference("pedidos")

        pedidosRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var pedidoEncontrado: DatabaseReference? = null

                for (pedidoSnapshot in snapshot.children) {
                    val fechaPedido = pedidoSnapshot.child("fecha").getValue(String::class.java) ?: ""
                    val horaPedido = pedidoSnapshot.child("hora").getValue(String::class.java) ?: ""
                    val estadoPedido = pedidoSnapshot.child("estado").getValue(String::class.java) ?: ""

                    if (fechaPedido == fecha && horaPedido == hora && estadoPedido == "Pendiente") {
                        pedidoEncontrado = pedidoSnapshot.ref
                        break
                    }
                }

                if (pedidoEncontrado != null) {
                    pedidoEncontrado.child("estado").setValue("Completado").addOnSuccessListener {
                        actualizarCantidadProducto(pedidoEncontrado)
                    }.addOnFailureListener {
                        Toast.makeText(this@PendingOrdersDetailsActivity, "Error al actualizar el pedido", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@PendingOrdersDetailsActivity, "Pedido no encontrado", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PendingOrdersDetailsActivity, "Error en la base de datos", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun actualizarCantidadProducto(pedidoRef: DatabaseReference) {
        pedidoRef.child("productos").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (productoSnapshot in snapshot.children) {
                    val nombreProducto = productoSnapshot.child("nombre").getValue(String::class.java)
                    val productoPedido = productList.find { it.nombre == nombreProducto }
                    productoPedido?.let {
                        productoSnapshot.ref.child("cantidad").setValue(it.cantidad)
                    }
                }
                Toast.makeText(this@PendingOrdersDetailsActivity, "Pedido confirmado y actualizado", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PendingOrdersDetailsActivity, "Error al actualizar productos", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
