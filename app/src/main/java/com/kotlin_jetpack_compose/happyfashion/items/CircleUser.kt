package com.kotlin_jetpack_compose.happyfashion.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.kotlin_jetpack_compose.happyfashion.R
import java.sql.Timestamp

@Composable
fun CircleUser(
    image: Any,
    display: String,
    isVerify: Boolean = false,
    venue: String ?= null,
    mention: List<String> ?= null,
    timestamp: Long ?= null,
    color: Map<String, Color> = mapOf(
        "display" to Color.Black,
        "venue" to Color.Black,
        "mention" to Color.Black,
        "timestamp" to Color.Black
    ),
    onImageClick: () -> Unit = {},
    onDisplayClick: () -> Unit = {},
    onVenueClick: () -> Unit = {},
    onMentionClick: () -> Unit = {}
){
    val density = LocalDensity.current.density
    var timestampWidth by remember { mutableIntStateOf(0) }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(
                    start = 5.dp,
                    top = 5.dp,
                    bottom = 5.dp,
                    end = (timestampWidth / density).dp
                )) {
            Box(modifier = Modifier
                .padding(start = 5.dp, end = 10.dp)
                .width(35.dp)
                .height(35.dp)
                .clip(
                    CircleShape
                )
                .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                .background(color = Color.Transparent),
                contentAlignment = Alignment.Center){
                Image(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(3.dp)
                        .clip(shape = CircleShape)
                        .clickable { onImageClick() },
                    painter = if(image is String) rememberAsyncImagePainter(model = image) else painterResource(id = image as Int),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = remember { display },
                        overflow = TextOverflow.Ellipsis,
                        color = color["display"]!!,
                        maxLines = 1,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { onDisplayClick() })
                    Image(painter = painterResource(id = R.drawable.verify), contentDescription = null)
                }

                Box {
                    Row {
                        if(venue != null)
                            Text(
                                text = "at $venue",
                                overflow = TextOverflow.Ellipsis,
                                color = color["venue"]!!,
                                maxLines = 1,
                                fontSize = 11.sp,
                                modifier = Modifier.clickable { onVenueClick() })
                        if(mention != null){
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "w/ " + mention.joinToString(", "),
                                overflow = TextOverflow.Ellipsis,
                                color = color["mention"]!!,
                                fontStyle = FontStyle.Italic,
                                maxLines = 1,
                                fontSize = 11.sp,
                                modifier = Modifier.clickable { onMentionClick() },
                            )
                        }
                    }
                }
            }
        }
        
        if(timestamp != null)
            Text(
                text = timestamp.toLabel(),
                fontSize = 11.sp,
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        if (timestampWidth == 0) {
                            timestampWidth = coordinates.size.width + 30
                        }
                    }
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp))

    }
}

fun Long.toLabel(): String {
    val time = System.currentTimeMillis() / 1000 - this
    val days = time / (1000 * 60 * 60 * 24)
    val hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
    val minutes = (time % (1000 * 60 * 60)) / (1000 * 60)
    return if(days == 0L && hours == 0L && minutes == 0L) "just now"
    else if(days == 0L && hours == 0L) "$minutes minutes ago"
    else if(days == 0L) "$hours hours ago"
    else if(days == 1L) "yesterday"
    else "$days days ago"
}

@Preview(showBackground = true)
@Composable
fun CircleUserPreview(){
    CircleUser(
        display = "olivia_rodrigo",
        image = R.drawable.olivia,
        venue = "Manchester",
        timestamp = System.currentTimeMillis(),
        mention = listOf("taylor_swift", "eminem", "drake", "kendrick_lamar", "rihanna", "beyonce"),
        onImageClick = {
            println("Image Click")
        },
        onDisplayClick = {
            println("Display Click")
        },
        onVenueClick = {
            println("Venue Click")
        },
        onMentionClick = {
            println("Mention Click")
        }
    )
}