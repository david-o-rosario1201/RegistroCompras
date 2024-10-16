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
                                //errorCargar = result.message,
                                categorias = result.data ?: emptyList(),
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
                                //errorCargar = result.message,
                                proveedores = result.data ?: emptyList(),
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
                        nombre = event.nombre,
                        errorNombre = ""
                    )
                }
            }
            is ProductoUiEvent.DescripcionChange -> {
                _uiState.update {
                    it.copy(
                        descripcion = event.descripcion,
                        errorDescripcion = ""
                    )
                }
            }
            is ProductoUiEvent.CategoriaIdChange -> {
                _uiState.update {
                    it.copy(
                        categoriaId = event.categoriaId,
                        errorCategoriaId = ""
                    )
                }
            }
            is ProductoUiEvent.ProveedorIdChange -> {
                _uiState.update {
                    it.copy(
                        proveedorId = event.proveedorId,
                        errorProveedorId = ""
                    )
                }
            }
            is ProductoUiEvent.PrecioChange -> {
                _uiState.update {
                    it.copy(
                        precio = event.precio,
                        errorPrecio = ""
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
                                precio = producto.precio.toString()
                            )
                        }
                    }
                }
            }
            ProductoUiEvent.Save -> {
                viewModelScope.launch {

                    val nombreBuscado = _uiState.value.productos
                        .find { it.nombre.lowercase() == _uiState.value.nombre?.lowercase() }

                    val descripcionBuscada = _uiState.value.productos
                        .find { it.descripcion.lowercase() == _uiState.value.descripcion?.lowercase() }

                    if(_uiState.value.nombre?.isBlank() == true){
                        _uiState.update {
                            it.copy(
                                errorNombre = "El nombre no puede estar vacío"
                            )
                        }
                    }
                    else if(nombreBuscado != null && nombreBuscado.productoId != _uiState.value.productoId){
                        _uiState.update {
                            it.copy(
                                errorNombre = "El nombre ya existe"
                            )
                        }
                    }

                    if(_uiState.value.descripcion?.isBlank() == true) {
                        _uiState.update {
                            it.copy(
                                errorDescripcion = "La descripción no puede estar vacío"
                            )
                        }
                    }
                    else if(descripcionBuscada != null && descripcionBuscada.productoId != _uiState.value.productoId) {
                        _uiState.update {
                            it.copy(
                                errorDescripcion = "La descripción ya existe"
                            )
                        }
                    }

                    if(_uiState.value.categoriaId == 0) {
                        _uiState.update {
                            it.copy(
                                errorCategoriaId = "Debe seleccionar una categoría"
                            )
                        }
                    }

                    if(_uiState.value.proveedorId == 0) {
                        _uiState.update {
                            it.copy(
                                errorProveedorId = "Debe seleccionar un proveedor"
                            )
                        }
                    }

                    if(_uiState.value.precio?.isBlank() == true) {
                        _uiState.update {
                            it.copy(
                                errorPrecio = "El precio no puede estar vacío"
                            )
                        }
                    }
                    else if(_uiState.value.precio?.toFloat()!! < 0.0f) {
                        _uiState.update {
                            it.copy(
                                errorPrecio = "El precio no puede ser negativo"
                            )
                        }
                    }

                    if(_uiState.value.errorNombre == "" && _uiState.value.errorDescripcion == "" &&
                        _uiState.value.errorCategoriaId == "" && _uiState.value.errorProveedorId == ""
                        && _uiState.value.errorPrecio == ""){

                        if(_uiState.value.productoId == null)
                            productoRepository.addProducto(_uiState.value.toEntity())
                        else
                            productoRepository.updateProducto(
                                _uiState.value.productoId ?: 0,
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
            ProductoUiEvent.Delete -> {
                viewModelScope.launch {
                    productoRepository.deleteProducto(_uiState.value.productoId ?: 0)
                }
            }
        }
    }

    fun ProductoUiState.toEntity() = ProductoDto(
        productoId = productoId,
        nombre = nombre ?: "",
        descripcion = descripcion ?: "",
        fechaCreacion = fechaCreacion,
        categoriaId = categoriaId ?: 0,
        proveedorId = proveedorId ?: 0,
        precio = precio.toString().toFloatOrNull() ?: 0.0f
    )
}