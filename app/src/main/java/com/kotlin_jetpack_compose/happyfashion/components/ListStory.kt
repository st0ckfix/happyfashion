package com.kotlin_jetpack_compose.happyfashion.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kotlin_jetpack_compose.happyfashion.R
import com.kotlin_jetpack_compose.happyfashion.items.CircleStory
import com.kotlin_jetpack_compose.happyfashion.viewmodels.StoryViewModel

@Composable
fun ListStory(){
    val viewmodel: StoryViewModel = viewModel()
    val listUser = viewmodel.userList
    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .height(110.dp)) {
        item {
            CircleStory(pair = Pair(R.drawable.harry,"Add Story"), true)
        }
        item {
            Box(modifier = Modifier.padding(top = 10.dp, bottom = 30.dp)) {
                VerticalDivider(thickness = 1.dp, color = Color.Black)
            }
        }
        items(listUser.size){
            CircleStory(Pair(listUser[it].image, listUser[it].name), false)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListStoryPreview(){
    ListStory()
}