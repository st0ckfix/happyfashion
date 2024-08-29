package com.kotlin_jetpack_compose.happyfashion.components.addStory

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotlin_jetpack_compose.happyfashion.R
import com.kotlin_jetpack_compose.happyfashion.items.ButtonItem
import com.kotlin_jetpack_compose.happyfashion.items.ColorPanel
import com.kotlin_jetpack_compose.happyfashion.items.IndicatorItem
import com.kotlin_jetpack_compose.happyfashion.items.PagerItem
import com.kotlin_jetpack_compose.happyfashion.models.FontDecorationModel
import com.kotlin_jetpack_compose.happyfashion.ui.theme.listFont
import com.kotlin_jetpack_compose.happyfashion.ui.theme.robotoFont
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt


enum class EditPanel(val icon: Int){
    FONT(R.drawable.font), COLOR(R.drawable.color_picker), BACKGROUND(R.drawable.background_1);
    fun icon() : Int { return icon }
}

@Composable
fun LabelEdit(
    font: FontDecorationModel,
    onDone: (FontDecorationModel) -> Unit
){
    var panel by remember { mutableStateOf(EditPanel.FONT) }
    var fontSize by remember { mutableIntStateOf(font.fontSize) }
    var fontFamily by remember { mutableStateOf(font.fontFamily) }
    var fontColor by remember { mutableStateOf(font.fontColor) }
    var fontBackground by remember { mutableStateOf(font.fontBackground) }
    var fontContent by remember { mutableStateOf(font.fontContent) }

    Column (
        modifier = Modifier
            .imePadding()
            .background(color = Color.Black.copy(alpha = .5f))
            .systemBarsPadding()
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    fontSize = min(40, fontSize + ((dragAmount / 5f).roundToInt()))
                }
            },
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                ControlEdit(
                    onPanel = {
                        panel = it
                        it
                    },
                    onDone = {
                        onDone(font.copy(
                            fontSize = fontSize,
                            fontFamily = fontFamily,
                            fontColor = fontColor,
                            fontBackground = fontBackground,
                            fontContent = fontContent
                        ))
                    }
                )
            }

        }
        Box {
            TextEdit(
                font = font.copy(
                    fontSize = fontSize,
                    fontFamily = fontFamily,
                    fontColor = fontColor,
                    fontBackground = fontBackground,
                    fontContent = fontContent
                )
            ){
                fontContent = it
            }
        }
        Box {
            when (panel) {
                EditPanel.COLOR -> ColorPanel(fontColor, false) {
                    fontColor = it
                }
                EditPanel.FONT -> FontPanel(fontFamily) {
                    fontFamily = it
                }
                EditPanel.BACKGROUND -> ColorPanel(fontBackground, true) {
                    fontBackground = it
                }
            }
        }

    }
}


@Composable
fun ControlEdit(onPanel: (EditPanel) -> EditPanel,  onDone: () -> Unit){
    var panel by remember { mutableStateOf(EditPanel.FONT) }
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)) {

        for (editPanel in EditPanel.entries)
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                ButtonItem {
                    IconButton(onClick = {
                        panel = onPanel(editPanel)
                    }) {
                        Image(
                            painter = painterResource(id = editPanel.icon()),
                            colorFilter = if(panel == editPanel) ColorFilter.tint(Color.White) else ColorFilter.tint(Color.Gray),
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

@Composable
fun TextEdit(font: FontDecorationModel, onValue: (String) -> Unit){
    val focusRequester = FocusRequester()
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = font.fontContent,
            textStyle = TextStyle.Default.copy(
                background = font.fontBackground,
                fontSize =  font.fontSize.sp,
                fontFamily =  font.fontFamily,
                textAlign = TextAlign.Center,
                color =  font.fontColor
            ),
            onValueChange = {
                onValue(it)
            },
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent // -> Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier.focusRequester(focusRequester)
        )
        if( font.fontContent.isEmpty())
            Text(
                text = "Enter Something ...",
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontFamily =  font.fontFamily
            )
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FontPanel(defaultFont: FontFamily, fontFamilyPicker: (FontFamily) -> Unit){
    val pagerState = rememberPagerState(initialPage = 0, pageCount = ({5}))
    Box(contentAlignment = Alignment.BottomCenter){
        PagerItem(
            list =
                arrayOf(listFont.subList(0,4), listFont.subList(4,8), listFont.subList(8,12), listFont.subList(12,16), listFont.subList(16,20)).map {
                    ({ FontPanelItem(defaultFont = defaultFont, listFont = it, fontFamilyPicker = fontFamilyPicker) })
                },
            pagerState = pagerState
        )
        IndicatorItem(pagerState = pagerState)
    }

}

@Composable
fun FontPanelItem(defaultFont: FontFamily, listFont: List<Pair<String, FontFamily>>, fontFamilyPicker: (FontFamily) -> Unit){
    var fontFamily by remember { mutableStateOf(defaultFont) }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly) {
        for(font in listFont){
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        fontFamily = font.second
                        fontFamilyPicker(font.second)
                    },
                contentAlignment = Alignment.BottomCenter) {
                if (font.second == robotoFont)
                    Text(
                        text = "DEFAULT",
                        fontSize = 10.sp,
                        color = Color.White,
                        modifier = Modifier.offset(x = 0.dp, y = (-25).dp)
                    )
                Text(
                    text = font.first,
                    textAlign = TextAlign.Center,
                    color = if (fontFamily == font.second) Color.White else Color.LightGray,
                    fontFamily = font.second,
                    fontWeight = if (fontFamily == font.second) FontWeight.Bold else FontWeight.Normal,
                    fontSize = if (fontFamily == font.second) 20.sp else 18.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}