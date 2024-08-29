package com.kotlin_jetpack_compose.happyfashion.components.addStory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.exoplayer.ExoPlayer
import coil.compose.rememberAsyncImagePainter
import com.kotlin_jetpack_compose.happyfashion.R
import com.kotlin_jetpack_compose.happyfashion.components.LocalSubtitleDisplay
import com.kotlin_jetpack_compose.happyfashion.components.SUBTITLE_MODE
import com.kotlin_jetpack_compose.happyfashion.components.SubtitleDisplay
import com.kotlin_jetpack_compose.happyfashion.components.SubtitleFlowDisplay
import com.kotlin_jetpack_compose.happyfashion.components.SubtitleVerticalDisplay
import com.kotlin_jetpack_compose.happyfashion.components.addStory.display.ImageDisplay
import com.kotlin_jetpack_compose.happyfashion.items.GifImage
import com.kotlin_jetpack_compose.happyfashion.items.TransformGesturesItem
import com.kotlin_jetpack_compose.happyfashion.items.TransformType
import com.kotlin_jetpack_compose.happyfashion.lyric
import com.kotlin_jetpack_compose.happyfashion.models.AudioModel
import com.kotlin_jetpack_compose.happyfashion.models.FontDecorationModel
import com.kotlin_jetpack_compose.happyfashion.models.GIPHY
import com.kotlin_jetpack_compose.happyfashion.models.TransformGestureModel

internal val LocalFullDisplay = compositionLocalOf { mutableStateListOf<FullDisplayUnit>() }

enum class FullDisplayType{
    DEFAULT, IMAGE, VIDEO, GIF, LABEL, MUSIC, EMOJI, HASHTAG, MENTION, PLACE
}

class FullDisplayUnit {
    var type: FullDisplayType by mutableStateOf(FullDisplayType.DEFAULT)
    var content: Any? by mutableStateOf(null)
    var transform: MutableState<TransformGestureModel> = mutableStateOf(TransformGestureModel.Default)
}

@Composable
fun FullDisplay(
    modifier: Modifier,
    onTap: (FullDisplayUnit) -> Unit ? = {},
){
    val listDisplay = LocalFullDisplay.current
    var inEditing by remember { mutableStateOf(false) }
    var editIndex by remember { mutableIntStateOf(-1) }

    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenWidthDp

    fun clearEdit(){
        repeat(2) { listDisplay.removeLast() }
        inEditing = false
        editIndex = -1
    }

    Box(modifier = modifier
        .background(color = Color.LightGray)
        .height(screenHeight.dp)
        .width(screenWidth.dp)){


        for (i in listDisplay.indices) {
            println("CREATE $i")
                if (listDisplay[i].transform.value.offset == Offset.Zero) {
                    Box(modifier = modifier
                        .align(Alignment.Center)
                        .onGloballyPositioned {
                            listDisplay[i].transform.value =
                                listDisplay[i].transform.value.copy(offset = it.positionInParent())
                        }) {
                        DisplayType(
                            modifier,
                            type = listDisplay[i].type,
                            content = listDisplay[i].content!!
                        )
                    }
                } else {
                    TransformGesturesItem(
                        modifier = modifier,
                        type = if (listDisplay[i].type == FullDisplayType.DEFAULT) TransformType.EDIT_HOLDER else if (listDisplay[i].type == FullDisplayType.LABEL) TransformType.LABEL else TransformType.OTHERS,
                        inEditing = inEditing,
                        transformGestureModel = listDisplay[i].transform.value,
                        onTransform = { scale, rotation, offset ->
                            listDisplay[i].transform.value = TransformGestureModel(
                                scale = scale,
                                rotation = rotation,
                                offset = offset
                            )

                        },
                        onTap = {
                            if (it == TransformType.EDIT_HOLDER) {
                                listDisplay.add(editIndex, listDisplay.last())
                                clearEdit()
                                return@TransformGesturesItem Unit
                            } else {
                                onTap(listDisplay[i])
                            }
                        },
                        onLongPress = {
                            inEditing = true
                            editIndex = i
                            listDisplay.add(FullDisplayUnit())
                            listDisplay.add(listDisplay[i])
                            listDisplay.removeAt(i)
                            return@TransformGesturesItem Unit

                        }
                    ) {
                        DisplayType(
                            modifier,
                            type = listDisplay[i].type,
                            content = listDisplay[i].content!!
                        )
                    }
                }
        }
        if(inEditing)
            EditComponent(
                onDelete = {
                    clearEdit()
                },
                onDuplicate = {
                    val item = listDisplay.last()
                    item.transform = mutableStateOf(listDisplay.last().transform.value.copy(offset = listDisplay.last().transform.value.offset + Offset(100f, 100f)))
                    listDisplay.add(editIndex, listDisplay.last())
                    listDisplay.add(editIndex + 1, item)
                    clearEdit()
                }
            )
    }
}

@Composable
fun DisplayType(modifier: Modifier, type: FullDisplayType, content: Any){
    when(type){
        FullDisplayType.IMAGE -> {
            println(FullDisplayType.IMAGE.name)
            ImageDisplay(modifier = modifier, image = (content as Pair<*,*>).first.toString(), state = State.DEFAULT, onState = {}, filter = content.second as? ColorMatrix?)
        }
        FullDisplayType.VIDEO -> {
            println(FullDisplayType.VIDEO.name)
        }
        FullDisplayType.GIF -> {
            println(FullDisplayType.GIF.name)
            GifImage(gif = content as GIPHY)
        }
        FullDisplayType.LABEL -> {
            println(FullDisplayType.LABEL.name)
            val fontDecorationModel = content as FontDecorationModel
            Text(
                text = fontDecorationModel.fontContent,
                fontSize = fontDecorationModel.fontSize.sp,
                fontFamily = fontDecorationModel.fontFamily,
                color = fontDecorationModel.fontColor,
                modifier = Modifier.background(fontDecorationModel.fontBackground))
        }
        FullDisplayType.MUSIC -> {
            println(FullDisplayType.MUSIC.name)
            val value = content as Pair<*, *>
            val colorState = LocalSubtitleDisplay.current
            Box {
                SubtitleDisplay(audio = value.first as AudioModel, fontColor = colorState.value.fontColor, backgroundColor = colorState.value.backgroundColor)
            }
        }
        FullDisplayType.EMOJI -> {
            println(FullDisplayType.EMOJI.name)
            GifImage(gif = (content as GIPHY))
        }
        FullDisplayType.HASHTAG -> {
            println(FullDisplayType.HASHTAG.name)
            Item(image = R.drawable.stk_hastag, label = (content as String))
        }
        FullDisplayType.MENTION -> {
            println(FullDisplayType.MENTION.name)
            Item(image = R.drawable.stk_mention, label = (content as String))
        }
        FullDisplayType.PLACE -> {
            println(FullDisplayType.PLACE.name)
            Item(image = R.drawable.stk_place, label = (content as String))
        }
        FullDisplayType.DEFAULT -> {
            println(FullDisplayType.DEFAULT.name)
            Box(modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.5f)))
        }
    }
}

@Composable
fun Item(image: Int, label: String){
    Row(
        modifier = Modifier.clip(shape = RoundedCornerShape(15)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = image), contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = label, fontSize = 20.sp)
    }
}

@Composable
fun BoxScope.EditComponent(onDelete: () -> Unit, onDuplicate: () -> Unit){
    Box(
        Modifier
            .fillMaxWidth()
            .systemBarsPadding()
            .background(color = Color.White)
            .align(Alignment.BottomCenter),
        contentAlignment = Alignment.Center
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()) {
            IconButton(modifier = Modifier.weight(1f), onClick = {
                onDelete()
            }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(
                            Color.Red
                        ),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "Delete", fontSize = 20.sp, color = Color.Red)
                }
            }
            IconButton(modifier = Modifier.weight(1f), onClick = {
                onDuplicate()
            }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.duplicate),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(
                            Color.Black
                        ),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "Duplicate", fontSize = 20.sp, color = Color.Black)
                }
            }
        }
    }
}