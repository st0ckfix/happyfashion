package com.kotlin_jetpack_compose.happyfashion.components.addStory.display

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.kotlin_jetpack_compose.happyfashion.R
import com.kotlin_jetpack_compose.happyfashion.components.addStory.State

@Composable
fun ImageDisplay(modifier: Modifier, image: Any ,state: State, onState: (State) -> Unit, filter: ColorMatrix?){
    Image(
        painter = rememberAsyncImagePainter(model = image),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                if (state == State.EFFECT) {
                    onState(State.DEFAULT)
                }
            },
        colorFilter = filter?.let { ColorFilter.colorMatrix(it) }
    )
}