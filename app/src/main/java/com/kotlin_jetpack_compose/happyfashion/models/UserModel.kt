package com.kotlin_jetpack_compose.happyfashion.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name : String,
    @SerializedName("image") val image: Any
) {
    constructor() : this("", "", "")

    companion object {
        fun fromJson(json: String): UserModel {
            return Gson().fromJson(json, UserModel::class.java)
        }
    }
}