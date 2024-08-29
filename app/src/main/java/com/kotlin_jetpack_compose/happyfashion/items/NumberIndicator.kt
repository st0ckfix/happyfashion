package com.kotlin_jetpack_compose.happyfashion.items

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NumberIndicatorItem(pagerState: PagerState){
    Box(modifier = Modifier.background(color = Color.Black.copy(alpha = .75f), shape = RoundedCornerShape(50))){
        with(pagerState){
            Text(
                text = (pagerState.currentPage + 1).toString() + "/" + pagerState.pageCount,
                color = Color.White,
                modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
            )
        }
    }
}