package com.kotlin_jetpack_compose.happyfashion.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kotlin_jetpack_compose.happyfashion.R

@Composable
fun CustomBottomAppBar(){
    var select by remember { mutableIntStateOf(0) }
    Column {
        HorizontalDivider(thickness = 2.dp)
        Row(modifier = Modifier.padding(10.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    select = 0
                }) {
                Image(
                    painter = painterResource(
                        id = if (select == 0) R.drawable.home_2 else R.drawable.home
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(if (select == 0) 30.dp else 22.dp)
                )
            }
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    select = 1
                }) {
                Image(
                    painter = painterResource(
                        id = if (select == 1) R.drawable.search_2 else R.drawable.search
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(if (select == 1) 30.dp else 22.dp)
                )
            }
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    select = 2
                }) {
                Image(
                    painter = painterResource(
                        R.drawable.add
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
            }
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    select = 3
                }) {
                Image(
                    painter = painterResource(
                        id = if (select == 3) R.drawable.reels_2 else R.drawable.reels
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(if (select == 3) 30.dp else 22.dp)
                )
            }
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    select = 4
                }) {
                Image(
                    painter = painterResource(
                        id = if (select == 4) R.drawable.person_2 else R.drawable.person
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(if (select == 4) 30.dp else 22.dp)
                )
            }

        }
    }
}