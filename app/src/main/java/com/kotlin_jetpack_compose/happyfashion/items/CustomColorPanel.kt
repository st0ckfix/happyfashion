package com.kotlin_jetpack_compose.happyfashion.items

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotlin_jetpack_compose.happyfashion.R
import com.kotlin_jetpack_compose.happyfashion.ui.theme.basicColor
import com.kotlin_jetpack_compose.happyfashion.ui.theme.listColor
import com.kotlin_jetpack_compose.happyfashion.ui.theme.robotoFont
import com.kotlin_jetpack_compose.happyfashion.ui.theme.secondListColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColorPanel(defaultColor: Color, isBackgroundColor: Boolean, colorPicker: (Color) -> Unit){
    val pagerState = rememberPagerState(initialPage = 0, pageCount = ({3}))
    Box(contentAlignment = Alignment.BottomCenter){
        PagerItem(
            list = listOf(
                ({ ColorPanelItem(defaultColor = defaultColor, listColor = if(isBackgroundColor) basicColor.subList(0,9) else basicColor, isBackgroundColor = isBackgroundColor, colorPicker = colorPicker) }),
                ({ ColorPanelItem(defaultColor = defaultColor, listColor = listColor, isBackgroundColor = false, colorPicker = colorPicker) }),
                ({ ColorPanelItem(defaultColor = defaultColor, listColor = secondListColor, isBackgroundColor = false, colorPicker = colorPicker) })

            ),
            pagerState = pagerState
        )
        IndicatorItem(pagerState = pagerState)
    }
}

@Composable
fun ColorPanelItem(defaultColor: Color, isBackgroundColor: Boolean, listColor: List<Color>, colorPicker: (Color) -> Unit){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 2.dp, end = 2.dp, top = 2.dp, bottom = 25.dp)) {
        if(isBackgroundColor){
            Box (contentAlignment = Alignment.BottomCenter){
                Text(
                    text = "DEFAULT",
                    fontSize = 10.sp,
                    color = Color.White,
                    modifier = Modifier.offset(x = 0.dp, y = (-35).dp)
                )
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(shape = CircleShape)
                        .background(Color.Transparent)
                        .border(
                            color = if (defaultColor == Color.Transparent) Color.White else Color.Transparent,
                            width = 2.dp,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clickable {
                                colorPicker(Color.Transparent)
                            }
                            .size(22.dp)
                            .clip(shape = CircleShape)
                            .border(color = Color.DarkGray, width = 2.dp, shape = CircleShape)){
                        Icon(painter = painterResource(id = R.drawable.transparent), contentDescription = null, modifier = Modifier.size(22.dp))
                    }
                }
            }
        }
        listColor.forEach {
            Box (contentAlignment = Alignment.BottomCenter){
                if (it == Color.White && !isBackgroundColor)
                    Text(
                        text = "DEFAULT",
                        fontSize = 10.sp,
                        color = Color.White,
                        modifier = Modifier.offset(x = 0.dp, y = (-30).dp)
                    )
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(shape = CircleShape)
                        .background(Color.Transparent)
                        .border(
                            color = if (defaultColor == it) Color.White else Color.Transparent,
                            width = 2.dp,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clickable {
                                colorPicker(it)
                            }
                            .size(22.dp)
                            .clip(shape = CircleShape)
                            .border(color = Color.DarkGray, width = 2.dp, shape = CircleShape)
                            .background(color = it))
                }
            }
        }
    }
}