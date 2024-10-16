@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.registrocompras.presentation.proveedor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrocompras.ui.theme.RegistroComprasTheme
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ProveedorScreen(
    viewModel: ProveedorViewModel = hiltViewModel(),
    proveedorId: Int,
    goProveedorList: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ProveedorBodyScreen(
        proveedorId = proveedorId,
        uiState = uiState,
        onEvent = viewModel::onEvent,
        goProveedorList = goProveedorList
    )
}

@Composable
private fun ProveedorBodyScreen(
    proveedorId: Int,
    uiState: ProveedorUiState,
    onEvent: (ProveedorUiEvent) -> Unit,
    goProveedorList: () -> Unit
){
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    LaunchedEffect(key1 = true, key2 = uiState.success) {
        onEvent(ProveedorUiEvent.SelectedProveedor(proveedorId))

        if(uiState.success)
            goProveedorList()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if(proveedorId == 0) "Crear Proveedor" else "Modificar Proveedor",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = goProveedorList
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Ir hacia proveedor list"
                        )
                    }
                }
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(15.dp)
        ){
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        OutlinedTextField(
                            label = {
                                Text("Fecha de creación")
                            },
                            value = dateFormat.format(uiState.fechaCreacion),
                            onValueChange = {},
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .focusRequester(focusRequester)
                                .onGloballyPositioned { coordinates ->
                                    textFieldSize = coordinates.size.toSize()
                                },
                            shape = RoundedCornerShape(10.dp),
                            readOnly = true
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        OutlinedTextField(
                            label = {
                                Text("Nombre")
                            },
                            value = uiState.nombre ?: "",
                            onValueChange = {
                                onEvent(ProveedorUiEvent.NombreChange(it))
                            },
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .focusRequester(focusRequester)
                                .onGloballyPositioned { coordinates ->
                                    textFieldSize = coordinates.size.toSize()
                                },
                            shape = RoundedCornerShape(10.dp),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(
                                        FocusDirection.Next
                                    )
                                }
                            )
                        )
                        uiState.errorNombre?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        OutlinedTextField(
                            label = {
                                Text("RNC")
                            },
                            value = uiState.rnc ?: "",
                            onValueChange = {
                                onEvent(ProveedorUiEvent.RncChange(it))
                            },
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .onGloballyPositioned { coordinates ->
                                    textFieldSize = coordinates.size.toSize()
                                },
                            shape = RoundedCornerShape(10.dp),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Number
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(
                                        FocusDirection.Next
                                    )
                                }
                            )
                        )
                        uiState.errorRnc?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        OutlinedTextField(
                            label = {
                                Text("Dirección")
                            },
                            value = uiState.direccion ?: "",
                            onValueChange = {
                                onEvent(ProveedorUiEvent.DireccionChange(it))
                            },
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .onGloballyPositioned { coordinates ->
                                    textFieldSize = coordinates.size.toSize()
                                },
                            shape = RoundedCornerShape(10.dp),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(
                                        FocusDirection.Next
                                    )
                                }
                            )
                        )
                        uiState.errorDireccion?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        OutlinedTextField(
                            label = {
                                Text("Email")
                            },
                            value = uiState.email ?: "",
                            onValueChange = {
                                onEvent(ProveedorUiEvent.EmailChange(it))
                            },
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .onGloballyPositioned { coordinates ->
                                    textFieldSize = coordinates.size.toSize()
                                },
                            shape = RoundedCornerShape(10.dp),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Email
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                    onEvent(ProveedorUiEvent.Save)
                                }
                            )
                        )
                        uiState.errorEmail?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        OutlinedButton(
                            onClick = {
                                focusRequester.requestFocus()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "Empezar formulario"
                            )
                            Text("Empezar a llenar")
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        OutlinedButton(
                            onClick = {
                                onEvent(ProveedorUiEvent.Save)
                            }
                        ) {
                            Icon(
                                imageVector = if(proveedorId == 0) Icons.Default.Add else Icons.Default.Done,
                                contentDescription = "Guardar Producto"
                            )
                            Text(
                                text = if(proveedorId == 0) "Crear Proveedor" else "Modificar Proveedor"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProveedorScreenPreview(){
    RegistroComprasTheme {
        ProveedorScreen(
            proveedorId = 0,
            goProveedorList = {}
        )
    }
}