package com.kotlin_jetpack_compose.happyfashion.services.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kotlin_jetpack_compose.happyfashion.models.AudioModel
import com.kotlin_jetpack_compose.happyfashion.services.paging_sources.AudioPagingSource
import kotlinx.coroutines.flow.Flow

class AudioRepository {
    fun getData(): Flow<PagingData<AudioModel>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { AudioPagingSource() }
    ).flow
}