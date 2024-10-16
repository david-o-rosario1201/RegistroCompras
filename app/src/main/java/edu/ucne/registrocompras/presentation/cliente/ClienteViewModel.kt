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
                        nombre = event.nombre,
                        errorNombre = ""
                    )
                }
            }
            is ClienteUiEvent.CedulaChange -> {
                _uiState.update {
                    it.copy(
                        cedula = event.cedula,
                        errorCedula = ""
                    )
                }
            }
            is ClienteUiEvent.DireccionChange -> {
                _uiState.update {
                    it.copy(
                        direccion = event.direccion,
                        errorDireccion = ""
                    )
                }
            }
            is ClienteUiEvent.TelefonoChange -> {
                _uiState.update {
                    it.copy(
                        telefono = event.telefono,
                        errorTelefono = ""
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
            ClienteUiEvent.Save -> {
                viewModelScope.launch {

                    val nombreBuscado = _uiState.value.clientes
                        .find { it.nombre.lowercase() == _uiState.value.nombre?.lowercase() }

                    val cedulaBuscada = _uiState.value.clientes
                        .find { it.cedula == _uiState.value.cedula }

                    val telefonoBuscado = _uiState.value.clientes
                        .find { it.telefono == _uiState.value.telefono }

                    if(_uiState.value.nombre?.isBlank() == true){
                        _uiState.update {
                            it.copy(
                                errorNombre = "El nombre no puede estar vacío"
                            )
                        }
                    }
                    else if(nombreBuscado != null && nombreBuscado.clienteId != _uiState.value.clienteId){
                        _uiState.update {
                            it.copy(
                                errorNombre = "El nombre ya existe"
                            )
                        }
                    }

                    if(_uiState.value.cedula?.isBlank() == true) {
                        _uiState.update {
                            it.copy(
                                errorCedula = "La cédula no puede estar vacío"
                            )
                        }
                    }
                    else if(cedulaBuscada != null && cedulaBuscada.clienteId != _uiState.value.clienteId){
                        _uiState.update {
                            it.copy(
                                errorCedula = "La cédula ya existe"
                            )
                        }
                    }
                    else if(_uiState.value.cedula?.length != 11){
                        _uiState.update {
                            it.copy(
                                errorCedula = "La cédula debe tener 11 dígitos"
                            )
                        }
                    }

                    if(_uiState.value.direccion?.isBlank() == true) {
                        _uiState.update {
                            it.copy(
                                errorDireccion = "La dirección no puede estar vacía"
                            )
                        }
                    }

                    if(_uiState.value.telefono?.isBlank() == true) {
                        _uiState.update {
                            it.copy(
                                errorTelefono = "El teléfono no puede estar vacío"
                            )
                        }
                    }
                    else if(telefonoBuscado != null && telefonoBuscado.clienteId != _uiState.value.clienteId){
                        _uiState.update {
                            it.copy(
                                errorTelefono = "El teléfono ya existe"
                            )
                        }
                    }
                    else if(_uiState.value.telefono?.length != 10){
                        _uiState.update {
                            it.copy(
                                errorTelefono = "El teléfono debe tener 10 dígitos"
                            )
                        }
                    }

                    if(_uiState.value.errorNombre == "" && _uiState.value.errorCedula == ""
                        && _uiState.value.errorDireccion == "" && _uiState.value.errorTelefono == ""){

                        if(_uiState.value.clienteId == null)
                            clienteRepository.addCliente(_uiState.value.toEntity())
                        else
                            clienteRepository.updateCliente(
                                _uiState.value.clienteId ?: 0,
                                _uiState.value.toEntity()
                            )

                        _uiState.update {
                            it.copy(
                                success = true
                            )
                        }
                    }
                }
            }
            ClienteUiEvent.Delete -> {
                viewModelScope.launch {
                    clienteRepository.deleteCliente(_uiState.value.clienteId ?: 0)
                }
            }
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