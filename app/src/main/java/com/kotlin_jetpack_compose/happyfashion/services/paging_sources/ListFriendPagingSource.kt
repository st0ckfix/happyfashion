package com.kotlin_jetpack_compose.happyfashion.services.paging_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kotlin_jetpack_compose.happyfashion.models.UserModel
import com.kotlin_jetpack_compose.happyfashion.services.FirebaseService
import timber.log.Timber
import kotlin.math.min

class ListFriendPagingSource(
    private val firebaseService: FirebaseService = FirebaseService
) : PagingSource<Int, UserModel>(){

    override fun getRefreshKey(state: PagingState<Int, UserModel>): Int?{
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserModel> {
        return try{
            val page = params.key ?: 0
            val response = try {
                firebaseService.getListFollowing().subList(page * 5, min(firebaseService.getListFollowing().size, (page + 1) * 5)).map { uid ->
                    firebaseService.getUser(uid)
                }
            } catch (e: Exception){
                println(e.message.toString())
                Timber.tag("ListFriendFirebase").e(e.message.toString())
                emptyList()
            }

            println("ListFPage: $page")

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