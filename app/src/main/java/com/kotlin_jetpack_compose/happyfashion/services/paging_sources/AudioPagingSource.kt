package com.kotlin_jetpack_compose.happyfashion.services.paging_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.kotlin_jetpack_compose.happyfashion.models.AudioModel
import com.kotlin_jetpack_compose.happyfashion.services.URL
import kotlinx.coroutines.tasks.await

class AudioPagingSource(
    private val ref: DatabaseReference = Firebase.database(URL).reference
) : PagingSource<DataSnapshot, AudioModel>(){

    override fun getRefreshKey(state: PagingState<DataSnapshot, AudioModel>): DataSnapshot? = null

    override suspend fun load(params: LoadParams<DataSnapshot>): LoadResult<DataSnapshot, AudioModel> {
        return try{
            val query = ref.child("audio").orderByKey().limitToFirst(10)
            val currentPage = params.key ?: query.get().await()
            val lastVisibleProductKey = currentPage.children.last().key
            val nextPage = query.startAfter(lastVisibleProductKey).get().await()
            val response = currentPage.children.map { snapshot ->
                snapshot.toAudio()
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
    private fun DataSnapshot.toAudio() = AudioModel(
        key = this.key!!,
        title = this.child("title").value.toString(),
        artist = this.child("artist").value.toString(),
        audioUrl = this.child("audio_url").value.toString(),
        imageUrl = this.child("image_url").value.toString()
    )
}