package com.kotlin_jetpack_compose.happyfashion.models

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.text.font.FontFamily
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.kotlin_jetpack_compose.happyfashion.ui.theme.robotoFont

data class StoryModel(
    @SerializedName("id") val id: String,
    @SerializedName("image_url") val imageUrl: Any? = null,
    @SerializedName("audio_url") val audioUrl: Any? = null,
    @SerializedName("video_url") val videoUrl: Any? = null,
    @SerializedName("content_list") val contentList: List<FontDecorationModel>? = null,
    @SerializedName("image_decoration") var imageDecoration: TransformGestureModel? = null,
    @SerializedName("video_decoration") var videoDecoration: TransformGestureModel? = null,
    @SerializedName("image_filter") var imageFilter: ColorMatrix? = null,
    @SerializedName("time_created") val timeCreated: Long,
){
    companion object {
        fun fromJson(json: String) : StoryModel{
            return Gson().fromJson(json, StoryModel::class.java)
        }
    }
}

