/*
package com.kotlin_jetpack_compose.happyfashion.components.addStory

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kotlin_jetpack_compose.happyfashion.R
import com.kotlin_jetpack_compose.happyfashion.components.displays.DrawDisplay
import com.kotlin_jetpack_compose.happyfashion.items.ColorPanel
import com.kotlin_jetpack_compose.happyfashion.viewmodels.UiEvent
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

internal val LocalDrawDecoration = compositionLocalOf { DrawDecoration() }

internal open class DrawDecoration{
    var redo by mutableIntStateOf(0)
    var color by mutableStateOf(Color.White)
    var radius by mutableFloatStateOf(5f)
    var alpha by mutableFloatStateOf(1f)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Draw(onEvent:(UiEvent) -> Unit) {
    val drawState = LocalDrawDecoration.current

    val circles = remember { mutableStateListOf<DrawCircle>() }
    var previousPoint by remember { mutableStateOf<Offset?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PenControl(
            undo = {
                drawState.redo = max(- circles.size, drawState.redo - 50)
            },
            redo = {
                drawState.redo = min(0, drawState.redo + 50)
            },
            done = {
                //onEvent(UiEvent.AddDraw(circles.subList(0, circles.size + drawState.redo)))
                circles.clear()
            }
        )
        DrawDisplay(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            circles.add(
                                DrawCircle(
                                    center = Offset(it.x, it.y),
                                    color = drawState.color,
                                    radius = drawState.radius,
                                    alpha = drawState.alpha
                                )
                            )
                            previousPoint = Offset(it.x, it.y)
                        }
                        MotionEvent.ACTION_MOVE -> {
                            val currentPoint = Offset(it.x, it.y)
                            previousPoint?.let { offset: Offset ->
                                val distance = abs((offset - currentPoint).getDistance())
                                val steps = (distance / 5f).roundToInt()
                                for (i in 1..steps) {
                                    val t = i.toFloat() / steps
                                    val interpolatedPoint = offset + (currentPoint - offset) * t
                                    circles.add(
                                        DrawCircle(
                                            center = interpolatedPoint,
                                            color = drawState.color,
                                            radius = drawState.radius,
                                            alpha = drawState.alpha
                                        )
                                    )
                                }
                            }
                            previousPoint = currentPoint
                        }
                        MotionEvent.ACTION_UP -> {
                            previousPoint = null
                        }
                    }
                    true
                },
            circles = circles,
            redo = drawState.redo,
            isEdit = true
        )
        ColorPanel(defaultColor = drawState.color, isBackgroundColor = false) {
            drawState.color = it
        }
    }



}


@Composable
fun PenControl(redo:() -> Unit, undo:() -> Unit, done:() -> Unit){
    val drawState = LocalDrawDecoration.current
    var selectPen by remember { mutableIntStateOf(0) }
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
        IconButton(onClick = {
            undo()
        }) {
            Image(
                painter = painterResource(id = R.drawable.undo),
                contentDescription = "Undo",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(24.dp))
        }
        IconButton(onClick = {
            redo()
        }) {
            Image(
                painter = painterResource(id = R.drawable.redo),
                contentDescription = "Redo",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(24.dp))
        }
        IconButton(onClick = {
            selectPen = 0
            drawState.radius = 5f
            drawState.alpha = 1f
        }) {
            Image(
                painter = painterResource(id = R.drawable.normal_pen),
                contentDescription = "Normal Pen",
                colorFilter = ColorFilter.tint(if(selectPen == 0) Color.White else Color.DarkGray),
                modifier = Modifier.size(24.dp))
        }
        IconButton(onClick = {
            selectPen = 1
            drawState.radius = 15f
            drawState.alpha = 1f
        }) {
            Image(
                painter = painterResource(id = R.drawable.brush_pen),
                contentDescription = "Brush Pen",
                colorFilter = ColorFilter.tint(if(selectPen == 1) Color.White else Color.DarkGray),
                modifier = Modifier.size(24.dp))
        }
        IconButton(onClick = {
            selectPen = 2
            drawState.radius = 15f
            drawState.alpha = .5f
        }) {
            Image(
                painter = painterResource(id = R.drawable.marker_pen),
                contentDescription = "Marker Pen",
                colorFilter = ColorFilter.tint(if(selectPen == 2) Color.White else Color.DarkGray),
                modifier = Modifier.size(24.dp))
        }
        IconButton(onClick = {
            done()
        }) {
            Text(text = "Done", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

data class DrawCircle(
    var center: Offset,
    var color: Color,
    var radius: Float,
    var alpha: Float,
)

@Preview(showBackground = true)
@Composable
fun DrawPreview(){
    Draw{}
}*/
