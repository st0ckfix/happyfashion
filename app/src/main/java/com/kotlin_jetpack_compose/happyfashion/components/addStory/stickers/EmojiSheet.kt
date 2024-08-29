package com.kotlin_jetpack_compose.happyfashion.components.addStory.stickers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kotlin_jetpack_compose.happyfashion.components.addStory.FullDisplayType
import com.kotlin_jetpack_compose.happyfashion.components.addStory.FullDisplayUnit
import com.kotlin_jetpack_compose.happyfashion.components.addStory.LocalFullDisplay
import com.kotlin_jetpack_compose.happyfashion.items.BottomSheetSearchItem
import com.kotlin_jetpack_compose.happyfashion.items.GifImage
import com.kotlin_jetpack_compose.happyfashion.viewmodels.EmojiViewModel

@Composable
fun EmojiSheet(
    onDismiss: () -> Unit
) {
    val fullState = LocalFullDisplay.current
    val emojiViewModel : EmojiViewModel = viewModel()
    val state = emojiViewModel.emojiFlow.collectAsLazyPagingItems()

    BottomSheetSearchItem(
        onDismiss = onDismiss,
        onSearch = {},
        content = {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                columns = GridCells.Fixed(3)
            ) {
                if (state.loadState.refresh == LoadState.Loading) {
                    item {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }
                items(state.itemCount) {
                    val emoji = state[it]
                    if (emoji != null) {
                        GifImage(gif = emoji) { giphy ->
                            val item = FullDisplayUnit()
                            item.type = FullDisplayType.EMOJI
                            item.content = giphy
                            fullState.add(item)
                            onDismiss()
                        }
                    }
                }

                if (state.loadState.append == LoadState.Loading) {
                    item {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    )
}