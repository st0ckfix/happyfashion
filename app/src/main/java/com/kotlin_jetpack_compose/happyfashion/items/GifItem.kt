package com.kotlin_jetpack_compose.happyfashion.items

import android.annotation.SuppressLint
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.disk.DiskCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import com.kotlin_jetpack_compose.happyfashion.models.GIPHY
import kotlinx.coroutines.Dispatchers

@SuppressLint("ObsoleteSdkInt")
@Composable
fun GifImage(
    modifier: Modifier = Modifier,
    gif: GIPHY ? = null,
    local: Any ? = null,
    onClick: (GIPHY) -> Unit = {}
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(.2)
                .build()
        }
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context)
                .data(data = gif?.url ?: local!!)
                .dispatcher(Dispatchers.IO)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .apply(block = { size(Size.ORIGINAL)
                }).build(), imageLoader = imageLoader
        ),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = modifier
            .clickable {
                if(gif != null)
                    onClick(gif)
            }
            .width(gif?.width?.toFloat()?.dp ?: 30.dp)
            .padding(all = 5.dp),
    )
}