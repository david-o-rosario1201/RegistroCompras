package edu.ucne.registrocompras.presentation.categoria

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrocompras.data.remote.Resource
import edu.ucne.registrocompras.data.remote.dto.CategoriaDto
import edu.ucne.registrocompras.data.repository.CategoriaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriaViewModel @Inject constructor(
    private val categoriaRepository: CategoriaRepository
): ViewModel(){

    private val _uiState = MutableStateFlow(CategoriaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCategorias()
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

    fun onEvent(event: CategoriaUiEvent){
        when(event){
            is CategoriaUiEvent.CategoriaIdChange -> {
                _uiState.update {
                    it.copy(
                        categoriaId = event.categoriaId
                    )
                }
            }
            is CategoriaUiEvent.DescripcionChange -> {
                _uiState.update {
                    it.copy(
                        descripcion = event.descripcion,
                        errorDescripcion = ""
                    )
                }
            }
            is CategoriaUiEvent.SelectedCategoria -> {
                viewModelScope.launch {
                    if(event.categoriaId > 0){
                        val categoria = categoriaRepository.getCategoria(event.categoriaId)
                        _uiState.update {
                            it.copy(
                                categoriaId = categoria.categoriaId,
                                fechaCreacion = categoria.fechaCreacion,
                                descripcion = categoria.descripcion,
                            )
                        }
                    }
                }
            }
            CategoriaUiEvent.Save -> {
                viewModelScope.launch {

                    val descripcionBuscada = _uiState.value.categorias
                        .find { it.descripcion.lowercase() == _uiState.value.descripcion?.lowercase() }

                    if(_uiState.value.descripcion?.isBlank() == true){
                        _uiState.update {
                            it.copy(
                                errorDescripcion = "La descripción no puede estar vacía"
                            )
                        }
                    }
                    else if(descripcionBuscada != null && descripcionBuscada.categoriaId != _uiState.value.categoriaId) {
                        _uiState.update {
                            it.copy(
                                errorDescripcion = "La descripción ya existe"
                            )
                        }
                    }

                    if(_uiState.value.errorDescripcion == ""){
                        if(_uiState.value.categoriaId == null)
                            categoriaRepository.addCategoria(_uiState.value.toEntity())
                        else
                            categoriaRepository.updateCategoria(
                                _uiState.value.categoriaId ?: 0,
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
            CategoriaUiEvent.Delete -> {
                viewModelScope.launch {
                    categoriaRepository.deleteCategoria(_uiState.value.categoriaId ?: 0)
                }
            }
        }
    }

    fun CategoriaUiState.toEntity() = CategoriaDto(
        categoriaId = categoriaId,
        descripcion = descripcion ?: "",
        fechaCreacion = fechaCreacion
    )
}