package com.kotlin_jetpack_compose.happyfashion.components.addStory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kotlin_jetpack_compose.happyfashion.R
import com.kotlin_jetpack_compose.happyfashion.components.AudioSheet
import com.kotlin_jetpack_compose.happyfashion.components.Gallery
import com.kotlin_jetpack_compose.happyfashion.components.SubtitleEdit
import com.kotlin_jetpack_compose.happyfashion.components.addStory.stickers.EmojiSheet
import com.kotlin_jetpack_compose.happyfashion.components.addStory.stickers.GifSheet
import com.kotlin_jetpack_compose.happyfashion.components.addStory.stickers.HashtagSheet
import com.kotlin_jetpack_compose.happyfashion.components.addStory.stickers.MentionSheet
import com.kotlin_jetpack_compose.happyfashion.models.AudioModel
import com.kotlin_jetpack_compose.happyfashion.models.FontDecorationModel

enum class State(val drawable: Int){
    MUSIC_EDIT(0),
    BOTTOM_SHEET(0),
    DEFAULT(0),
    TITLE(R.drawable.text),
    STICKERS(R.drawable.stickers),
    MUSIC(R.drawable.note),
    EFFECT(R.drawable.effect),
    DRAW(R.drawable.draw);

    fun icon(): Int = drawable
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContentScreen(){
    val fullState = LocalFullDisplay.current
    var currentState by remember { mutableStateOf(State.DEFAULT) }
    var labelSelect by remember { mutableStateOf(FontDecorationModel.Default) }
    var stickerSheet by remember { mutableStateOf<Stickers?>(null) }
    var haveMedia by remember { mutableStateOf(fullState.any { it.type == FullDisplayType.IMAGE || it.type == FullDisplayType.VIDEO }) }
    var audio by remember { mutableStateOf<AudioModel?>(null) }

    if(!haveMedia)
        Gallery {
            println(it)
            if(it != null){
                val item = FullDisplayUnit()
                item.type = FullDisplayType.IMAGE
                item.content = Pair(it, null)
                fullState.add(item)
                haveMedia = true
            }
            else{
                haveMedia = false
            }
        }
    else
        Box(
            Modifier.fillMaxSize()
        ) {
            FullDisplay(modifier = Modifier){
                when(it.type) {
                    FullDisplayType.LABEL -> {
                        labelSelect = it.content as FontDecorationModel
                        currentState = State.TITLE
                    }
                    FullDisplayType.MUSIC -> {
                        audio = it.content as AudioModel
                        currentState = State.MUSIC_EDIT
                    }
                    else -> {}
                }
            }
            when (currentState) {
            State.DEFAULT -> {
                MainControlAddStory {
                    currentState = it
                }
            }
            State.TITLE -> {
                LabelEdit(
                    labelSelect,
                ) { label ->
                    if (labelSelect == FontDecorationModel.Default) {
                        val item = FullDisplayUnit()
                        item.type = FullDisplayType.LABEL
                        item.content = label
                        fullState.add (item)
                    }
                    currentState = State.DEFAULT
                    labelSelect = FontDecorationModel.Default
                }
            }

            State.STICKERS -> {
                ModalBottomSheet(
                    onDismissRequest = {
                        currentState = State.DEFAULT
                    }) {
                    Stickers {
                        currentState = State.BOTTOM_SHEET
                        stickerSheet = it
                    }
                }
            }

            State.MUSIC -> {
                AudioSheet(
                    onDismiss = {
                        currentState = State.DEFAULT
                    },
                    onMusicSelect = {
                        audio = it
                        currentState = State.MUSIC_EDIT
                    }
                )
            }

            State.MUSIC_EDIT -> {
                SubtitleEdit(
                    modifier = Modifier,
                    audio = audio!!,
                    onSwitch = {
                        currentState = State.MUSIC
                    },
                    onDone = {
                        currentState = State.DEFAULT
                        val item = FullDisplayUnit()
                        item.type = FullDisplayType.MUSIC
                        item.content = Pair(audio!!, it)
                        fullState.add(item)
                    },
                    onCancel = {
                        currentState = State.DEFAULT
                    }
                )
            }

            State.EFFECT -> {
                FilterComponent {
                    fullState[0].content = Pair((fullState[0].content as Pair<*,*>).first, it)
                }
            }
            // Add Later
            State.DRAW -> {}

            State.BOTTOM_SHEET -> {
                when (stickerSheet) {
                    Stickers.GIF -> {
                        GifSheet {
                            currentState = State.DEFAULT
                        }
                    }

                    Stickers.EMOJI -> {
                        EmojiSheet {
                            currentState = State.DEFAULT
                        }
                    }

                    Stickers.HASHTAG -> {
                        HashtagSheet {
                            currentState = State.DEFAULT
                        }
                    }

                    Stickers.MENTION -> {
                        MentionSheet {
                            currentState = State.DEFAULT
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddContentPreview(){
    Box(Modifier.background(color = Color.Black.copy(alpha = .5f))) {
        ControlEdit(onPanel = {
            it
        }) {
        }
    }
}