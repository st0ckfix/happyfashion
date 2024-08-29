package com.kotlin_jetpack_compose.happyfashion.components.addStory.display

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.kotlin_jetpack_compose.happyfashion.components.addStory.LocalFilter
import com.kotlin_jetpack_compose.happyfashion.components.addStory.LocalState
import com.kotlin_jetpack_compose.happyfashion.components.addStory.State

@Composable
fun ImageDisplay(modifier: Modifier, image: String){
    println("Create Image")
    val filter = LocalFilter.current
    val state = LocalState.current
    Image(
        painter = rememberAsyncImagePainter(model = image),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                state.value = State.DEFAULT
            },
        colorFilter = ColorFilter.colorMatrix(filter.value)
    )

    BackHandler {
        state.value = State.DEFAULT
    }
}