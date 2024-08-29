package com.kotlin_jetpack_compose.happyfashion.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.exoplayer.ExoPlayer
import coil.compose.rememberAsyncImagePainter
import com.kotlin_jetpack_compose.happyfashion.R
import com.kotlin_jetpack_compose.happyfashion.items.ButtonItem
import com.kotlin_jetpack_compose.happyfashion.items.ColorPanel
import com.kotlin_jetpack_compose.happyfashion.models.AudioModel
import com.kotlin_jetpack_compose.happyfashion.models.SubtitleDisplayModel
import com.kotlin_jetpack_compose.happyfashion.services.AudioService
import com.kotlin_jetpack_compose.happyfashion.services.EXOPLAYER_STATE
import com.kotlin_jetpack_compose.happyfashion.services.SubtitleService
import kotlinx.coroutines.delay

enum class SUBTITLE_MODE(
    val drawable: Int
){
    NO(R.drawable.no), FLOW(R.drawable.word), VERTICAL(R.drawable.vertical), CAM(R.drawable.cam);

    fun icon() : Int { return drawable }
}

internal val LocalSubtitleDisplay = compositionLocalOf { mutableStateOf(SubtitleDisplayModel.Default) }

@Composable
fun SubtitleEdit(
    modifier: Modifier,
    audio: AudioModel,
    onSwitch: () -> Unit,
    onDone: (SUBTITLE_MODE) -> Unit,
    onCancel: () -> Unit
) {
    val audioService: AudioService = viewModel()
    var fontColor by remember { mutableStateOf(Color.White) }
    var backgroundColor by remember { mutableStateOf(Color.Transparent) }

    Box(
        modifier
            .background(color = Color.Black.copy(alpha = .5f))
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Box(modifier = Modifier
            .systemBarsPadding()
            .fillMaxWidth()
            .align(Alignment.TopCenter)){
            MainControl(
                onSwitch = onSwitch,
                onState = {
                    if(audioService.exoPlayerState.value == EXOPLAYER_STATE.PLAYING) audioService.pause() else audioService.play()
                    audioService.exoPlayerState.value == EXOPLAYER_STATE.PLAYING
                },
                onDone = { onDone(audioService.subtitleMode.value) },
                onCancel = {
                    audioService.stop()
                    audioService.release()
                    onCancel()
                }
            )
        }

        SubtitleDisplay(audio = audio, fontColor = fontColor, backgroundColor = backgroundColor)

        Box(
            Modifier
                .systemBarsPadding()
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ) {
            SubtitleControl(
                onMode = {
                    audioService.setSubtitleState(it)
                },
                onChange = { bool, color ->
                    if(bool) fontColor = color else backgroundColor = color
                }
            )
        }
    }
}

@Composable
fun BoxScope.SubtitleDisplay(audio: AudioModel, fontColor: Color, backgroundColor: Color){
    val context = LocalContext.current
    val audioService: AudioService = viewModel()
    LaunchedEffect(audioService.exoPlayerState.value) {
        if (audioService.exoPlayerState.value == EXOPLAYER_STATE.RELEASED){
            val exoPlayer = ExoPlayer.Builder(context).build()
            audioService.setExoPlayer(exoPlayer)
            audioService.setMediaItem(audio.audioUrl)
            audioService.setSubtitlesFlow(SubtitleService().getSubtitleFlowTest())
            audioService.setSubtitlesVertical(SubtitleService().getSubtitleVerticalTest())
        }
    }

    val subtitlesFlow = remember { mutableStateListOf<String>() }
    var subtitlesVertical by remember { mutableStateOf(SubtitleService.Subtitle.Default) }

    Box(
        Modifier.align(Alignment.Center)
    ) {
        when (audioService.subtitleMode.value) {
            SUBTITLE_MODE.NO -> Text(text = "Music Only", fontSize = 22.sp, color = Color.White, textAlign = TextAlign.Center)
            SUBTITLE_MODE.FLOW -> SubtitleFlowDisplay(listControl = subtitlesFlow, fontColor = fontColor, backgroundColor = backgroundColor)
            SUBTITLE_MODE.VERTICAL -> SubtitleVerticalDisplay(subtitle = subtitlesVertical, list = SubtitleService().getSubtitleVerticalTest(), fontColor = fontColor, backgroundColor = backgroundColor)
            SUBTITLE_MODE.CAM -> Box(modifier = Modifier.clip(shape = RoundedCornerShape(15))){
                Row(
                    Modifier
                        .padding(5.dp)
                        .background(color = Color.White)
                        .clip(shape = RoundedCornerShape(15)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = audio.imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(shape = RoundedCornerShape(15)))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column{
                        Text(text = audio.title, fontSize = 14.sp)
                        Text(text = audio.artist, fontSize = 12.sp)
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = audioService.exoPlayerState.value, key2 = audioService.subtitleMode.value){
        if (audioService.exoPlayerState.value == EXOPLAYER_STATE.PLAYING) {
            when (audioService.subtitleMode.value) {
                SUBTITLE_MODE.FLOW -> {
                    while (audioService.exoPlayerState.value == EXOPLAYER_STATE.PLAYING) {
                        audioService.getSubtitle()?.let {
                            if (subtitlesFlow.isNotEmpty() && subtitlesFlow.last().contains("*")) subtitlesFlow.clear()
                            subtitlesFlow.add(it.content)
                        }
                        delay(100)
                    }
                }

                SUBTITLE_MODE.VERTICAL -> {
                    while (audioService.exoPlayerState.value == EXOPLAYER_STATE.PLAYING) {
                        audioService.getSubtitle()?.let {
                            subtitlesVertical =  it
                        }
                        delay(250)
                    }
                }
                else -> {}
            }
        }
    }
}

@Composable
fun SubtitleVerticalDisplay(subtitle: SubtitleService.Subtitle, list: List<SubtitleService.Subtitle>, fontColor: Color, backgroundColor: Color){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    )  {
        list.forEach { item ->
            key(item.index) {
                VerticalItem(transform = subtitle.content == item.content, display = item.content, fontColor = fontColor, backgroundColor = backgroundColor)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SubtitleFlowDisplay(listControl: List<String>, fontColor: Color, backgroundColor: Color) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        FlowRow(
            modifier = Modifier
                .width((screenWidth * .5).dp)
                .padding(start = 10.dp)
        ) {
            for(i in listControl.indices){
                key(i) {
                    FlowItem(display = listControl[i].replace("*",""), fontColor = fontColor, backgroundColor = backgroundColor)
                }

            }
        }
    }
}

@Composable
fun FlowItem(display: String, fontColor: Color, backgroundColor: Color){
    var visible by remember { mutableStateOf(false) }
    val fontSize = remember { (25..35).random().sp }
    val fontStyle = remember { FontStyle.values().random() }
    val fontWeight = remember { arrayOf(FontWeight.Black, FontWeight.Bold, FontWeight.Normal, FontWeight.SemiBold, FontWeight.ExtraBold).random() }
    AnimatedVisibility(
        modifier = Modifier.padding(all = 2.dp),
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { it }
        ) + fadeIn(),
    ) {
        Text(
            text = "${display.replace("*","")} ",
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            color = fontColor,
            modifier = Modifier.background(color = backgroundColor)
        )
    }
    LaunchedEffect(Unit) {
        visible = true
    }
}

@Composable
fun VerticalItem(transform: Boolean, display: String, fontColor: Color, backgroundColor: Color){
    var visible by remember { mutableStateOf(false) }
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { it }
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { -it }
        ) + fadeOut()
    ) {
        Text(
            text = display,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(all = 10.dp)
                .background(color = backgroundColor),
            color = fontColor,
        )
    }
    LaunchedEffect(transform) {
        visible = transform
    }
}

@Composable
fun SubtitleControl(
    onChange: (Boolean, Color) -> Unit,
    onMode: (SUBTITLE_MODE) -> Unit,
){
    var textColorEdit by remember { mutableStateOf(true) }
    val colorState = LocalSubtitleDisplay.current
    var fontColor by remember { mutableStateOf(colorState.value.fontColor) }
    var backgroundColor by remember { mutableStateOf(colorState.value.backgroundColor) }

    Column {
        ColorPanel(defaultColor = if (textColorEdit) fontColor else backgroundColor, isBackgroundColor = !textColorEdit) {
            if(textColorEdit) {
                colorState.value.fontColor = it
                fontColor = it
                onChange(true, it)
            } else {
                colorState.value.backgroundColor = it
                backgroundColor = it
                onChange(false, it)
            }
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonItem(Modifier) {
                IconButton(onClick = {
                    textColorEdit = !textColorEdit
                }) {
                    Image(
                        painter = painterResource(id = if(textColorEdit) R.drawable.font else R.drawable.background_2),
                        colorFilter = ColorFilter.tint(Color.White),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            VerticalDivider(thickness = 2.dp, color = Color.White, modifier = Modifier.height(24.dp))
            for(mode in arrayOf(SUBTITLE_MODE.NO, SUBTITLE_MODE.FLOW, SUBTITLE_MODE.VERTICAL, SUBTITLE_MODE.CAM))
                ButtonItem(Modifier) {
                    IconButton(onClick = {
                        onMode(mode)
                    }) {
                        Image(
                            painter = painterResource(id = mode.drawable),
                            colorFilter = ColorFilter.tint(Color.White),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
        }
    }
}

@Composable
fun MainControl(
    onSwitch: () -> Unit,
    onState: () -> Boolean,
    onCancel: () -> Unit,
    onDone: () -> Unit
){
    var isPlaying by remember { mutableStateOf(true) }
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.weight(1f), contentAlignment = Alignment.Center){
            ButtonItem(Modifier) {
                IconButton(onClick = { onSwitch() }) {
                    Image(
                        painter = painterResource(id = R.drawable.change),
                        colorFilter = ColorFilter.tint(Color.White),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        Box(Modifier.weight(1f), contentAlignment = Alignment.Center){
            ButtonItem(Modifier) {
                IconButton(onClick = { isPlaying = onState() }) {
                    Image(
                        painter = painterResource(id = if(isPlaying) R.drawable.pause else R.drawable.play),
                        colorFilter = ColorFilter.tint(Color.White),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        Box(Modifier.weight(1f), contentAlignment = Alignment.Center){
            ButtonItem(Modifier) {
                IconButton(onClick = { onCancel() }) {
                    Image(
                        painter = painterResource(R.drawable.cancel),
                        colorFilter = ColorFilter.tint(Color.White),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        IconButton(modifier = Modifier.weight(1f), onClick = {
            onDone()
        }) {
            Text(text = "Done", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun Preview(){
    Box(Modifier.background(color = Color.Black)) {
        SubtitleEdit(modifier = Modifier, audio = AudioModel.Default, onSwitch = { /*TODO*/ }, onDone = {}, onCancel = {})
    }
}