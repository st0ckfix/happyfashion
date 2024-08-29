package com.kotlin_jetpack_compose.happyfashion.items

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.kotlin_jetpack_compose.happyfashion.R
import com.kotlin_jetpack_compose.happyfashion.components.PostControlButton
import com.kotlin_jetpack_compose.happyfashion.components.PostDetail
import com.kotlin_jetpack_compose.happyfashion.viewmodels.FirebaseViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostScreen() {
    val context = LocalContext.current
    val postViewModel: FirebaseViewModel = viewModel()
    val state = postViewModel.postFlow.collectAsLazyPagingItems()

    LazyColumn(
        Modifier.systemBarsPadding()
    ) {
        if(state.loadState.refresh == LoadState.Loading){
            item {
                CircularProgressIndicator()
            }
        }
        items(state.itemCount) { index ->
            val post = state[index] !!
            val postUser = post.postUser
            Column {
                CircleUser(
                    image = postUser.avatar,
                    display = postUser.uid,
                    isVerify = true
                )
                val postContent = post.postContent
                
                if (postContent.images_url.isNotEmpty()) {
                    val pagerState: PagerState = rememberPagerState(
                        initialPage = 0,
                        pageCount = ({ postContent.images_url.size })
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(600.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        PagerItem(
                            list = postContent.images_url.map {
                                (@Composable {
                                    Image(
                                        painter = rememberAsyncImagePainter(model = it),
                                        contentDescription = null,
                                        contentScale = ContentScale.FillHeight,
                                        modifier = Modifier
                                            .height(600.dp)
                                            .fillMaxWidth()
                                            .align(Alignment.Center)
                                    )
                                })
                            },
                            pagerState = pagerState
                        )
                        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                            IndicatorItem(pagerState = pagerState)
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(10.dp)
                        ) {
                            NumberIndicatorItem(pagerState = pagerState)
                        }
                    }
                }
                else{
                    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
                    exoPlayer.setMediaItem(MediaItem.fromUri(postContent.video_url!!))
                    exoPlayer.prepare()
                    AndroidView(
                        factory = {
                            PlayerView(it).apply {
                                player = exoPlayer
                                useController = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(300.dp).onGloballyPositioned {
                            if(it.isAttached) exoPlayer.play()
                        }
                    )
                }
                val postDetail = post.postDetail
                PostDetail(likes = postDetail.likes.size, content = postDetail.content, comments = postDetail.comments.size)
                PostControlButton()
                HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
        if(state.loadState.append == LoadState.Loading){
            item {
                CircularProgressIndicator()
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PostItemPreview(){
    PostScreen()
}