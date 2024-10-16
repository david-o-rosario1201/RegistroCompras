package edu.ucne.registrocompras.presentation.compra

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import edu.ucne.registrocompras.ui.theme.RegistroComprasTheme

@Composable
fun DropDownMenuProductos(
    uiState: CompraUiState,
    onEvent: (CompraUiEvent) -> Unit
){
    var expanded by remember { mutableStateOf(false) }
    var selectItem by remember { mutableStateOf(uiState.productoId.toString()) }
    var textFielSize by remember { mutableStateOf(Size.Zero) }
    val productos = uiState.productos

    val icon = if(expanded) {
        Icons.Filled.KeyboardArrowUp
    }else{
        Icons.Filled.KeyboardArrowDown
    }

    LaunchedEffect(uiState.productoId) {
        selectItem = productos.find { it.categoriaId == uiState.productoId }?.descripcion ?: ""
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            label = {
                Text("Producto")
            },
            value = selectItem,
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .onGloballyPositioned { coordinates ->
                    textFielSize = coordinates.size.toSize()
                },
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                )
            },
            readOnly = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(
                with(LocalDensity.current) {
                    textFielSize.width.toDp()
                }
            )
        ) {
            productos.forEach { producto ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        selectItem = producto.nombre
                        onEvent(CompraUiEvent.ProductoIdChanged(producto.productoId ?: 0))
                    },
                    text = {
                        Text(text = producto.nombre)
                    }
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DropDownMenuProductosPreview() {
    RegistroComprasTheme {
        DropDownMenuProductos(
            uiState = CompraUiState(),
            onEvent = {}
        )
    }
}