package com.kotlin_jetpack_compose.happyfashion.models

import androidx.compose.ui.graphics.Color
import com.kotlin_jetpack_compose.happyfashion.components.SUBTITLE_MODE

data class SubtitleDisplayModel(
    var mode: SUBTITLE_MODE,
    var fontColor: Color,
    var backgroundColor: Color
){
    companion object{
        val Default = SubtitleDisplayModel(SUBTITLE_MODE.NO, Color.White, Color.Transparent)
    }
}