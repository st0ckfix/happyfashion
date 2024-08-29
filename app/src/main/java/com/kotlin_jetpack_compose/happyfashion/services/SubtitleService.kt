package com.kotlin_jetpack_compose.happyfashion.services

import com.kotlin_jetpack_compose.happyfashion.lyric
import com.kotlin_jetpack_compose.happyfashion.verticalLyric
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class SubtitleService {
    data class Subtitle(
        val index: Int,
        val start: java.sql.Timestamp,
        val end: java.sql.Timestamp,
        val content: String
    ){
        companion object {
            val Default = Subtitle(
                index = 0,
                start = java.sql.Timestamp(0),
                end = java.sql.Timestamp(0),
                content = ""
            )
        }
    }

    private var subtitlesFlow: List<Subtitle>? = emptyList()
    private var subtitlesVertical: List<Subtitle> ? = emptyList()

    fun getSubtitleFlowTest(): List<Subtitle> {
        return parse(lyric)
    }

    fun getSubtitleVerticalTest(): List<Subtitle> {
        return parse(verticalLyric)
    }

    suspend fun setSubtitlesFlow(url: String){
        subtitlesFlow = fetchSubtitle(url)?.let { parse(it) }
    }

    suspend fun setSubtitlesVertical(url: String){
        subtitlesVertical = fetchSubtitle(url)?.let { parse(it) }
    }

    fun getSubtitlesFlow() : List<Subtitle>? = subtitlesFlow

    fun getSubtitlesVertical() : List<Subtitle>? = subtitlesVertical

    private suspend fun fetchSubtitle(url: String) : String? = withContext(Dispatchers.IO){
        val request = Request.Builder()
            .url(url)
            .build()
        OkHttpClient().newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            response.body?.string()?.trim()
        }
    }

    private fun parse(lyric: String): List<Subtitle>{
        /**
         * Split every single subtitle
         */
        val splitter = lyric.trim().split("\n\n")
        return splitter.map { split ->
            /**
             * Split every single line of subtitle
             */
            val item: List<String?> = split.split("\n")
            /**
             * Get index, timestamp and content of subtitle
             */
            val timestamp = item[1]!!.split(" --> ")
            val index = item[0]!!.trim().toInt()

            /**
             * Convert timestamp to milliseconds
             */
            val start = timestamp[0].trim().replace(":",",").split(",").map { it.toInt() }
            val end = timestamp[1].trim().replace(":",",").split(",").map { it.toInt() }
            /**
             * Get content of subtitle, item3 and item4 can be null
             */
            val content = item[2]!!.trim() + (if(item.size > 3) item[3] else "") + if(item.size > 4) item[4] else ""
            Subtitle(
                index = index,
                start = java.sql.Timestamp((start[0] * 3600000L) + (start[1] * 60000L) + (start[2] * 1000L) + start[3]),
                end = java.sql.Timestamp((end[0] * 3600000L) + (end[1] * 60000L) + (end[2] * 1000L) + end[3]),
                content = content
            )
        }
    }
}