package edu.ucne.registrocompras.presentation.producto


import edu.ucne.registrocompras.data.remote.dto.CategoriaDto
import edu.ucne.registrocompras.data.remote.dto.ProductoDto
import edu.ucne.registrocompras.data.remote.dto.ProveedorDto
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
    val productos: List<ProductoDto> = emptyList(),
    val categorias: List<CategoriaDto> = emptyList(),
    val proveedores: List<ProveedorDto> = emptyList(),
    val errorNombre: String? = "",
    val errorDescripcion: String? = "",
    val errorCategoriaId: String? = "",
    val errorProveedorId: String? = "",
    val errorPrecio: String? = "",
    val errorCargar: String? = "",
    val success: Boolean = false,
    val isLoading: Boolean = false
)
