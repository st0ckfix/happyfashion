package com.kotlin_jetpack_compose.happyfashion

import android.content.ContentUris
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.google.firebase.FirebaseApp
import com.kotlin_jetpack_compose.happyfashion.components.Gallery
import com.kotlin_jetpack_compose.happyfashion.components.addStory.AddContentScreen
import com.kotlin_jetpack_compose.happyfashion.components.addStory.DisplayType
import com.kotlin_jetpack_compose.happyfashion.components.addStory.FullDisplayType
import com.kotlin_jetpack_compose.happyfashion.components.addStory.FullDisplayUnit
import com.kotlin_jetpack_compose.happyfashion.components.addStory.LocalFullDisplay
import com.kotlin_jetpack_compose.happyfashion.items.TransformGesturesItem
import com.kotlin_jetpack_compose.happyfashion.items.TransformType
import com.kotlin_jetpack_compose.happyfashion.models.FontDecorationModel
import com.kotlin_jetpack_compose.happyfashion.models.PostContent
import com.kotlin_jetpack_compose.happyfashion.models.TransformGestureModel
import com.kotlin_jetpack_compose.happyfashion.ui.theme.HappyFashionTheme
import kotlinx.coroutines.coroutineScope
import okhttp3.internal.wait
import java.util.UUID
import kotlin.Unit
import kotlin.coroutines.suspendCoroutine


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            HappyFashionTheme {
                AddContentScreen()
            }
        }
    }
}

val verticalLyric = """
    1
    00:00:00,000 --> 00:00:02,840
    Some people want it all

    2
    00:00:02,840 --> 00:00:06,120
    but I don't want nothing at all.

    3
    00:00:06,720 --> 00:00:09,140
    If it ain't you, baby

    4
    00:00:09,140 --> 00:00:11,820
    if I ain't got you, baby.

    5
    00:00:12,160 --> 00:00:14,980
    Some people want diamond rings

    6
    00:00:14,980 --> 00:00:17,420
    some just want everything,

    7
    00:00:17,420 --> 00:00:20,900
    but everything means nothing

    8
    00:00:20,900 --> 00:00:23,340
    if I ain't got you
""".trimIndent()

val lyric = """
        1
        00:00:00,320 --> 00:00:00,680
        Some

        2
        00:00:00,680 --> 00:00:01,300
        people

        3
        00:00:01,300 --> 00:00:02,040
        want

        4
        00:00:02,040 --> 00:00:02,160
        it

        5
        00:00:02,160 --> 00:00:02,840
        all *

        6
        00:00:03,210 --> 00:00:03,310
        but

        7
        00:00:03,240 --> 00:00:03,520
        I

        8
        00:00:03,520 --> 00:00:04,560
        don't

        9
        00:00:04,560 --> 00:00:04,720
        want

        10
        00:00:04,720 --> 00:00:05,040
        nothing

        11
        00:00:05,040 --> 00:00:05,340
        at

        12
        00:00:05,340 --> 00:00:06,500
        all *

        13
        00:00:06,720 --> 00:00:06,810
        If

        14
        00:00:06,780 --> 00:00:07,300
        it

        15
        00:00:07,300 --> 00:00:07,800
        ain't

        16
        00:00:07,800 --> 00:00:08,240
        you

        17
        00:00:08,780 --> 00:00:09,140
        baby *

        18
        00:00:09,880 --> 00:00:09,910
        if

        19
        00:00:09,880 --> 00:00:10,060
        I

        20
        00:00:10,060 --> 00:00:10,320
        ain't

        21
        00:00:10,320 --> 00:00:10,620
        got

        22
        00:00:10,620 --> 00:00:11,120
        you

        23
        00:00:11,500 --> 00:00:12,020
        baby *

        24
        00:00:12,420 --> 00:00:12,700
        Some

        25
        00:00:12,700 --> 00:00:13,300
        people

        26
        00:00:13,300 --> 00:00:13,880
        want

        27
        00:00:13,880 --> 00:00:14,240
        diamond

        28
        00:00:14,240 --> 00:00:14,980
        rings *

        29
        00:00:15,380 --> 00:00:15,740
        some

        30
        00:00:15,740 --> 00:00:16,340
        just

        31
        00:00:16,340 --> 00:00:16,860
        want

        32
        00:00:16,860 --> 00:00:17,480
        everything *

        33
        00:00:18,290 --> 00:00:18,340
        but

        34
        00:00:18,340 --> 00:00:19,060
        everything

        35
        00:00:19,060 --> 00:00:19,780
        means

        36
        00:00:19,780 --> 00:00:20,900
        nothing *

        37
        00:00:20,900 --> 00:00:21,420
        if

        38
        00:00:21,420 --> 00:00:21,980
        I

        39
        00:00:21,980 --> 00:00:22,500
        ain't

        40
        00:00:22,500 --> 00:00:22,780
        got

        41
        00:00:22,780 --> 00:00:23,320
        you *
    """.trimIndent()