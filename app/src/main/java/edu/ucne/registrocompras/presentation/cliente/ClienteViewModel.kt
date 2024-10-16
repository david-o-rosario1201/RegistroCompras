package edu.ucne.registrocompras.presentation.cliente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrocompras.data.remote.Resource
import edu.ucne.registrocompras.data.remote.dto.ClienteDto
import edu.ucne.registrocompras.data.repository.ClienteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClienteViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository
): ViewModel(){

    private val _uiState = MutableStateFlow(ClienteUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getClientes()
    }

    private fun getClientes(){
        viewModelScope.launch {
            clienteRepository.getClientes().collectLatest { result ->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                clientes = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorCargar = result.message,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: ClienteUiEvent){
        when(event){
            is ClienteUiEvent.ClienteIdChange -> {
                _uiState.update {
                    it.copy(
                        clienteId = event.clienteId
                    )
                }
            }
            is ClienteUiEvent.NombreChange -> {
                _uiState.update {
                    it.copy(
                        nombre = event.nombre
                    )
                }
            }
            is ClienteUiEvent.CedulaChange -> {
                _uiState.update {
                    it.copy(
                        cedula = event.cedula
                    )
                }
            }
            is ClienteUiEvent.DireccionChange -> {
                _uiState.update {
                    it.copy(
                        direccion = event.direccion
                    )
                }
            }
            is ClienteUiEvent.TelefonoChange -> {
                _uiState.update {
                    it.copy(
                        telefono = event.telefono
                    )
                }
            }
            is ClienteUiEvent.SelectedCliente -> {
                viewModelScope.launch {
                    if(event.clienteId > 0){
                        val cliente = clienteRepository.getCliente(event.clienteId)
                        _uiState.update {
                            it.copy(
                                clienteId = cliente.clienteId,
                                fechaCreacion = cliente.fechaCreacion,
                                nombre = cliente.nombre,
                                cedula = cliente.cedula,
                                direccion = cliente.direccion,
                                telefono = cliente.telefono
                            )
                        }
                    }
                }
            }
            ClienteUiEvent.Save -> TODO()
            ClienteUiEvent.Delete -> TODO()
        }
    }

    fun ClienteUiState.toEntity() = ClienteDto(
        clienteId = clienteId,
        fechaCreacion = fechaCreacion,
        nombre = nombre ?: "",
        cedula = cedula ?: "",
        direccion = direccion ?: "",
        telefono = telefono ?: ""
    )
}