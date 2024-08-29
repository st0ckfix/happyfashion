package com.kotlin_jetpack_compose.happyfashion.models

import com.google.gson.annotations.SerializedName

data class AudioModel(
    @SerializedName("key") val key: String,
    @SerializedName("title") val title: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("audio_url") val audioUrl: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("subtitle") val subtitle: String ? = null,
    @SerializedName("subtitle_word") val subtitleWord: String ? = null
){
    constructor() : this("", "", "", "", "")

    companion object{
        val Default = AudioModel()
    }
}