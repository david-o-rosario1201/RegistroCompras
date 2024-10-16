package edu.ucne.registrocompras.presentation.categoria

sealed interface CategoriaUiEvent {
    data class CategoriaIdChange(val categoriaId: Int) : CategoriaUiEvent
    data class DescripcionChange(val descripcion: String) : CategoriaUiEvent
    data class SelectedCategoria(val categoriaId: Int) : CategoriaUiEvent
    data object Save : CategoriaUiEvent
    data object Delete : CategoriaUiEvent
}