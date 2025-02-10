package com.example.gestionpedidosapp.domain

data class Order (
    val fecha: String,
    val hora: String,
    val productos: List<Product>
)