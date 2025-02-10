package com.example.gestionpedidosapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionpedidosapp.R
import com.example.gestionpedidosapp.domain.Order

class OrderAdapter(
    private val orders: MutableList<Order>,
    private val onItemClicked: (Order) -> Unit
) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hora: TextView = view.findViewById(R.id.hora)
        val fecha: TextView = view.findViewById(R.id.fecha)
        val cantProductos: TextView = view.findViewById(R.id.cant_productos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_adapter, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]
        holder.hora.text = "Hora: " + order.hora
        holder.fecha.text = "Fecha: " + order.fecha
        holder.cantProductos.text = "Cantidad: " + order.cantProductos

        holder.itemView.setOnClickListener { onItemClicked(order) }
    }

    override fun getItemCount() = orders.size

    fun actualizarLista(nuevaLista: List<Order>) {
        orders.clear()
        orders.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}