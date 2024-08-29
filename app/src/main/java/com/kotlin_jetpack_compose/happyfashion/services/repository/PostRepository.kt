package com.kotlin_jetpack_compose.happyfashion.services.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kotlin_jetpack_compose.happyfashion.models.PostModel
import com.kotlin_jetpack_compose.happyfashion.services.FirebaseService
import com.kotlin_jetpack_compose.happyfashion.services.paging_sources.PostPagingSource
import kotlinx.coroutines.flow.Flow

class PostRepository {
    suspend fun getData(): Flow<PagingData<PostModel>> = Pager(
        config = PagingConfig(pageSize = 5),
        pagingSourceFactory = { PostPagingSource() }
    ).flow
}