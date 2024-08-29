package com.kotlin_jetpack_compose.happyfashion.components.addStory.stickers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kotlin_jetpack_compose.happyfashion.components.Location
import com.kotlin_jetpack_compose.happyfashion.components.addStory.FullDisplayType
import com.kotlin_jetpack_compose.happyfashion.components.addStory.FullDisplayUnit
import com.kotlin_jetpack_compose.happyfashion.components.addStory.LocalFullDisplay
import com.kotlin_jetpack_compose.happyfashion.items.BottomSheetSearchItem

@Composable
fun PlaceSheet(
    onDismiss:() -> Unit
){
    var requestLocation by remember { mutableStateOf(false) }
    var location by remember { mutableStateOf("") }
    val fullState = LocalFullDisplay.current

    BottomSheetSearchItem(
        onDismiss = {},
        onSearch = {},
        content = {
            if(location.isEmpty())
                TextButton(
                    onClick = {
                        requestLocation = true
                    }
                ) {
                    Text(text = "Get Location", modifier = Modifier.padding(all = 5.dp))
                }
            else
                Text(text = location, modifier = Modifier.padding(all = 5.dp).clickable {
                    val item = FullDisplayUnit()
                    item.type = FullDisplayType.PLACE
                    item.content = location
                    fullState.add(item)
                    onDismiss()
                })
        }
    )

    if(requestLocation)
        Location(
            onLocation = {
                location = it.toString()
            },
            onFail = {}
        )
}

@Preview(showBackground = true)
@Composable
fun PlacePreview() {
    Box {
        PlaceSheet{}
    }
}