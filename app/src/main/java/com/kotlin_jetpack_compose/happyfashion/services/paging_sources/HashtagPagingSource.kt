package com.kotlin_jetpack_compose.happyfashion.services.paging_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.kotlin_jetpack_compose.happyfashion.models.HASHTAG
import com.kotlin_jetpack_compose.happyfashion.services.URL
import kotlinx.coroutines.tasks.await

class HashtagPagingSource(
    private val ref: DatabaseReference = Firebase.database(URL).reference
) : PagingSource<DataSnapshot, HASHTAG>(){

    override fun getRefreshKey(state: PagingState<DataSnapshot, HASHTAG>): DataSnapshot? = null

    override suspend fun load(params: LoadParams<DataSnapshot>): LoadResult<DataSnapshot, HASHTAG> {
        return try{
            val query = ref.child("hashtag").orderByKey().limitToFirst(25)
            val currentPage = params.key ?: query.get().await()
            val lastVisibleProductKey = currentPage.children.last().key
            val nextPage = query.startAfter(lastVisibleProductKey).get().await()
            val response = currentPage.children.map { snapshot ->
                snapshot.toHashtag()
            }
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextPage
            )
        }
        catch (e: Exception){
            print(e.message)
            LoadResult.Error(e)
        }
    }
    private fun DataSnapshot.toHashtag() = HASHTAG(
        tag = this.child("tag").value.toString(),
        times = this.child("times").value.toString().toInt()
    )
}