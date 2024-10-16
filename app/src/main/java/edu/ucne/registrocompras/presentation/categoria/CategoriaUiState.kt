package edu.ucne.registrocompras.presentation.categoria

import edu.ucne.registrocompras.data.local.entities.CategoriaEntity
import java.time.Instant
import java.util.Date

data class CategoriaUiState(
    val categoriaId: Int? = null,
    val fechaCreacion: Date = Date.from(Instant.now()),
    val descripcion: String? = "",
    val categorias: List<CategoriaEntity> = emptyList(),
    val errorDescripcion: String? = "",
    val errorCargar: String? = "",
    val success: Boolean = false,
    val isLoading: Boolean = false
)
