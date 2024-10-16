package edu.ucne.registrocompras.presentation.cliente

sealed interface ClienteUiEvent {
    data class ClienteIdChange(val clienteId: Int?): ClienteUiEvent
    data class NombreChange(val nombre: String): ClienteUiEvent
    data class CedulaChange(val cedula: String): ClienteUiEvent
    data class DireccionChange(val direccion: String): ClienteUiEvent
    data class TelefonoChange(val telefono: String): ClienteUiEvent
    data class SelectedCliente(val clienteId: Int): ClienteUiEvent
    data object Save: ClienteUiEvent
    data object Delete: ClienteUiEvent
}