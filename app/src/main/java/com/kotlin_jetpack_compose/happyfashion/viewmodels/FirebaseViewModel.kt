package com.kotlin_jetpack_compose.happyfashion.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.kotlin_jetpack_compose.happyfashion.models.AudioModel
import com.kotlin_jetpack_compose.happyfashion.models.HASHTAG
import com.kotlin_jetpack_compose.happyfashion.models.PostModel
import com.kotlin_jetpack_compose.happyfashion.models.UserModel
import com.kotlin_jetpack_compose.happyfashion.services.repository.AudioRepository
import com.kotlin_jetpack_compose.happyfashion.services.repository.HashtagRepository
import com.kotlin_jetpack_compose.happyfashion.services.repository.ListFriendRepository
import com.kotlin_jetpack_compose.happyfashion.services.repository.PostRepository
import kotlinx.coroutines.launch

class FirebaseViewModel(
    hashtagRepository: HashtagRepository = HashtagRepository(),
    listFriendRepository: ListFriendRepository = ListFriendRepository(),
    audioRepository: AudioRepository = AudioRepository(),
    postRepository: PostRepository = PostRepository()
): ViewModel() {

    var hashtagFlow = MutableLiveData<PagingData<HASHTAG>>().asFlow()
    var mentionFlow = MutableLiveData<PagingData<UserModel>>().asFlow()
    var audioFlow = MutableLiveData<PagingData<AudioModel>>().asFlow()
    var postFlow = MutableLiveData<PagingData<PostModel>>().asFlow()

    init {
        viewModelScope.launch {
            hashtagFlow = hashtagRepository.getData().cachedIn(viewModelScope)
            mentionFlow = listFriendRepository.getData().cachedIn(viewModelScope)
            audioFlow = audioRepository.getData().cachedIn(viewModelScope)
            postFlow = postRepository.getData().cachedIn(viewModelScope)
        }
    }
}
