package com.kotlin_jetpack_compose.happyfashion.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotlin_jetpack_compose.happyfashion.R
import com.kotlin_jetpack_compose.happyfashion.components.CustomBottomAppBar
import com.kotlin_jetpack_compose.happyfashion.components.ListStory
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    //, fontWeight = FontWeight.Normal
                    title = { Text(text = "Happy Fashion", fontSize = 22.sp)},
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Image(painter = painterResource(id = R.drawable.add), contentDescription = null, modifier = Modifier.size(30.dp))
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Image(imageVector = Icons.Default.FavoriteBorder, contentDescription = null, modifier = Modifier.size(30.dp))
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Image(painter = painterResource(id = R.drawable.send), contentDescription = null, modifier = Modifier.size(30.dp))
                        }
                    })
                HorizontalDivider(thickness = 2.dp)
            }

        },
        bottomBar = {
            CustomBottomAppBar()
        }
    ) { paddingValues ->

        Box(modifier = Modifier.padding(paddingValues)) {
            LazyColumn {
                item {
                    ListStory()
                }
                item {
                    //PostItem()
                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}