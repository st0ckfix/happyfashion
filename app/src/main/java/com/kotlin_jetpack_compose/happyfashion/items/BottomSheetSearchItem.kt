package com.kotlin_jetpack_compose.happyfashion.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSearchItem(
    onDismiss: () -> Unit,
    onSearch: (String) -> Unit,
    content: @Composable () -> Unit
) {
    var query by remember { mutableStateOf("") }
    ModalBottomSheet(
        modifier = Modifier.imePadding(),
        //containerColor = Color(0xFF2E2C2C),
        onDismissRequest = { onDismiss() }) {
        SearchBar(
            modifier = Modifier.padding(all = 10.dp),
            colors = SearchBarDefaults.colors(
                containerColor = Color.Transparent,
                //dividerColor = Color.White,
                inputFieldColors = SearchBarDefaults.inputFieldColors(
                    //focusedTextColor = Color.White,
                    //unfocusedTextColor = Color.White
                )
            ),
            query = query,
            onQueryChange = {
                query = it
                if(query.isEmpty()){
                    onSearch(query)
                }
            },
            onSearch = { onSearch(it) },
            active = true,
            onActiveChange = {},
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            placeholder = {
                Text(text = "Searching", fontStyle = FontStyle.Italic)
            }
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetSearchItemPreview() {
    BottomSheetSearchItem(
        onDismiss = {},
        onSearch = {},
        content = {}
    )
}