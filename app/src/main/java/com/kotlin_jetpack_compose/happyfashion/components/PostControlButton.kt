package com.kotlin_jetpack_compose.happyfashion.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kotlin_jetpack_compose.happyfashion.R

@Composable
fun PostControlButton(){
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(5.dp)) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
        }
        IconButton(onClick = { /*TODO*/ }) {
            Image(painter = painterResource(id = R.drawable.comment), contentDescription = null, modifier = Modifier.size(22.dp))
        }
        IconButton(onClick = { /*TODO*/ }) {
            Image(painter = painterResource(id = R.drawable.send), contentDescription = null, modifier = Modifier.size(22.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Image(painter = painterResource(id = R.drawable.bookmark), contentDescription = null, modifier = Modifier.size(22.dp))
        }
    }
}