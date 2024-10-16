package edu.ucne.registrocompras.presentation.cliente

import edu.ucne.registrocompras.data.remote.dto.ClienteDto
import java.time.Instant
import java.util.Date

data class ClienteUiState(
    val clienteId: Int? = null,
    val fechaCreacion: Date = Date.from(Instant.now()),
    val nombre: String? = "",
    val cedula: String? = "",
    val direccion: String? = "",
    val telefono: String? = "",
    val clientes: List<ClienteDto> = emptyList(),
    val errorNombre: String? = "",
    val errorCedula: String? = "",
    val errorDireccion: String? = "",
    val errorTelefono: String? = "",
    val errorCargar: String? = "",
    val success: Boolean = false,
    val isLoading: Boolean = false,
)
