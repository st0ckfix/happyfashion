package com.kotlin_jetpack_compose.happyfashion.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class PostModel(
    @SerializedName("key") val key: String,
    @SerializedName("post_user") val postUser: PostUser,
    @SerializedName("post_rule") val postRule: PostRule ?= PostRule(),
    @SerializedName("post_header") val postHeader: PostHeader ?= null,
    @SerializedName("post_content") val postContent: PostContent,
    @SerializedName("post_detail") val postDetail: PostDetail,
    @SerializedName("timestamp") val timestamp: Long
) {
    constructor() : this("", PostUser("", ""), PostRule(), PostHeader(), PostContent(), PostDetail(""), 0)

    companion object {
        fun fromJson(json: String): PostModel {
            return Gson().fromJson(json, PostModel::class.java)
        }
    }
}

data class PostUser(
    @SerializedName("uid") val uid: String,
    @SerializedName("avatar") val avatar: String,
){
    constructor() : this("", "")

    companion object {
        fun fromJson(json: String): PostUser {
            return Gson().fromJson(json, PostUser::class.java)
        }
    }
}

data class PostRule(
    @SerializedName("allow_comment") val isAllowComment: Boolean = true,
    @SerializedName("allow_share") val isAllowShare: Boolean = true,
){
    constructor() : this(true, true)
    companion object {
        fun fromJson(json: String): PostRule {
            return Gson().fromJson(json, PostRule::class.java)
        }
    }
}

data class PostHeader(
    @SerializedName("venue") val venue: String ?= null,
    @SerializedName("mention") val mention: List<String> ?= null
){
    constructor() : this(null, null)
    companion object {
        fun fromJson(json: String): PostHeader {
            return Gson().fromJson(json, PostHeader::class.java)
        }
    }
}

data class PostContent(
    @SerializedName("video_url") val video_url: String ?= null,
    @SerializedName("images_url") val images_url: List<String> = emptyList(),
){
    constructor() : this(null)

    companion object {
        fun fromJson(json: String): PostContent {
            return Gson().fromJson(json, PostContent::class.java)
        }
    }

}

data class PostDetail(
    @SerializedName("content") val content: String ?,
    @SerializedName("likes") val likes: List<String> = emptyList(),
    @SerializedName("comments") val comments: List<String> = emptyList(),
){
    constructor() : this(null)
    companion object {
        fun fromJson(json: String): PostDetail {
            return Gson().fromJson(json, PostDetail::class.java)
        }
    }
}