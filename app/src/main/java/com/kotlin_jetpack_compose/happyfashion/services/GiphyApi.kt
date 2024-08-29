package com.kotlin_jetpack_compose.happyfashion.services

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kotlin_jetpack_compose.happyfashion.models.GIPHY
import com.kotlin_jetpack_compose.happyfashion.models.TransformGestureModel
import com.kotlin_jetpack_compose.happyfashion.services.paging_sources.PAGE_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

class GiphyApi {
    class Builder{
        private val client = OkHttpClient()
        private var url: String = ""

        fun GifTrending(page: Int) = apply { this.url = "https://api.giphy.com/v1/gifs/trending?api_key=8P4E7f5Mitt0wQujYm48xHCKl8FDVUlv&limit=25&offset=$page&rating=g&bundle=messaging_non_clips" }

        fun GifSearch(key: String, page: Int) = apply { this.url = "https://api.giphy.com/v1/gifs/search?api_key=8P4E7f5Mitt0wQujYm48xHCKl8FDVUlv&q=$key&limit=$PAGE_SIZE&offset=$page&rating=g&lang=en&bundle=messaging_non_clips"}

        fun EmojiTrending(page: Int) = apply { this.url = "https://api.giphy.com/v2/emoji?api_key=8P4E7f5Mitt0wQujYm48xHCKl8FDVUlv&limit=${PAGE_SIZE * 2}&offset=$page"}

        suspend fun build() : List<GIPHY> = withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                // Lấy dữ liệu JSON trả về
                val responseData = response.body?.string()
                // Xử lý dữ liệu JSON
                if (responseData != null) {
                    val gson = Gson()
                    val type = object : TypeToken<Map<String, Any>>() {}.type

                    val resultMap: Map<String, Any> = gson.fromJson(responseData, type)
                    val listData = resultMap["data"] as List<*>

                    val listGif =
                        listData.map {it as Map<*, *>
                            GIPHY(
                                url = ((it["images"] as Map<*, *>)["fixed_height_small"] as Map<*, *>)["url"].toString(),
                                width = ((it["images"] as Map<*, *>)["fixed_height_small"] as Map<*, *>)["width"].toString(),
                                height = ((it["images"] as Map<*, *>)["fixed_height_small"] as Map<*, *>)["height"].toString(),
                                transformGesture = TransformGestureModel.Default
                            )
                        }.toList()

                    return@use listGif

                } else {
                    return@use emptyList()
                }
            }
        }
    }
}