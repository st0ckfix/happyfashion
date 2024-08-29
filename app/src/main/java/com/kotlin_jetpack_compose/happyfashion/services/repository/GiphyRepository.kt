package com.kotlin_jetpack_compose.happyfashion.services.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kotlin_jetpack_compose.happyfashion.models.GIPHY
import com.kotlin_jetpack_compose.happyfashion.services.paging_sources.EnumGIPHY
import com.kotlin_jetpack_compose.happyfashion.services.paging_sources.GiphyPagingSource
import kotlinx.coroutines.flow.Flow

class GiphyRepository {
    fun getGiphy(
        query: String? = null,
        mode: EnumGIPHY
    ): Flow<PagingData<GIPHY>> = Pager(
        config = PagingConfig(pageSize = 25),
        pagingSourceFactory = { GiphyPagingSource(query, mode) }
    ).flow
}