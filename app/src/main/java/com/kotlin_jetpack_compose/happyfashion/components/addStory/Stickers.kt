package com.kotlin_jetpack_compose.happyfashion.components.addStory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotlin_jetpack_compose.happyfashion.R

enum class Stickers(val drawable: Int, private val title: String) {
    PLACE(R.drawable.stk_place, "Place"),
    MENTION(R.drawable.stk_mention, "Mention"),
    GIF(R.drawable.stk_search, "GIF"),
    HASHTAG(R.drawable.stk_hastag, "Hashtag"),
    LINK(R.drawable.stk_link, "Link"),
    EMOJI(R.drawable.stk_emoji, "Emoji");

    fun icon(): Int = drawable
    fun title(): String = title
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Stickers(onStickerSelect: (Stickers?) -> Unit) {
    FlowRow(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center,
        maxItemsInEachRow = 2
    ) {
        for (stickeR in Stickers.entries)
            StickerItem(
                stickers = stickeR,
                onStickerSelect = {
                    when (it) {
                        Stickers.PLACE -> { onStickerSelect(Stickers.PLACE) }
                        Stickers.MENTION -> { onStickerSelect(Stickers.MENTION) }
                        Stickers.GIF -> { onStickerSelect(Stickers.GIF) }
                        Stickers.HASHTAG -> { onStickerSelect(Stickers.HASHTAG) }
                        Stickers.LINK -> { onStickerSelect(Stickers.LINK) }
                        Stickers.EMOJI -> { onStickerSelect(Stickers.EMOJI) }
                    }
                }
            )
    }
}

@Composable
fun StickerItem(stickers: Stickers, onStickerSelect: (Stickers) -> Unit) {
    val rotation = (-2..2).random()
    Box(
        modifier = Modifier
            .rotate(rotation.toFloat())
            .clickable {
                onStickerSelect(stickers)
            }
            .padding(5.dp)
            .clip(shape = RoundedCornerShape(15))
            .background(color = Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(5.dp)) {
            Image(painter = painterResource(id = stickers.icon()), contentDescription = null, modifier = Modifier.size(25.dp))
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = stickers.title(), fontSize = 20.sp, color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StickersPreview(){
    Box(modifier = Modifier.background(color = Color(0xFF2E2C2C))){
        Stickers{}
    }
}