package edu.ucne.registrocompras.presentation.proveedor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrocompras.data.remote.Resource
import edu.ucne.registrocompras.data.remote.dto.ProveedorDto
import edu.ucne.registrocompras.data.repository.ProveedorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProveedorViewModel @Inject constructor(
    private val proveedorRepository: ProveedorRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ProveedorUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getProveedores()
    }

    private fun getProveedores(){
        viewModelScope.launch {
            proveedorRepository.getProveedores().collectLatest { result ->
                when(result){
                    is Resource.Loading ->{
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                proveedores = result.data ?: emptyList(),
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

                    else -> {}
                }
            }
        }
    }

    fun onEvent(event: ProveedorUiEvent){
        when(event){
            is ProveedorUiEvent.ProveedorIdChange -> {
                _uiState.update {
                    it.copy(
                        proveedorId = event.proveedorId
                    )
                }
            }
            is ProveedorUiEvent.DireccionChange -> {
                _uiState.update {
                    it.copy(
                        direccion = event.direccion,
                        errorDireccion = ""
                    )
                }
            }
            is ProveedorUiEvent.EmailChange -> {
                _uiState.update {
                    it.copy(
                        email = event.email,
                        errorEmail = ""
                    )
                }
            }
            is ProveedorUiEvent.NombreChange -> {
                _uiState.update {
                    it.copy(
                        nombre = event.nombre,
                        errorNombre = ""
                    )
                }
            }
            is ProveedorUiEvent.RncChange -> {
                _uiState.update {
                    it.copy(
                        rnc = event.rnc,
                        errorRnc = ""
                    )
                }
            }

            is ProveedorUiEvent.SelectedProveedor -> {
                viewModelScope.launch {
                    if(event.proveedorId > 0){
                        val proveedor = proveedorRepository.getProveedor(event.proveedorId)
                        _uiState.update {
                            it.copy(
                                proveedorId = proveedor.proveedorId,
                                fechaCreacion = proveedor.fechaCreacion,
                                nombre = proveedor.nombre,
                                rnc = proveedor.rnc,
                                direccion = proveedor.direccion,
                                email = proveedor.email
                            )
                        }
                    }
                }
            }

            ProveedorUiEvent.Save -> {
                viewModelScope.launch {
                    val nombreBuscado = _uiState.value.proveedores
                        .firstOrNull { it.nombre.lowercase() == _uiState.value.nombre?.lowercase() }

                    val rncBuscado = _uiState.value.proveedores
                        .firstOrNull { it.rnc.lowercase() == _uiState.value.rnc?.lowercase() }

                    val emailBuscado = _uiState.value.proveedores
                        .firstOrNull { it.email.lowercase() == _uiState.value.email?.lowercase() }

                    if(_uiState.value.nombre?.isBlank() == true){
                        _uiState.update {
                            it.copy(
                                errorNombre = "El nombre no puede estar vacío"
                            )
                        }
                    }
                    else if(nombreBuscado != null && nombreBuscado.proveedorId != _uiState.value.proveedorId){
                        _uiState.update {
                            it.copy(
                                errorNombre = "El nombre ya existe"
                            )
                        }
                    }

                    if(_uiState.value.rnc?.isBlank() == true) {
                        _uiState.update {
                            it.copy(
                                errorRnc = "El rnc no puede estar vacío"
                            )
                        }
                    }
                    else if(rncBuscado != null && rncBuscado.proveedorId != _uiState.value.proveedorId){
                        _uiState.update {
                            it.copy(
                                errorRnc = "El rnc ya existe"
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

                    if(_uiState.value.email?.isBlank() == true) {
                        _uiState.update {
                            it.copy(
                                errorEmail = "El email no puede estar vacío"
                            )
                        }
                    }
                    else if(emailBuscado != null && emailBuscado.proveedorId != _uiState.value.proveedorId){
                        _uiState.update {
                            it.copy(
                                errorEmail = "El email ya existe"
                            )
                        }
                    }

                    if(_uiState.value.errorNombre == "" && _uiState.value.errorRnc == "" && _uiState.value.errorDireccion == ""
                        && _uiState.value.errorEmail == ""){

                        if(_uiState.value.proveedorId == null)
                            proveedorRepository.addProveedor(_uiState.value.toEntity())
                        else
                            proveedorRepository.updateProveedor(
                                _uiState.value.proveedorId ?: 0,
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
            ProveedorUiEvent.Delete -> {
                viewModelScope.launch {
                    proveedorRepository.deleteProveedor(_uiState.value.proveedorId ?: 0)
                }
            }

            else -> {}
        }
    }

    fun ProveedorUiState.toEntity() = ProveedorDto(
        proveedorId = proveedorId,
        fechaCreacion = fechaCreacion,
        nombre = nombre ?: "",
        rnc = rnc ?: "",
        direccion = direccion ?: "",
        email = email ?: ""
    )
}