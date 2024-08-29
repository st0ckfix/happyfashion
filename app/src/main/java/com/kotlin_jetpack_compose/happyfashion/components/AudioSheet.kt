package com.kotlin_jetpack_compose.happyfashion.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.kotlin_jetpack_compose.happyfashion.R
import com.kotlin_jetpack_compose.happyfashion.items.BottomSheetSearchItem
import com.kotlin_jetpack_compose.happyfashion.items.GifImage
import com.kotlin_jetpack_compose.happyfashion.models.AudioModel
import com.kotlin_jetpack_compose.happyfashion.viewmodels.FirebaseViewModel

@Composable
fun AudioSheet(
    onMusicSelect: (AudioModel) -> Unit,
    onDismiss: () -> Unit,
){
    val context = LocalContext.current
    val audio: FirebaseViewModel = viewModel()
    val state = audio.audioFlow.collectAsLazyPagingItems()
    var filter by remember { mutableStateOf("") }

    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    var mediaPlaying by remember { mutableStateOf("") }
    var isPlaying by remember { mutableStateOf(false) }

    BottomSheetSearchItem(
        onDismiss = onDismiss,
        onSearch = { filter = it },
        content = {
            LazyColumn (
                modifier = Modifier
                    .padding(all = 5.dp)
                    .background(color = Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                if(state.loadState.refresh == LoadState.Loading){
                    item {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }
                val subList = state.itemSnapshotList.items.filter { it.title.contains(filter) }
                items( if(filter.isEmpty()) state.itemCount else subList.size){
                    val mAudio = if(filter.isEmpty()) state[it] else subList[it]
                    if(mAudio != null)
                        Row(
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                                .clip(shape = RoundedCornerShape(15))
                                .clickable {
                                    exoPlayer.stop()
                                    exoPlayer.release()
                                    onMusicSelect(mAudio)
                                }
                                .background(Color.LightGray),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = mAudio.imageUrl),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(shape = RoundedCornerShape(15))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(text = mAudio.title, fontSize = 14.sp)
                                Text(text = mAudio.artist, fontSize = 12.sp)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = {
                                if (mediaPlaying == mAudio.key) {
                                    if (isPlaying) exoPlayer.pause() else exoPlayer.play()
                                    isPlaying = !isPlaying
                                } else {
                                    val mediaItem = MediaItem.fromUri(mAudio.audioUrl)
                                    isPlaying = true
                                    mediaPlaying = mAudio.key
                                    exoPlayer.setMediaItem(mediaItem)
                                    exoPlayer.prepare()
                                    exoPlayer.play()
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = if (mediaPlaying == mAudio.key && isPlaying) R.drawable.pause else R.drawable.play),
                                    contentDescription = null
                                )
                            }
                        }
                }
                if(state.loadState.append == LoadState.Loading){
                    item {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    )
}