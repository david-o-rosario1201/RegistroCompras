@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package edu.ucne.registrocompras.presentation.compra

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrocompras.R
import edu.ucne.registrocompras.data.local.entities.CompraEntity
import edu.ucne.registrocompras.data.local.entities.ProductoEntity
import edu.ucne.registrocompras.ui.theme.RegistroComprasTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CompraListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: CompraViewModel = hiltViewModel(),
    onClickCompra: (Int) -> Unit,
    onAddCompra: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CompraListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState = uiState,
        onClickCompra = onClickCompra,
        onAddCompra = onAddCompra,
        onDeleteCompra = { compraId ->
            viewModel.onEvent(CompraUiEvent.CompraIdChanged(compraId))
            viewModel.onEvent(CompraUiEvent.Delete)
        }
    )
}

@Composable
private fun CompraListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: CompraUiState,
    onClickCompra: (Int) -> Unit,
    onAddCompra: () -> Unit,
    onDeleteCompra: (Int) -> Unit
){
    val compras = remember { mutableStateListOf(*uiState.compras.toTypedArray()) }

    LaunchedEffect(uiState.compras) {
        compras.clear()
        compras.addAll(uiState.compras)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lista de Compras",
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
                onClick = onAddCompra
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar Nueva Compra"
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
                        if(compras.isEmpty()){
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
                            items(
                                items = compras,
                                key = { it.compraId ?: 0 }
                            ){
                                CompraRow(
                                    it = it,
                                    productos = uiState.productos,
                                    onClickCompra = onClickCompra,
                                    onDeleteCompra = onDeleteCompra,
                                    compras = compras,
                                    modifier = Modifier.animateItemPlacement(tween(200))
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
private fun CompraRow(
    it: CompraEntity,
    productos: List<ProductoEntity>,
    onClickCompra: (Int) -> Unit,
    onDeleteCompra: (Int) -> Unit,
    compras: MutableList<CompraEntity>,
    modifier: Modifier = Modifier
){
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val descripcionProducto = productos.find { producto ->
        producto.productoId == it.productoId
    }?.nombre ?: ""

    val scope = rememberCoroutineScope()
    val swipeToDismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { state ->
            if(state == SwipeToDismissBoxValue.EndToStart){
                scope.launch {
                    delay(500)
                    onDeleteCompra(it.compraId ?: 0)
                    compras.remove(it)
                }
                true
            } else{
                false
            }
        }
    )

    LaunchedEffect(compras) {
        swipeToDismissState.snapTo(SwipeToDismissBoxValue.Settled)
    }

    SwipeToDismissBox(
        state = swipeToDismissState,
        backgroundContent = {
            val backgroundColor by animateColorAsState(
                targetValue = when (swipeToDismissState.currentValue) {
                    SwipeToDismissBoxValue.StartToEnd -> Color.Green
                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                    SwipeToDismissBoxValue.Settled -> Color.White
                }, label = ""
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
            )
        },
        modifier = modifier
    ){
        Card(
            onClick = {
                onClickCompra(it.compraId ?: 0)
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
                    painter = painterResource(R.drawable.compras_image),
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
                        text = "Fecha de Creación: ${dateFormat.format(it.fechaCompra)}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = "Producto: $descripcionProducto",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = "Cantidad: ${it.cantidad}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = "Precio Unitario: ${it.precioUnitario}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = "Total Compra: ${it.totalCompra}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CompraListScreenPreview() {
    RegistroComprasTheme {
        CompraListScreen(
            drawerState = DrawerState(DrawerValue.Closed),
            scope = rememberCoroutineScope(),
            onClickCompra = {},
            onAddCompra = {}
        )
    }
}