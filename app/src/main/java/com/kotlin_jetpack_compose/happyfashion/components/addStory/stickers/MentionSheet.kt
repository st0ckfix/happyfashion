package com.kotlin_jetpack_compose.happyfashion.components.addStory.stickers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kotlin_jetpack_compose.happyfashion.components.addStory.FullDisplayType
import com.kotlin_jetpack_compose.happyfashion.components.addStory.FullDisplayUnit
import com.kotlin_jetpack_compose.happyfashion.components.addStory.LocalFullDisplay
import com.kotlin_jetpack_compose.happyfashion.items.BottomSheetSearchItem
import com.kotlin_jetpack_compose.happyfashion.items.CircleStory
import com.kotlin_jetpack_compose.happyfashion.viewmodels.FirebaseViewModel

@Composable
fun MentionSheet(
    onDismiss: () -> Unit,
){
    val fullState = LocalFullDisplay.current
    val mention: FirebaseViewModel = viewModel()
    val state = mention.mentionFlow.collectAsLazyPagingItems()
    var filter by remember { mutableStateOf("") }

    BottomSheetSearchItem(
        onDismiss = onDismiss,
        onSearch = { filter = it }
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .background(color = Color.Transparent)
                .padding(all = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.Center,
            columns = GridCells.Fixed(4)
        ) {
            if (state.loadState.refresh == LoadState.Loading) {
                item {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
            val subList = state.itemSnapshotList.items.filter {
                it.id.contains(filter) || it.name.contains(filter)
            }
            items(if (filter.isEmpty()) state.itemCount else subList.size) {
                val user = if (filter.isEmpty()) state[it] else subList[it]
                if (user != null)
                    CircleStory(pair = Pair(user.image, user.id), firstItem = false) {
                        val item  = FullDisplayUnit()
                        item.type = FullDisplayType.MENTION
                        item.content = user.name
                        fullState.add(item)
                        onDismiss()
                    }
            }
            if (state.loadState.append == LoadState.Loading) {
                item {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}