package edu.ucne.registrocompras.presentation.cliente

import edu.ucne.registrocompras.data.local.entities.ClienteEntity
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

data class ClienteUiState(
    val clienteId: Int? = null,
    val fechaCreacion: String = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss",
        Locale.getDefault()).format(Date.from(Instant.now())),
    val nombre: String? = "",
    val cedula: String? = "",
    val direccion: String? = "",
    val telefono: String? = "",
    val clientes: List<ClienteEntity> = emptyList(),
    val errorNombre: String? = "",
    val errorCedula: String? = "",
    val errorDireccion: String? = "",
    val errorTelefono: String? = "",
    val errorCargar: String? = "",
    val success: Boolean = false,
    val isLoading: Boolean = false,
)
