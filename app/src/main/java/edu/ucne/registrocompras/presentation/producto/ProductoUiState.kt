package edu.ucne.registrocompras.presentation.producto


import edu.ucne.registrocompras.data.local.entities.CategoriaEntity
import edu.ucne.registrocompras.data.local.entities.ProductoEntity
import edu.ucne.registrocompras.data.local.entities.ProveedorEntity
import java.time.Instant
import java.util.Date

data class ProductoUiState(
    val productoId: Int? = null,
    val nombre: String? = "",
    val descripcion: String? = "",
    val fechaCreacion: Date = Date.from(Instant.now()),
    val categoriaId: Int? = 0,
    val proveedorId: Int? = 0,
    val precio: String? = "",
    val productos: List<ProductoEntity> = emptyList(),
    val categorias: List<CategoriaEntity> = emptyList(),
    val proveedores: List<ProveedorEntity> = emptyList(),
    val errorNombre: String? = "",
    val errorDescripcion: String? = "",
    val errorCategoriaId: String? = "",
    val errorProveedorId: String? = "",
    val errorPrecio: String? = "",
    val errorCargar: String? = "",
    val success: Boolean = false,
    val isLoading: Boolean = false
)
