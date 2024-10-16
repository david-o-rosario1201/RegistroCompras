package edu.ucne.registrocompras.presentation.compra

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrocompras.data.remote.Resource
import edu.ucne.registrocompras.data.remote.dto.CompraDto
import edu.ucne.registrocompras.data.repository.CompraRepository
import edu.ucne.registrocompras.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompraViewModel @Inject constructor(
    private val compraRepository: CompraRepository,
    private val productoRepository: ProductoRepository
): ViewModel(){

    private val _uiState = MutableStateFlow(CompraUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCompras()
        getProductos()
    }

    private fun getCompras(){
        viewModelScope.launch {
            compraRepository.getCompras().collectLatest { result ->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                compras = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                //errorCargar = result.message,
                                compras = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getProductos(){
        viewModelScope.launch {
            productoRepository.getProductos().collectLatest { result ->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                productos = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                //errorCargar = result.message,
                                productos = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: CompraUiEvent){
        when(event){
            is CompraUiEvent.CompraIdChanged -> {
                _uiState.update {
                    it.copy(
                        compraId = event.compraId
                    )
                }
            }
            is CompraUiEvent.ProductoIdChanged -> {
                _uiState.update {
                    it.copy(
                        productoId = event.productoId,
                        precioUnitario = it.productos.find { p -> p.productoId == event.productoId }?.precio.toString(),
                        errorProductoId = ""
                    )
                }
            }
            is CompraUiEvent.CantidadChanged -> {

                val cantidad = event.cantidad.toIntOrNull() ?: 0
                val precioUnitario = _uiState.value.precioUnitario?.toFloatOrNull() ?: 0.0f

                _uiState.update {
                    it.copy(
                        cantidad = event.cantidad,
                        totalCompra = (cantidad * precioUnitario).toString(),
                        errorCantidad = ""
                    )
                }
            }
            is CompraUiEvent.PrecioUnitarioChanged -> {
                _uiState.update {
                    it.copy(
                        precioUnitario = event.precioUnitario,
                        errorPrecioUnitario = ""
                    )
                }
            }
            is CompraUiEvent.SelectedCompra -> {
                viewModelScope.launch {
                    if(event.compraId > 0){
                        val compra = compraRepository.getCompra(event.compraId)
                        _uiState.update {
                            it.copy(
                                compraId = compra.compraId,
                                fechaCompra = compra.fechaCompra,
                                productoId = compra.productoId,
                                cantidad = compra.cantidad.toString(),
                                precioUnitario = compra.precioUnitario.toString(),
                                totalCompra = compra.totalCompra.toString()
                            )
                        }
                    }
                }
            }
            CompraUiEvent.Save -> {
                viewModelScope.launch {

                    if(_uiState.value.productoId == 0){
                        _uiState.update {
                            it.copy(
                                errorProductoId = "Debe seleccionar un producto"
                            )
                        }
                    }

                    if(_uiState.value.cantidad?.isBlank() == true) {
                        _uiState.update {
                            it.copy(
                                errorCantidad = "Debe ingresar una cantidad"
                            )
                        }
                    }
                    else if(_uiState.value.cantidad?.toIntOrNull()!! < 0 || _uiState.value.cantidad?.toIntOrNull()!! > 1000) {
                        _uiState.update {
                            it.copy(
                                errorCantidad = "Debe ingresar una cantidad entre 0 y 1000"
                            )
                        }
                    }

                    if(_uiState.value.errorProductoId == "" && _uiState.value.errorCantidad == ""){

                        if(_uiState.value.compraId == null)
                            compraRepository.addCompra(_uiState.value.toEntity())
                        else
                            compraRepository.updateCompra(
                                _uiState.value.compraId ?: 0,
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
            CompraUiEvent.Delete -> {
                viewModelScope.launch {
                    compraRepository.deleteCompra(_uiState.value.compraId ?: 0)
                }
            }

            else -> {}
        }
    }

    fun CompraUiState.toEntity() = CompraDto(
        compraId = compraId,
        fechaCompra = fechaCompra,
        productoId = productoId ?: 0,
        cantidad = cantidad?.toIntOrNull() ?: 0,
        precioUnitario = precioUnitario?.toFloatOrNull() ?: 0.0f,
        totalCompra = totalCompra?.toFloatOrNull() ?: 0.0f
    )
}