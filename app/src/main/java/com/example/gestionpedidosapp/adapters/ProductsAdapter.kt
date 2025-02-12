package com.example.gestionpedidosapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionpedidosapp.R
import com.example.gestionpedidosapp.domain.Order
import com.example.gestionpedidosapp.domain.Product

class ProductsAdapter(
    private val products: MutableList<Product>,
    private val onItemClicked: (Product) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.nombre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.products_adapter, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.nombre.text = "" + product.nombre

        holder.itemView.setOnClickListener { onItemClicked(product) }
    }

    override fun getItemCount() = products.size

    fun actualizarLista(nuevaLista: List<Product>) {
        products.clear()
        products.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}