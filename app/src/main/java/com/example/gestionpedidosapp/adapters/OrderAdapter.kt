package com.example.gestionpedidosapp.adapters

import com.example.gestionpedidosapp.R

/*class OrderAdapter(
    private val paseoItems: MutableList<SolicitudItem>,
    private val onItemClicked: (SolicitudItem) -> Unit
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val horaInicial: TextView = view.findViewById(R.id.hora_inicial)
        val horaFinal: TextView = view.findViewById(R.id.hora_final)
        val cantidadMascotas: TextView = view.findViewById(R.id.cantidad_mascotas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_adapter, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paseoItem = paseoItems[position]
        holder.horaInicial.text = "Hora inicial: " + paseoItem.hora_inicial
        holder.horaFinal.text = "Hora final: " + paseoItem.hora_final
        holder.cantidadMascotas.text = "Cantidad: " + paseoItem.cantidad

        holder.itemView.setOnClickListener { onItemClicked(paseoItem) }
    }

    override fun getItemCount() = paseoItems.size

    fun actualizarLista(nuevaLista: List<SolicitudItem>) {
        paseoItems.clear()
        paseoItems.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}*/