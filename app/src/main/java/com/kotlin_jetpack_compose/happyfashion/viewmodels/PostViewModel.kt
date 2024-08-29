package com.kotlin_jetpack_compose.happyfashion.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin_jetpack_compose.happyfashion.R
import com.kotlin_jetpack_compose.happyfashion.models.PostModel
import com.kotlin_jetpack_compose.happyfashion.models.UserModel
import kotlinx.coroutines.launch

class PostViewModel() : ViewModel() {
    val _postList = mutableStateListOf<PostModel>()
    val postList: List<PostModel> = _postList

    init {
        fetchData()
    }

    private fun fetchData(){
        viewModelScope.launch {

        }
    }
}