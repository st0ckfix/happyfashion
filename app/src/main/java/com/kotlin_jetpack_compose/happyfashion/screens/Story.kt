/*
package com.kotlin_jetpack_compose.happyfashion.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kotlin_jetpack_compose.happyfashion.R
import com.kotlin_jetpack_compose.happyfashion.components.StoryControlButton
import com.kotlin_jetpack_compose.happyfashion.components.StorySlider
import com.kotlin_jetpack_compose.happyfashion.items.CircleUser
import com.kotlin_jetpack_compose.happyfashion.viewmodels.SliderViewModel
import com.kotlin_jetpack_compose.happyfashion.viewmodels.StoryViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryScreen(){
    /// Get Screen Width
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    /// Import Slider Viewmodel & Story Viewmodel
    val viewmodel: SliderViewModel = viewModel()
    val listStoryViewmodel: StoryViewModel = viewModel()

    /// Fetch List User
    listStoryViewmodel.fetchListUser()

    /// Fetch List Story by Id
    listStoryViewmodel.fetchListStory(listStoryViewmodel.getCurrentUser().id)

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Gray)
        .pointerInput(Unit) {
            coroutineScope {
                detectTapGestures(
                    /// On Tapping Screen
                    onTap = {
                        /// On Tapping Right Side
                        if (it.x > .75 * screenWidthDp) {
                            /// Reach End of List Story
                            if(listStoryViewmodel.currentIndex.intValue == listStoryViewmodel.storyList.size - 1) {
                                /// Reach End of List User
                                if (listStoryViewmodel.currentUserIndex.intValue == listStoryViewmodel.userList.size - 1) {
                                    viewmodel.setPauseState(false)
                                    return@detectTapGestures
                                }
                                else {
                                    listStoryViewmodel.updateCurrentUserIndex(1)
                                    listStoryViewmodel.resetCurrentIndex()
                                }
                            }
                            else {
                                listStoryViewmodel.updateCurrentIndex(1)
                            }
                            viewmodel.reset()
                        }
                        /// On Tapping Left Side
                        else if(it.x < .25 * screenWidthDp) {
                            /// If List Story Index Is 0
                            if (listStoryViewmodel.currentIndex.intValue == 0){
                                /// If List User Index Is 0
                                if(listStoryViewmodel.currentUserIndex.intValue == 0) {
                                    viewmodel.setPauseState(false)
                                    return@detectTapGestures
                                }
                                else {
                                    listStoryViewmodel.updateCurrentUserIndex(-1)
                                    listStoryViewmodel.resetCurrentIndex()
                                }
                            }
                            else {
                                listStoryViewmodel.updateCurrentIndex(-1)
                            }
                            viewmodel.reset()
                        }
                    },
                    /// On Pressing Screen
                    onPress = {
                        if (it.x <= .75 * screenWidthDp && it.x >= .25 * screenWidthDp) {
                            /// Set State is Pause
                            viewmodel.setPauseState(true)
                            /// Wait for Release
                            tryAwaitRelease()
                            /// Set State is Not Pause
                            viewmodel.setPauseState(false)
                        }
                    },
                )
            }
        }) {

        Box(modifier = Modifier.weight(.1f)){
            Column{
                StorySlider(sliderViewmodel = viewmodel, listStoryViewmodel = listStoryViewmodel)
                Row {
                    val user = listStoryViewmodel.getCurrentUser()
                    CircleUser(pair = Pair(user.image, user.id), textColor = Color.White)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { */
/*TODO*//*
 }) {
                        Image(
                            painter = painterResource(id = if(listStoryViewmodel.getCurrentItem().audioUrl == null) R.drawable.audio_off_2 else R.drawable.audio_2),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier.size(25.dp))
                    }
                    IconButton(onClick = { */
/*TODO*//*
 }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(30.dp))
                    }
                }
            }

        }
        Box(modifier = Modifier.weight(.8f)){
            Column {
                Image(painter = painterResource(id = listStoryViewmodel.getCurrentItem().imageUrl as Int), contentDescription = null, modifier = Modifier.fillMaxSize())
                listStoryViewmodel.getCurrentItem().title?.let {
                    Text(text = it, color = Color.White)
                }
            }
        }
        Box(modifier = Modifier.weight(.1f), contentAlignment = Alignment.Center){
            StoryControlButton()
        }
        if(!viewmodel.getPauseState())
            LaunchedEffect(key1 = viewmodel.getPauseState()) {
                viewmodel.viewModelScope.launch {
                    while(!viewmodel.getPauseState()){
                        viewmodel.onUpdateSliderValue()
                    }
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun StoryScreenPreview(){
    StoryScreen()
}*/
