package com.kotlin_jetpack_compose.happyfashion.services.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kotlin_jetpack_compose.happyfashion.models.HASHTAG
import com.kotlin_jetpack_compose.happyfashion.services.paging_sources.HashtagPagingSource
import kotlinx.coroutines.flow.Flow

class HashtagRepository {
    fun getData(): Flow<PagingData<HASHTAG>> = Pager(
        config = PagingConfig(pageSize = 25),
        pagingSourceFactory = { HashtagPagingSource() }
    ).flow
}