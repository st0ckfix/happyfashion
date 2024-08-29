package com.kotlin_jetpack_compose.happyfashion.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class CommentModel(
    @SerializedName("id") val id: String,
    @SerializedName("user") val user: UserModel,
    @SerializedName("content") val content: String,
    @SerializedName("time_created") val timeCreated: Long
) {
    companion object {
        fun fromJson(json: String): CommentModel {
            return Gson().fromJson(json, CommentModel::class.java)
        }
    }
}