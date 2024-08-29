package com.kotlin_jetpack_compose.happyfashion.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kotlin_jetpack_compose.happyfashion.R

@Composable
fun StoryControlButton(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.padding(10.dp)) {
        OutlinedTextField(
            "",
            onValueChange = {},
            shape = CircleShape,
            label = { Text("Leave a comment ...")},
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors().copy(
                unfocusedIndicatorColor = Color.White,
                unfocusedLabelColor = Color.White
            ))
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = Color.White)
        }
        IconButton(onClick = { /*TODO*/ }) {
            Image(
                painter = painterResource(id = R.drawable.send),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(30.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StoryControlButtonPreview(){
    StoryControlButton()
}