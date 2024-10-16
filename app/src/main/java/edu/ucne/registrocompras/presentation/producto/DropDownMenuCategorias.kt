package edu.ucne.registrocompras.presentation.producto

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
    uiState: ProductoUiState,
    onEvent: (ProductoUiEvent) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectItem by remember { mutableStateOf(uiState.categoriaId.toString()) }
    var textFielSize by remember { mutableStateOf(Size.Zero) }
    val categorias = uiState.categorias

    val icon = if(expanded) {
        Icons.Filled.KeyboardArrowUp
    }else{
        Icons.Filled.KeyboardArrowDown
    }

    LaunchedEffect(uiState.categoriaId) {
        selectItem = categorias.find { it.categoriaId == uiState.categoriaId }?.descripcion ?: ""
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            label = {
                Text("Categoría")
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
            categorias.forEach { categoria ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        selectItem = categoria.descripcion
                        onEvent(ProductoUiEvent.CategoriaIdChange(categoria.categoriaId ?: 0))
                    },
                    text = {
                        Text(text = categoria.descripcion)
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
            uiState = ProductoUiState(),
            onEvent = {}
        )
    }
}