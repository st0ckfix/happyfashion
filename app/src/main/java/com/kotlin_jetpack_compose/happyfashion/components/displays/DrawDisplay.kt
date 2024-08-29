/*
package com.kotlin_jetpack_compose.happyfashion.components.displays

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kotlin_jetpack_compose.happyfashion.components.addStory.DrawCircle

@Composable
fun DrawDisplay(modifier: Modifier, circles: List<DrawCircle>, redo: Int = 0, isEdit: Boolean = false) {
    return if(circles.isEmpty() && !isEdit) Box {}
    else {
        println("DISPLAY")
        Canvas(
            modifier = modifier
        ) {
            circles.subList(0, circles.size + redo).forEach {
                drawCircle(
                    color = it.color.copy(alpha = it.alpha),
                    center = it.center,
                    radius = it.radius
                )
            }
        }
    }
}*/
