package com.example.gestionpedidosapp.domain

data class Product(
    var id: String = "",
    var nombre: String = "",  // Necesario para Firebase
    var cantidad: Int = 0       // Necesario para Firebase
)
