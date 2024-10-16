package edu.ucne.registrocompras.presentation.producto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrocompras.data.remote.Resource
import edu.ucne.registrocompras.data.remote.dto.ProductoDto
import edu.ucne.registrocompras.data.repository.CategoriaRepository
import edu.ucne.registrocompras.data.repository.ProductoRepository
import edu.ucne.registrocompras.data.repository.ProveedorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductoViewModel @Inject constructor(
    private val productoRepository: ProductoRepository,
    private val categoriaRepository: CategoriaRepository,
    private val proveedorRepository: ProveedorRepository
): ViewModel(){

    private val _uiState = MutableStateFlow(ProductoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getProductos()
        getCategorias()
        getProveedores()
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

    private fun getCategorias(){
        viewModelScope.launch {
            categoriaRepository.getCategorias().collectLatest { result ->
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
                                categorias = result.data ?: emptyList(),
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

    private fun getProveedores(){
        viewModelScope.launch {
            proveedorRepository.getProveedores().collectLatest { result ->
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
                }
            }
        }
    }

    fun onEvent(event: ProductoUiEvent){
        when(event){
            is ProductoUiEvent.ProductoIdChange -> {
                _uiState.update {
                    it.copy(
                        productoId = event.productoId
                    )
                }
            }
            is ProductoUiEvent.NombreChange -> {
                _uiState.update {
                    it.copy(
                        nombre = event.nombre
                    )
                }
            }
            is ProductoUiEvent.DescripcionChange -> {
                _uiState.update {
                    it.copy(
                        descripcion = event.descripcion
                    )
                }
            }
            is ProductoUiEvent.CategoriaIdChange -> {
                _uiState.update {
                    it.copy(
                        categoriaId = event.categoriaId
                    )
                }
            }
            is ProductoUiEvent.ProveedorIdChange -> {
                _uiState.update {
                    it.copy(
                        proveedorId = event.proveedorId
                    )
                }
            }
            is ProductoUiEvent.PrecioChange -> {
                _uiState.update {
                    it.copy(
                        precio = event.precio
                    )
                }
            }
            is ProductoUiEvent.SelectedProducto -> {
                viewModelScope.launch {
                    if(event.productoId > 0){
                        val producto = productoRepository.getProducto(event.productoId)
                        _uiState.update {
                            it.copy(
                                productoId = producto.productoId,
                                nombre = producto.nombre,
                                descripcion = producto.descripcion,
                                fechaCreacion = producto.fechaCreacion,
                                categoriaId = producto.categoriaId,
                                proveedorId = producto.proveedorId,
                                precio = producto.precio
                            )
                        }
                    }
                }
            }
            ProductoUiEvent.Save -> TODO()
            ProductoUiEvent.Delete -> TODO()
        }
    }

    fun ProductoUiState.toEntity() = ProductoDto(
        productoId = productoId,
        nombre = nombre ?: "",
        descripcion = descripcion ?: "",
        fechaCreacion = fechaCreacion,
        categoriaId = categoriaId ?: 0,
        proveedorId = proveedorId ?: 0,
        precio = precio ?: 0.0f
    )
}