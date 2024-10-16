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
                                errorCargar = result.message,
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
                                errorCargar = result.message,
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
                        productoId = event.productoId
                    )
                }
            }
            is CompraUiEvent.CantidadChanged -> {
                _uiState.update {
                    it.copy(
                        cantidad = event.cantidad
                    )
                }
            }
            is CompraUiEvent.PrecioUnitarioChanged -> {
                _uiState.update {
                    it.copy(
                        precioUnitario = event.precioUnitario
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
                                cantidad = compra.cantidad,
                                precioUnitario = compra.precioUnitario,
                                totalCompra = compra.totalCompra
                            )
                        }
                    }
                }
            }
            CompraUiEvent.Save -> TODO()
            CompraUiEvent.Delete -> TODO()
        }
    }

    fun CompraUiState.toEntity() = CompraDto(
        compraId = compraId,
        fechaCompra = fechaCompra,
        productoId = productoId ?: 0,
        cantidad = cantidad ?: 0,
        precioUnitario = precioUnitario ?: 0.0f,
        totalCompra = totalCompra ?: 0.0f
    )
}