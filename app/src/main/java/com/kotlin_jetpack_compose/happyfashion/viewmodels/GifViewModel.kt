package com.kotlin_jetpack_compose.happyfashion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kotlin_jetpack_compose.happyfashion.services.paging_sources.EnumGIPHY
import com.kotlin_jetpack_compose.happyfashion.services.repository.GiphyRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class GifViewModel : ViewModel() {

    private val searchQuery = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val gifFlow = searchQuery.flatMapLatest { query ->
        GiphyRepository()
            .getGiphy(
                query = query,
                mode = if(query.isNullOrEmpty()) EnumGIPHY.GIF_TRENDING else EnumGIPHY.GIF_SEARCH,
            )
            .cachedIn(viewModelScope)
    }

    fun searchGif(query: String?) {
        searchQuery.value = query
    }
}
