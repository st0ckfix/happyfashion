package com.kotlin_jetpack_compose.happyfashion.services.paging_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kotlin_jetpack_compose.happyfashion.models.GIPHY
import com.kotlin_jetpack_compose.happyfashion.services.GiphyApi

const val PAGE_SIZE = 25

enum class EnumGIPHY {
    GIF_TRENDING, GIF_SEARCH, EMOJI_TRENDING
}

class GiphyPagingSource(
    private val query: String? = null,
    private val mode: EnumGIPHY
) : PagingSource<Int, GIPHY>(){


    override fun getRefreshKey(state: PagingState<Int, GIPHY>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GIPHY> {
        val builder = GiphyApi.Builder()
        return try{
            val page = params.key ?: 0
            val response = when(mode){
                EnumGIPHY.GIF_TRENDING -> builder.GifTrending(page).build()
                EnumGIPHY.GIF_SEARCH -> builder.GifSearch(query!!, page).build()
                EnumGIPHY.EMOJI_TRENDING -> builder.EmojiTrending(page).build()
            }
            println("Request API $page")
            LoadResult.Page(
                data = response,
                prevKey = if(page == 0) null else page - 1,
                nextKey = if(response.isEmpty()) null else page + 1
            )
        }
        catch (e: Exception){
            print(e.message)
            LoadResult.Error(e)
        }
    }
}
