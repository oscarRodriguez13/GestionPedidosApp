package com.example.gestionpedidosapp.domain

import java.io.Serializable

data class Product(
    var id: String = "",
    var nombre: String = "",
    var cantidad: Int = 0
) : Serializable
