package com.kotlin_jetpack_compose.happyfashion.viewmodels

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.kotlin_jetpack_compose.happyfashion.R
import com.kotlin_jetpack_compose.happyfashion.models.StoryModel
import com.kotlin_jetpack_compose.happyfashion.models.UserModel

class StoryViewModel : ViewModel() {

    private val _userList = mutableStateListOf<UserModel>()
    val userList: MutableList<UserModel> = _userList

    private val _storyList = mutableStateListOf<StoryModel>()
    val storyList: MutableList<StoryModel> = _storyList

    private val _currentIndex = mutableIntStateOf(0)
    var currentIndex: MutableIntState = _currentIndex

    private val _currentUserIndex = mutableIntStateOf(0)
    var currentUserIndex: MutableIntState = _currentUserIndex

    fun fetchListUser(){
        _userList.addAll(
            listOf(
                UserModel("olivia_rodrigo", "Olivia Rodrigo", R.drawable.olivia),
                UserModel("scarlett_johansson", "Scarlett Johansson", R.drawable.johansson),
                UserModel("tzuyu", "Tzuyu", R.drawable.tzuyu),
                UserModel("taylor_swift", "Taylor Swift", R.drawable.taylor),
            ),
        )
    }

    fun fetchListStory(id: String){
        mapOf(
            "olivia_rodrigo" to listOf(
                StoryModel("olivia1", imageUrl =  R.drawable.olivia1, timeCreated =  1722433558),
                StoryModel("olivia2", imageUrl =  R.drawable.olivia2, timeCreated =  1722426358),
                StoryModel("olivia3", imageUrl =  R.drawable.olivia3, timeCreated =  1722419158),
                StoryModel("olivia4", imageUrl =  R.drawable.olivia4, timeCreated =  1722408358)
            ),
            "scarlett_johansson" to listOf(
                StoryModel("scarlett", imageUrl =  R.drawable.scarlett, timeCreated =  1722433558)
            ),
            "tzuyu" to listOf(
                StoryModel("tzuyu1", imageUrl =  R.drawable.tzuyu1, timeCreated =  1722433558),
                StoryModel("tzuyu2", imageUrl =  R.drawable.tzuyu2, timeCreated =  1722426358),
                StoryModel("tzuyu3", imageUrl =  R.drawable.tzuyu3, timeCreated =  1722419158),
                StoryModel("tzuyu4", imageUrl =  R.drawable.tzuyu4, timeCreated =  1722408358),
                StoryModel("tzuyu5", imageUrl =  R.drawable.tzuyu5, timeCreated =  1722401158),
                StoryModel("tzuyu6", imageUrl =  R.drawable.tzuyu6, timeCreated =  1722390358),
                StoryModel("tzuyu7", imageUrl =  R.drawable.tzuyu7, timeCreated =  1722375958),
                StoryModel("tzuyu8", imageUrl =  R.drawable.tzuyu8, timeCreated =  1722343558)
            ),
            "taylor_swift" to listOf(
                StoryModel("taylor1", imageUrl =  R.drawable.taylor1, timeCreated =  1722419158),
                StoryModel("taylor2", imageUrl =  R.drawable.taylor2, timeCreated =  1722343558)
            )
        )[id]?.let {
            _storyList.clear()
            _storyList.addAll(
                it
            )
        }
    }

    fun resetCurrentIndex(){
        _currentIndex.intValue = 0
    }

    fun updateCurrentIndex(value: Int) {
        _currentIndex.intValue += value
    }

    fun updateCurrentUserIndex(value: Int) {
        _currentUserIndex.intValue += value
    }

    fun getCurrentItem(): StoryModel{
        return _storyList[_currentIndex.intValue]
    }

    fun getCurrentUser(): UserModel{
        return _userList[_currentUserIndex.intValue]
    }
}