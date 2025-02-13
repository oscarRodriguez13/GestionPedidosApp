package com.example.gestionpedidosapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionpedidosapp.R
import com.example.gestionpedidosapp.domain.Product

class ProductsAdapter(
    context: Context,
    private var products: MutableList<Product>,
    private val onItemClicked: (Product) -> Unit,
    private val onProductDeleted: (Product) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.nombre)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.products_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.nombre.text = product.nombre

        holder.itemView.setOnClickListener { onItemClicked(product) }

        holder.btnDelete.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Eliminar producto")
                .setMessage("¿Desea borrar este producto?")
                .setPositiveButton("Sí") { _, _ ->
                    val pos = holder.adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        onProductDeleted(product)  // Eliminar de Firebase
                        products.removeAt(pos)
                        notifyItemRemoved(pos)
                    }
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    override fun getItemCount(): Int = products.size

    fun actualizarLista(nuevaLista: List<Product>) {
        products = nuevaLista.toMutableList()
        notifyDataSetChanged()
    }
}
