package com.kotlin_jetpack_compose.happyfashion.components.addStory.stickers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kotlin_jetpack_compose.happyfashion.components.addStory.FullDisplayType
import com.kotlin_jetpack_compose.happyfashion.components.addStory.FullDisplayUnit
import com.kotlin_jetpack_compose.happyfashion.components.addStory.LocalFullDisplay
import com.kotlin_jetpack_compose.happyfashion.items.BottomSheetSearchItem
import com.kotlin_jetpack_compose.happyfashion.viewmodels.FirebaseViewModel

@Composable
fun HashtagSheet(
    onDismiss: () -> Unit,
){
    val fullState = LocalFullDisplay.current
    val hashtagViewModel: FirebaseViewModel = viewModel()
    val state = hashtagViewModel.hashtagFlow.collectAsLazyPagingItems()
    var filter by remember { mutableStateOf("") }

    BottomSheetSearchItem(
        onDismiss = onDismiss,
        onSearch = { filter = it }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
        ) {
            if (state.loadState.refresh == LoadState.Loading) {
                item {
                    CircularProgressIndicator()
                }
            }
            val list = state.itemSnapshotList.items.filter { it.tag.contains(filter) }

            items(if (filter.isEmpty()) state.itemCount else list.size) {
                val hashtag = if (filter.isEmpty()) state[it] else list[it]
                if (filter.isNotEmpty() && list.isEmpty())
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "No Result Found")
                        IconButton(onClick = {
                            val item = FullDisplayUnit()
                            item.type = FullDisplayType.HASHTAG
                            item.content = filter
                            fullState.add(item)
                            onDismiss()
                        }) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null
                                )
                                Text(text = "Add #$filter")
                            }
                        }
                    }
                if (hashtag != null)
                    Box(
                        modifier = Modifier.clickable {
                            val item = FullDisplayUnit()
                            item.type = FullDisplayType.HASHTAG
                            item.content = hashtag.tag
                            fullState.add(item)
                            onDismiss()
                        },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(all = 10.dp)
                        ) {
                            Text(text = hashtag.tag, fontSize = 15.sp)
                            Text(
                                text = hashtag.times.toString() + " times",
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
            }
            if (state.loadState.append == LoadState.Loading) {
                item {
                    CircularProgressIndicator()
                }
            }
        }
    }
}