package com.kotlin_jetpack_compose.happyfashion.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import com.google.gson.annotations.SerializedName
import com.kotlin_jetpack_compose.happyfashion.ui.theme.robotoFont
import java.util.UUID

data class FontDecorationModel(
    @SerializedName("font_content") var fontContent: String,
    @SerializedName("font_size") var fontSize: Int,
    @SerializedName("font_color") var fontColor: Color,
    @SerializedName("font_family") var fontFamily: FontFamily,
    @SerializedName("font_background") var fontBackground: Color
){
    companion object {
        val Default = FontDecorationModel(
            fontContent = "",
            fontSize = 15,
            fontColor = Color.White,
            fontFamily = robotoFont,
            fontBackground = Color.Transparent
        )
    }
}