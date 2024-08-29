package com.kotlin_jetpack_compose.happyfashion.services.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kotlin_jetpack_compose.happyfashion.models.UserModel
import com.kotlin_jetpack_compose.happyfashion.services.FirebaseService
import com.kotlin_jetpack_compose.happyfashion.services.paging_sources.ListFriendPagingSource
import kotlinx.coroutines.flow.Flow

class ListFriendRepository {
    suspend fun getData(): Flow<PagingData<UserModel>> = Pager(
        config = PagingConfig(pageSize = 5),
        pagingSourceFactory = { ListFriendPagingSource() }
    ).flow
}