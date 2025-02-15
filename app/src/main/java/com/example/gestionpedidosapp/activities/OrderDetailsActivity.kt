package com.example.gestionpedidosapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionpedidosapp.R
import com.example.gestionpedidosapp.adapters.ProductsOrderAdapter
import com.example.gestionpedidosapp.domain.Product
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.FileOutputStream

class OrderDetailsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var orderProductsAdapter: ProductsOrderAdapter
    private val productList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        val fecha = intent.getStringExtra("fecha") ?: ""
        val hora = intent.getStringExtra("hora") ?: ""
        val estado = intent.getStringExtra("estado") ?: ""
        val productos = intent.getSerializableExtra("productos") as? ArrayList<Product> ?: arrayListOf()

        val textFecha: TextView = findViewById(R.id.textFecha)
        val textHora: TextView = findViewById(R.id.textHora)
        val textEstado: TextView = findViewById(R.id.textEstado)
        val btnExportExcel: ImageView = findViewById(R.id.btnExportExcel) // Cambiado a ImageView

        textFecha.text = "Fecha: $fecha"
        textHora.text = "Hora: $hora"
        textEstado.text = "Estado: $estado"

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        orderProductsAdapter = ProductsOrderAdapter(productList) { producto ->
            // Acción cuando se clickea un producto (opcional)
        }
        recyclerView.adapter = orderProductsAdapter

        // Cargar productos en la lista
        cargarProductos(productos)

        // Botón para exportar a Excel
        btnExportExcel.setOnClickListener {
            exportToExcel(productos)
        }
    }

    private fun cargarProductos(productos: List<Product>) {
        productList.clear()
        productList.addAll(productos)
        orderProductsAdapter.notifyDataSetChanged()
    }

    private fun exportToExcel(productos: List<Product>) {
        try {
            val workbook = WorkbookFactory.create(true)
            val sheet = workbook.createSheet("Pedidos")

            // Obtener fecha y hora del intent
            val fecha = intent.getStringExtra("fecha") ?: "Desconocida"
            val hora = intent.getStringExtra("hora") ?: "Desconocida"

            // Agregar fecha y hora en las primeras filas
            val rowFecha = sheet.createRow(0)
            rowFecha.createCell(0).setCellValue("Fecha:")
            rowFecha.createCell(1).setCellValue(fecha)

            val rowHora = sheet.createRow(1)
            rowHora.createCell(0).setCellValue("Hora:")
            rowHora.createCell(1).setCellValue(hora)

            // Crear encabezados de la tabla de productos (comienza desde la fila 3)
            val headerRow = sheet.createRow(3)
            val headers = listOf("Producto", "Cantidad")
            for ((index, title) in headers.withIndex()) {
                headerRow.createCell(index).setCellValue(title)
            }

            // Llenar datos de productos desde la fila 4
            for ((rowIndex, producto) in productos.withIndex()) {
                val row = sheet.createRow(rowIndex + 4) // Empieza desde la fila 4
                row.createCell(0).setCellValue(producto.nombre)
                row.createCell(1).setCellValue(producto.cantidad.toDouble()) // Suponiendo que cantidad es numérica
            }

            // Guardar archivo en almacenamiento interno
            val file = File(getExternalFilesDir(null), "Pedido_${fecha}_$hora.xlsx")
            val outputStream = FileOutputStream(file)
            workbook.write(outputStream)
            outputStream.close()
            workbook.close()

            // Compartir el archivo
            shareExcelFile(file)

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al exportar: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun shareExcelFile(file: File) {
        val uri = FileProvider.getUriForFile(this, "$packageName.provider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(intent, "Compartir archivo Excel"))
    }
}
