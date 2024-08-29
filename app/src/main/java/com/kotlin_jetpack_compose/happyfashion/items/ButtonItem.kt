package com.kotlin_jetpack_compose.happyfashion.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

@Composable
fun ButtonItem(modifier: Modifier = Modifier, content: @Composable () -> Unit ){
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = Color.Black.copy(alpha = .7f)),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}