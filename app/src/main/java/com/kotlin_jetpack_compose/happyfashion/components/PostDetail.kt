package com.kotlin_jetpack_compose.happyfashion.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PostDetail(
    modifier: Modifier = Modifier,
    likes: Int,
    content: String?,
    comments: Int
){
    Column(modifier = Modifier.padding(start = 10.dp)) {
        Text(text = "$likes Likes", fontWeight = FontWeight.Bold)
        if(content != null) Text(text = content, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "$comments Comments", fontStyle = FontStyle.Italic, color = Color.DarkGray)
    }
}

@Preview(showBackground = true)
@Composable
fun PostDetailPreview(){
    PostDetail(likes = 100, content = "Look so fresh today! #HappyFashion", comments = 10)
}