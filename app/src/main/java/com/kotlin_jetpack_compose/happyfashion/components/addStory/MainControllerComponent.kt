package com.kotlin_jetpack_compose.happyfashion.components.addStory

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kotlin_jetpack_compose.happyfashion.items.ButtonItem

@Composable
fun BoxScope.MainControlAddStory(onState: (State) -> Unit){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxWidth()
            .padding(top = 15.dp)
            .align(Alignment.TopCenter)) {

        ButtonItem(Modifier) {
            IconButton(onClick = {
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.White)
            }
        }
        for(state in arrayOf(State.TITLE, State.STICKERS, State.MUSIC, State.EFFECT, State.DRAW))
            ButtonItem(Modifier) {
                IconButton(onClick = {
                    onState(state)
                }) {
                    Image(
                        painter = painterResource(id = state.drawable),
                        colorFilter = ColorFilter.tint(Color.White),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun MainControlAddStoryPreview(){
    Box {
        MainControlAddStory{}
    }
}