package com.kotlin_jetpack_compose.happyfashion.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kotlin_jetpack_compose.happyfashion.viewmodels.SliderViewModel
import com.kotlin_jetpack_compose.happyfashion.viewmodels.StoryViewModel

@Composable
@ExperimentalMaterial3Api
fun StorySlider(sliderViewmodel: SliderViewModel, listStoryViewmodel: StoryViewModel){
    Box(modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
                for(i in 0..<listStoryViewmodel.storyList.size){
                    Slider(
                        modifier = Modifier.weight(1f),
                        value =
                            if(i < listStoryViewmodel.currentIndex.intValue) 10f
                            else if(i == listStoryViewmodel.currentIndex.intValue) sliderViewmodel.sliderValue.floatValue
                            else 0f,
                        valueRange = 0f..10f,
                        colors =  SliderDefaults.colors().copy(
                            inactiveTrackColor = Color.LightGray,
                            activeTrackColor =
                                if(i > listStoryViewmodel.currentIndex.intValue) Color.Transparent
                                else Color.Blue,
                            activeTickColor = Color.White
                        ),
                        onValueChange = {},
                        thumb = {
                            SliderDefaults.Thumb(
                                interactionSource = remember { MutableInteractionSource() },
                                thumbSize = DpSize.Zero,
                                enabled = false
                            )
                        },
                    )
                }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun StorySliderPreview(){
    StorySlider(viewModel(), viewModel())
}