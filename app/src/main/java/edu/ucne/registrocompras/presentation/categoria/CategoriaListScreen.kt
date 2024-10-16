@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package edu.ucne.registrocompras.presentation.categoria

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import edu.ucne.registrocompras.data.local.entities.CategoriaEntity
import edu.ucne.registrocompras.data.remote.dto.CategoriaDto
import edu.ucne.registrocompras.ui.theme.RegistroComprasTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CategoriaListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: CategoriaViewModel = hiltViewModel(),
    onClickCategoria: (Int) -> Unit,
    onAddCategoria: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CategoriaListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState = uiState,
        onClickCategoria = onClickCategoria,
        onAddCategoria = onAddCategoria,
        onDeleteCategoria = { categoriaId ->
            viewModel.onEvent(CategoriaUiEvent.CategoriaIdChange(categoriaId))
            viewModel.onEvent(CategoriaUiEvent.Delete)
        }
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun CategoriaListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: CategoriaUiState,
    onClickCategoria: (Int) -> Unit,
    onAddCategoria: () -> Unit,
    onDeleteCategoria: (Int) -> Unit
){
    val categorias = remember { mutableStateListOf(*uiState.categorias.toTypedArray()) }

    LaunchedEffect(uiState.categorias) {
        categorias.clear()
        categorias.addAll(uiState.categorias)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lista de Categorías",
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
                onClick = onAddCategoria
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar Nueva Categoría"
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
                        if(categorias.isEmpty()){
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
                                items = categorias,
                                key = { it.categoriaId ?: 0 }
                            ){
                                CategoriaRow(
                                    it = it,
                                    onClickCategoria = onClickCategoria,
                                    onDeleteCategoria = onDeleteCategoria,
                                    categorias = categorias,
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
private fun CategoriaRow(
    it: CategoriaEntity,
    onClickCategoria: (Int) -> Unit,
    onDeleteCategoria: (Int) -> Unit,
    categorias: MutableList<CategoriaEntity>,
    modifier: Modifier = Modifier
){
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val scope = rememberCoroutineScope()
    val swipeToDismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { state ->
            if(state == SwipeToDismissBoxValue.EndToStart){
                scope.launch {
                    delay(500)
                    onDeleteCategoria(it.categoriaId ?: 0)
                    categorias.remove(it)
                }
                true
            } else{
                false
            }
        }
    )

    LaunchedEffect(categorias) {
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
                onClickCategoria(it.categoriaId ?: 0)
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
                    painter = painterResource(R.drawable.categorias_image),
                    contentDescription = "Categorías Image",
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
                        text = "Descripción: ${it.descripcion}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CategoriaListScreenPreview(){
    RegistroComprasTheme {
        CategoriaListScreen(
            drawerState = DrawerState(DrawerValue.Closed),
            scope = rememberCoroutineScope(),
            onClickCategoria = {},
            onAddCategoria = {}
        )
    }
}