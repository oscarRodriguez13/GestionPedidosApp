package com.example.gestionpedidosapp.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionpedidosapp.R
import com.example.gestionpedidosapp.domain.Product

class OrderProductsAdapter(
    private val products: MutableList<Product>,
    private val onQuantityChanged: (Product) -> Unit
) : RecyclerView.Adapter<OrderProductsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.nombre)
        val cantidad: TextView = view.findViewById(R.id.tvCantidad)  // ID corregido
        val btnMas: Button = view.findViewById(R.id.btnMas)
        val btnMenos: Button = view.findViewById(R.id.btnMenos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_products_adapter, parent, false)  // Asegurar que el XML existe
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.nombre.text = product.nombre
        holder.cantidad.text = product.cantidad.toString()

        holder.btnMas.setOnClickListener {
            product.cantidad++
            holder.cantidad.text = product.cantidad.toString()
            onQuantityChanged(product)
            notifyItemChanged(position)  // Actualizar la vista
        }

        holder.btnMenos.setOnClickListener {
            if (product.cantidad > 0) {
                product.cantidad--
                holder.cantidad.text = product.cantidad.toString()
                onQuantityChanged(product)
                notifyItemChanged(position)  // Actualizar la vista
            }
        }

        // Permitir edición manual al tocar el número
        holder.cantidad.setOnClickListener {
            showEditQuantityDialog(holder.itemView.context, product, holder, position)
        }
    }

    override fun getItemCount() = products.size

    private fun showEditQuantityDialog(context: Context, product: Product, holder: ViewHolder, position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Editar cantidad")

        // Crear un campo de entrada de número
        val input = EditText(context)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.setText(product.cantidad.toString())

        builder.setView(input)

        builder.setPositiveButton("OK") { _, _ ->
            val newQuantity = input.text.toString().toIntOrNull()
            if (newQuantity != null && newQuantity >= 0) {
                product.cantidad = newQuantity
                holder.cantidad.text = product.cantidad.toString()
                onQuantityChanged(product)
                notifyItemChanged(position)
            } else {
                Toast.makeText(context, "Cantidad inválida", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}
