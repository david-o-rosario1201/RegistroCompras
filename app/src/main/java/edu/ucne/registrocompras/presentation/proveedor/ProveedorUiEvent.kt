package edu.ucne.registrocompras.presentation.proveedor

sealed interface ProveedorUiEvent {
    data class ProveedorIdChange(val proveedorId: Int): ProveedorUiEvent
    data class NombreChange(val nombre: String): ProveedorUiEvent
    data class RncChange(val rnc: String): ProveedorUiEvent
    data class DireccionChange(val direccion: String): ProveedorUiEvent
    data class EmailChange(val email: String): ProveedorUiEvent
    data class SelectedProveedor(val proveedorId: Int): ProveedorUiEvent
    data object Save: ProveedorUiEvent
    data object Delete: ProveedorUiEvent
}