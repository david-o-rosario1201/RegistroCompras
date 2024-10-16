package edu.ucne.registrocompras.presentation.categoria

import edu.ucne.registrocompras.data.local.entities.CategoriaEntity
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

data class CategoriaUiState(
    val categoriaId: Int? = null,
    val fechaCreacion: String = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss",
        Locale.getDefault()).format(Date.from(Instant.now())),
    val descripcion: String? = "",
    val categorias: List<CategoriaEntity> = emptyList(),
    val errorDescripcion: String? = "",
    val errorCargar: String? = "",
    val success: Boolean = false,
    val isLoading: Boolean = false
)
