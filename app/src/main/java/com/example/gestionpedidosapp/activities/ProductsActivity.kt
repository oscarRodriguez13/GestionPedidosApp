package com.example.gestionpedidosapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionpedidosapp.R
import com.example.gestionpedidosapp.adapters.ProductsAdapter
import com.example.gestionpedidosapp.domain.Product
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class ProductsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductsAdapter
    private val listaProductos = mutableListOf<Product>()
    private val database = FirebaseDatabase.getInstance().getReference("productos")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        productAdapter = ProductsAdapter(
            this,
            listaProductos,
            onItemClicked = { product ->
                // Acción cuando se hace clic en un producto
                Toast.makeText(this, "Producto: ${product.nombre}", Toast.LENGTH_SHORT).show()
            },
            onProductDeleted = { product ->
                eliminarProductoDeFirebase(product)
            }
        )

        recyclerView.adapter = productAdapter

        cargarProductosDesdeFirebase()  // Cargar productos desde Firebase

        val fabAdd: FloatingActionButton = findViewById(R.id.fab_add)
        fabAdd.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }

        val buttonPedidos: Button = findViewById(R.id.buttonHistorial)
        buttonPedidos.setOnClickListener {
            val intent = Intent(this, HomeAdminActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cargarProductosDesdeFirebase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listaProductos.clear()
                for (snapshot in dataSnapshot.children) {
                    val product = snapshot.getValue(Product::class.java)
                    if (product != null) {
                        listaProductos.add(product)
                    }
                }
                productAdapter.actualizarLista(listaProductos)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Firebase", "Error al cargar productos", databaseError.toException())
            }
        })
    }

    private fun eliminarProductoDeFirebase(product: Product) {
        database.orderByChild("nombre").equalTo(product.nombre)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (productoSnapshot in snapshot.children) {
                            productoSnapshot.ref.removeValue()  // Elimina el producto por ID
                                .addOnSuccessListener {
                                    Toast.makeText(this@ProductsActivity, "Producto eliminado", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this@ProductsActivity, "Error al eliminar producto", Toast.LENGTH_SHORT).show()
                                }
                        }
                    } else {
                        Toast.makeText(this@ProductsActivity, "Producto no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error al buscar el producto", error.toException())
                }
            })
    }

}
