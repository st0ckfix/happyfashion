package com.kotlin_jetpack_compose.happyfashion.services.paging_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kotlin_jetpack_compose.happyfashion.models.PostModel
import com.kotlin_jetpack_compose.happyfashion.services.FirebaseService
import timber.log.Timber
import kotlin.math.min

class PostPagingSource(
    private val firebase: FirebaseService = FirebaseService
) : PagingSource<Int, PostModel >(){

    override fun getRefreshKey(state: PagingState<Int, PostModel>): Int?{
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostModel> {
        return try{
            val page = params.key ?: 0

            val response = try {
                    firebase.getListPost().subList(page * 5, min(firebase.getListPost().size, (page + 1) * 5)).map {
                        firebase.fetchPostData(it)
                    }
                }

                catch (e: Exception){
                    println(e.message.toString())
                    Timber.tag("PostFirebase").e(e.message.toString())
                    emptyList()
                }

            println("PostPage: $page")

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