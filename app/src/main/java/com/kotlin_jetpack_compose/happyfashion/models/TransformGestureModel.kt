package com.kotlin_jetpack_compose.happyfashion.models

import androidx.compose.ui.geometry.Offset

data class TransformGestureModel(
    var scale: Float,
    var rotation: Float,
    var offset: Offset
){
    companion object{
        val Default = TransformGestureModel(
            scale = 1f,
            rotation = 0f,
            offset = Offset.Zero
        )
    }

    val isDefault: Boolean
        get() = this == Default

}