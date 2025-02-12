package com.example.gestionpedidosapp.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionpedidosapp.R
import com.example.gestionpedidosapp.domain.Product

class ProductsAdapter(
    private val products: MutableList<Product>,
    private val onItemClicked: (Product) -> Unit,
    private val onProductDeleted: (Product) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.nombre)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete) // Botón "X" de eliminación
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.products_adapter, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.nombre.text = product.nombre

        // Acción al hacer clic en el producto
        holder.itemView.setOnClickListener { onItemClicked(product) }

        // Acción al presionar el botón "X"
        holder.btnDelete.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Eliminar producto")
                .setMessage("¿Desea borrar este producto?")
                .setPositiveButton("Sí") { _, _ ->
                    onProductDeleted(product) // Callback para manejar la eliminación en el Activity/Fragment
                    products.removeAt(position)
                    notifyItemRemoved(position)
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    override fun getItemCount() = products.size

    fun actualizarLista(nuevaLista: List<Product>) {
        products.clear()
        products.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}
