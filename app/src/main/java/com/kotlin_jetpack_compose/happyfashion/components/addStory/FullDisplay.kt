package com.kotlin_jetpack_compose.happyfashion.components.addStory

import android.media.session.PlaybackState
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.kotlin_jetpack_compose.happyfashion.R
import com.kotlin_jetpack_compose.happyfashion.components.LocalSubtitleDisplay
import com.kotlin_jetpack_compose.happyfashion.components.SubtitleDisplay
import com.kotlin_jetpack_compose.happyfashion.components.addStory.display.ImageDisplay
import com.kotlin_jetpack_compose.happyfashion.items.GifImage
import com.kotlin_jetpack_compose.happyfashion.items.TransformGesturesItem
import com.kotlin_jetpack_compose.happyfashion.items.TransformType
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


        listDisplay.forEachIndexed { index, unit ->
                if (unit.transform.value.offset == Offset.Zero) {
                    Box(modifier = modifier
                        .align(Alignment.Center)
                        .onGloballyPositioned {
                            unit.transform.value =
                                unit.transform.value.copy(offset = it.positionInParent())
                        }) {
                        DisplayType(
                            modifier = modifier,
                            type = unit.type,
                            content = unit.content!!
                        )
                    }
                } else {
                    TransformGesturesItem(
                        modifier = modifier,
                        type = if (unit.type == FullDisplayType.DEFAULT) TransformType.EDIT_HOLDER else if (unit.type == FullDisplayType.LABEL) TransformType.LABEL else TransformType.OTHERS,
                        inEditing = inEditing,
                        transformGestureModel = unit.transform.value,
                        onTransform = { scale, rotation, offset ->
                            unit.transform.value = TransformGestureModel(
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
                                onTap(unit)
                            }
                        },
                        onLongPress = {
                            inEditing = true
                            editIndex = index
                            listDisplay.add(FullDisplayUnit())
                            listDisplay.add(unit)
                            listDisplay.removeAt(index)
                            return@TransformGesturesItem Unit
                        }
                    ) {
                        DisplayType(
                            modifier = modifier,
                            type = unit.type,
                            content = unit.content!!
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
            ImageDisplay(modifier = modifier, image = content as String)
        }
        FullDisplayType.VIDEO -> {
            VideoItem(uri = content as String)
        }
        FullDisplayType.GIF -> {
            GifImage(gif = content as GIPHY)
        }
        FullDisplayType.LABEL -> {
            val fontDecorationModel = content as FontDecorationModel
            TextItem(
                label = fontDecorationModel.fontContent,
                fontSize = fontDecorationModel.fontSize,
                fontFamily = fontDecorationModel.fontFamily,
                fontColor = fontDecorationModel.fontColor,
                backgroundColor = fontDecorationModel.fontBackground
            )
        }
        FullDisplayType.MUSIC -> {
            val value = content as Pair<*, *>
            val colorState = LocalSubtitleDisplay.current
            Box {
                SubtitleDisplay(audio = value.first as AudioModel, fontColor = colorState.value.fontColor, backgroundColor = colorState.value.backgroundColor)
            }
        }
        FullDisplayType.EMOJI -> {
            GifImage(gif = (content as GIPHY))
        }
        FullDisplayType.HASHTAG -> {
            Item(image = R.drawable.stk_hastag, label = (content as String))
        }
        FullDisplayType.MENTION -> {
            Item(image = R.drawable.stk_mention, label = (content as String))
        }
        FullDisplayType.PLACE -> {
            Item(image = R.drawable.stk_place, label = (content as String))
        }
        FullDisplayType.DEFAULT -> {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.5f)))
        }
    }
}

@Composable
fun TextItem(label: String, fontSize: Int, fontFamily: FontFamily , fontColor: Color, backgroundColor: Color){
    println("Create Label")
    Text(
        text = label,
        fontSize = fontSize.sp,
        fontFamily = fontFamily,
        color = fontColor,
        modifier = Modifier.background(backgroundColor))
}

@OptIn(UnstableApi::class)
@Composable
fun VideoItem(uri: String) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            playWhenReady = true
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            }
        }
    )
}

@Composable
fun Item(image: Int, label: String){
    println("Create $label" )
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