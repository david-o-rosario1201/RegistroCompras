@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.registrocompras.presentation.producto

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrocompras.R
import edu.ucne.registrocompras.data.remote.dto.CategoriaDto
import edu.ucne.registrocompras.data.remote.dto.ProductoDto
import edu.ucne.registrocompras.data.remote.dto.ProveedorDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ProductoListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: ProductoViewModel = hiltViewModel(),
    onClickProducto: (productoId: Int) -> Unit,
    onAddProducto: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ProductoListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState = uiState,
        onClickProducto = onClickProducto,
        onAddProducto = onAddProducto
    )
}

@Composable
private fun ProductoListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: ProductoUiState,
    onClickProducto: (productoId: Int) -> Unit,
    onAddProducto: () -> Unit
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lista de Productos",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddProducto
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar Nuevo Producto"
                )
            }
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(
                    start = 15.dp,
                    end = 15.dp
                )
        ){
            Spacer(modifier = Modifier.height(32.dp))

            if(uiState.isLoading){
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    CircularProgressIndicator()
                }
            }
            else{
                if(uiState.errorCargar?.isNotBlank() == true){
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        uiState.errorCargar?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }
                    }
                }
                else{
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ){
                        if(uiState.productos.isEmpty()){
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillParentMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ){
                                    Image(
                                        painter = painterResource(R.drawable.empty_icon),
                                        contentDescription = "Lista vacía"
                                    )
                                    Text(
                                        text = "Lista vacía",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }else{
                            items(uiState.productos){
                                ProductoRow(
                                    it = it,
                                    categorias = uiState.categorias,
                                    proveedores = uiState.proveedores,
                                    onClickProducto = onClickProducto
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductoRow(
    it: ProductoDto,
    categorias: List<CategoriaDto>,
    proveedores: List<ProveedorDto>,
    onClickProducto: (productoId: Int) -> Unit
){
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val descripcionCategoria = categorias.find { categoria ->
        categoria.categoriaId == it.categoriaId
    }?.descripcion ?: ""

    val descripcionProveedor = proveedores.find { proveedor ->
        proveedor.proveedorId == it.proveedorId
    }?.nombre ?: ""

    Card(
        onClick = {
            onClickProducto(it.productoId ?: 0)
        },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFB0BEC5)
        ),
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .heightIn(min = 100.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.productos_image),
                contentDescription = "Productos Image",
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .size(96.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = "Fecha de Creación: ${dateFormat.format(it.fechaCreacion)}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Nombre: ${it.nombre}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Descripción: ${it.descripcion}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Precio: ${it.precio}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Proveedor: $descripcionProveedor",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Categoría: $descripcionCategoria",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}