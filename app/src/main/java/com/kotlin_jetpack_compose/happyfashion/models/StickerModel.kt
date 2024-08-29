package com.kotlin_jetpack_compose.happyfashion.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class StickerModel(
    @SerializedName("question") var question: QUESTION ?= null,
    @SerializedName("place") var place: PLACE ?= null,
    @SerializedName("mention") val mention: MENTION ?= null,
    @SerializedName("hashtag") val hashtag: HASHTAG ?= null,
    @SerializedName("link") val link: LINK ?= null,
    @SerializedName("emoji") val giphy: GIPHY ?= null,
)

data class QUESTION(
    @SerializedName("content") var content: String,
    @SerializedName("transform_gesture") val transformGesture: TransformGestureModel
){
    companion object {
        val Default = QUESTION(
            content = "Ask me anything ?",
            transformGesture = TransformGestureModel.Default
        )
    }
}

data class PLACE(
    @SerializedName("content") var content: String,
    @SerializedName("transform_gesture") val transformGesture: TransformGestureModel
) {
    companion object {
        val Default = PLACE(
            content = "",
            transformGesture = TransformGestureModel.Default
        )
    }
}

data class MENTION(
    @SerializedName("user_id") val userId: String,
    @SerializedName("transform_gesture") val transformGesture: TransformGestureModel
)

data class GIPHY(
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: String,
    @SerializedName("height") val height: String,
    @SerializedName("transform_gesture") val transformGesture: TransformGestureModel
)

data class HASHTAG(
    @SerializedName("tag") val tag: String,
    @SerializedName("times") val times: Int,
    @SerializedName("transform_gesture") val transformGesture: TransformGestureModel = TransformGestureModel.Default
){
    constructor() : this("", 0, TransformGestureModel.Default)
}

data class LINK(
    @SerializedName("link") val link: String,
    @SerializedName("transform_gesture") val transformGesture: TransformGestureModel
){
    companion object {
        val Default = LINK(
            link = "",
            transformGesture = TransformGestureModel.Default
        )
    }
}
