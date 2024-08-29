package com.kotlin_jetpack_compose.happyfashion.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun CircleStory(pair: Pair<Any, String>, firstItem: Boolean, onSelect: (String?) -> Any ? = {}){
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(90.dp)
            .height(110.dp)
            .clickable {
                onSelect(null)
            }) {
        Box {
            Box(modifier = Modifier
                .width(70.dp)
                .height(70.dp)
                .clip(
                    CircleShape
                )
                .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                .background(color = Color.Transparent),
                contentAlignment = Alignment.Center) {

                Image(
                    modifier = Modifier
                        .padding(3.dp)
                        .clip(CircleShape),
                    painter = rememberAsyncImagePainter(pair.first),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
            if(firstItem)
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .offset(45.dp, 45.dp)
                        .size(30.dp)
                        .clip(shape = CircleShape)
                        .background(color = Color.White)) {
                    Icon(
                        modifier = Modifier.padding(all = 2.dp),
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = null,
                    )
                }
        }
        Text(text = pair.second, overflow = TextOverflow.Ellipsis, maxLines = 1, textAlign = TextAlign.Center, modifier = Modifier.width(80.dp), fontSize = 12.sp)
    }
}