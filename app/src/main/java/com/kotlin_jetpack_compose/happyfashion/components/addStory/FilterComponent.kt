package com.kotlin_jetpack_compose.happyfashion.components.addStory

import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kotlin_jetpack_compose.happyfashion.R


val defaultMatrix = ColorMatrix(
    floatArrayOf(
        1f, 0f, 0f, 0f, 0f, // Red channel
        0f, 1f, 0f, 0f, 0f, // Green channel
        0f, 0f, 1f, 0f, 0f, // Blue channel
        0f, 0f, 0f, 1f, 0f  // Alpha channel
    )
)

val grayscaleMatrix = ColorMatrix(
    floatArrayOf(
        0.3f, 0.3f, 0.3f, 0f, 0f, // Red channel
        0.3f, 0.3f, 0.3f, 0f, 0f, // Green channel
        0.3f, 0.3f, 0.3f, 0f, 0f, // Blue channel
        0f, 0f, 0f, 1f, 0f  // Alpha channel
    )
)
val sepiaMatrix = ColorMatrix(
    floatArrayOf(
        0.393f, 0.769f, 0.189f, 0f, 0f, // Red channel
        0.349f, 0.686f, 0.168f, 0f, 0f, // Green channel
        0.272f, 0.534f, 0.131f, 0f, 0f, // Blue channel
        0f, 0f, 0f, 1f, 0f  // Alpha channel
    )
)
val brightnessMatrix = ColorMatrix(
    floatArrayOf(
        1f, 0f, 0f, 0f, 0.5f, // Red channel (brightness adjustment)
        0f, 1f, 0f, 0f, 0.5f, // Green channel (brightness adjustment)
        0f, 0f, 1f, 0f, 0.5f, // Blue channel (brightness adjustment)
        0f, 0f, 0f, 1f, 0f  // Alpha channel
    )
)
val contrastMatrix = ColorMatrix(
    floatArrayOf(
        1.5f, 0f, 0f, 0f, -0.5f, // Red channel (contrast adjustment)
        0f, 1.5f, 0f, 0f, -0.5f, // Green channel (contrast adjustment)
        0f, 0f, 1.5f, 0f, -0.5f, // Blue channel (contrast adjustment)
        0f, 0f, 0f, 1f, 0f  // Alpha channel
    )
)
val clarendonMatrix = ColorMatrix(
    floatArrayOf(
        1.4f, 0f, 0f, 0f, -30f, // Red channel
        0f, 1.4f, 0f, 0f, -30f, // Green channel
        0f, 0f, 1.4f, 0f, -30f, // Blue channel
        0f, 0f, 0f, 1f, 0f  // Alpha channel
    )
)
val moonMatrix = ColorMatrix(
    floatArrayOf(
        0.6f, 0.6f, 0.6f, 0f, 0f, // Red channel
        0.6f, 0.6f, 0.6f, 0f, 0f, // Green channel
        0.6f, 0.6f, 0.6f, 0f, 0f, // Blue channel
        0f, 0f, 0f, 1f, 0f  // Alpha channel
    )
)

enum class Filter(private val colorMatrix: ColorMatrix){
    DEFAULT(defaultMatrix),
    BRIGHTNESS(brightnessMatrix),
    CONTRAST(contrastMatrix),
    CLARE(clarendonMatrix),
    GRAYSCALE(grayscaleMatrix),
    SEPIA(sepiaMatrix),
    MOON(moonMatrix);

    fun getColorMatrix(): ColorMatrix {
        return colorMatrix
    }
}

internal val LocalFilter = compositionLocalOf { mutableStateOf(ColorMatrix(defaultMatrix.values)) }

@Composable
fun BoxScope.FilterComponent(){
    var filter = LocalFilter.current
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .systemBarsPadding()
            .align(Alignment.BottomCenter)
            .background(color = Color.Black.copy(alpha = .5f))
            .padding(all = 10.dp)) {
        items(Filter.entries.toList()){ filterColor ->
            FilterItem(filter = filterColor, isFilterSelect = filterColor.getColorMatrix() == filter.value){
                filter.value = it.getColorMatrix()
            }
        }
    }
}

@Composable
fun FilterItem(filter: Filter, isFilterSelect: Boolean, onFilter: (Filter) -> Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = filter.name, modifier = Modifier.padding(5.dp), Color.White )
        Image(
            painter = painterResource(id = R.drawable.filter),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .border(
                    if (isFilterSelect) 4.dp else 0.dp,
                    color = Color.White
                )
                .clickable {
                    onFilter(filter)
                },
            colorFilter = ColorFilter.colorMatrix(filter.getColorMatrix()))
    }
}

@Preview(showBackground = true)
@Composable
fun FilterPreviewPreview(){
    Box{
        FilterComponent()
    }
}

