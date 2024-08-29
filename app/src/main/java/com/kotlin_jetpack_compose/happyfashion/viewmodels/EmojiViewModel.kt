package com.kotlin_jetpack_compose.happyfashion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kotlin_jetpack_compose.happyfashion.services.paging_sources.EnumGIPHY
import com.kotlin_jetpack_compose.happyfashion.services.repository.GiphyRepository
class EmojiViewModel(
    giphyRepository: GiphyRepository = GiphyRepository()
) : ViewModel(){
    val emojiFlow = giphyRepository.getGiphy(mode = EnumGIPHY.EMOJI_TRENDING).cachedIn(viewModelScope)
}