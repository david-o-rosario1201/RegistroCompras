package edu.ucne.registrocompras.presentation.proveedor

import edu.ucne.registrocompras.data.remote.dto.ProveedorDto
import java.time.Instant
import java.util.Date

data class ProveedorUiState(
    val proveedorId: Int? = null,
    val fechaCreacion: Date = Date.from(Instant.now()),
    val nombre: String? = "",
    val rnc: String? = "",
    val direccion: String? = "",
    val email: String? = "",
    val proveedores: List<ProveedorDto> = emptyList(),
    val errorFechaseCreacion: String? = "",
    val errorNombre: String? = "",
    val errorRnc: String? = "",
    val errorDireccion: String? = "",
    val errorEmail: String? = "",
    val errorCargar: String? = "",
    val success: Boolean = false,
    val isLoading: Boolean = false
)
