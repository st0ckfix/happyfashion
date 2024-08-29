package com.kotlin_jetpack_compose.happyfashion.components

import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.kotlin_jetpack_compose.happyfashion.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects

enum class SHOW(val drawable: Int, val text: String) {
    CAMERA(R.drawable.camera, "Camera"), RECORD(R.drawable.record, "Record") ,IMAGE(R.drawable.image, "Image"), VIDEO(R.drawable.video, "Video"), ALL(R.drawable.all, "All");
    fun icon(): Int = drawable
    fun title(): String = text
}

@Composable
fun Gallery(
    imageUrl: (Uri ?) -> Unit
){
    var required by remember { mutableStateOf(false) }
    var permissionCount by remember { mutableIntStateOf(0) }

    RequiredPermission(
        onPermissionFailed = {
            required = false
        },
        onPermissionGranted = {
            permissionCount++
        }
    )

    LaunchedEffect(permissionCount){
        println(permissionCount)
        if(permissionCount == 2)
            required = true
    }

    if(required)
        GalleryRead(imageUrl)
}

@Composable
fun RequiredPermission(
    onPermissionFailed: () -> Unit,
    onPermissionGranted: () -> Unit
) {
    var mode by remember { mutableIntStateOf(0) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            onPermissionGranted()
            mode ++
        }
        else{
            onPermissionFailed()
        }
    }

    if(mode == 0) {
        ReadCameraPermission(
            onReadCamera = { launcher.launch(android.Manifest.permission.CAMERA) },
            onHavePermission = {
                mode = 1
                onPermissionGranted()
            }
        )
    }

    if(mode == 1) {
        ReadStoragePermission(
            onReadStorage = { launcher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE) },
            onHavePermission = {
                onPermissionGranted()
            }
        )
    }
}

@Composable
fun ReadCameraPermission(
    onReadCamera: () -> Unit,
    onHavePermission: () -> Unit
){
    println("read camera")
    val context = LocalContext.current
    if (ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.CAMERA
        )
        != PackageManager.PERMISSION_GRANTED
    ) {
        println("no camera permission")
        LaunchedEffect(Unit) {
            onReadCamera()
        }
    }
    else {
        println("have camera permission")
        onHavePermission()
    }
}

@Composable
fun ReadStoragePermission(
    onReadStorage: () -> Unit,
    onHavePermission: () -> Unit
) {
    println("read storage")
    val context = LocalContext.current
    if (ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        != PackageManager.PERMISSION_GRANTED
    ) {
        println("no storage permission")
        LaunchedEffect(Unit) {
            onReadStorage()
        }
    }
    else {
        println("have storage permission")
        onHavePermission()
    }
}

@Composable
fun CameraRead(
    mode: SHOW,
    imageUrl: (Uri) -> Unit
){
    val context = LocalContext.current
    val mediaFile = createMediaFile(context, mode)
    val mediaURI = FileProvider.getUriForFile(Objects.requireNonNull(context), "${context.packageName}.provider", mediaFile)

    val launcher = rememberLauncherForActivityResult(
        contract = if(mode == SHOW.CAMERA) ActivityResultContracts.TakePicture() else ActivityResultContracts.CaptureVideo()
    ) {
        if(it){
            println(mediaURI)
            imageUrl(mediaURI)
        }
    }

    LaunchedEffect(Unit){
        launcher.launch(mediaURI)
    }
}

fun createMediaFile(context: Context, mode: SHOW): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = context.getExternalFilesDir(if(mode == SHOW.RECORD) Environment.DIRECTORY_MOVIES else Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "${timeStamp}_",
        if(mode == SHOW.RECORD) ".mp4" else ".jpg",
        storageDir
    )
}

@Composable
fun GalleryRead(
    imageUrl: (Uri) -> Unit
){
    val context = LocalContext.current
    val listImage = mutableListOf<Uri>()
    val listVideo = mutableListOf<Uri>()

    val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaStore.Images.Media.DATE_ADDED
    )

    val selection =
        "${MediaStore.Images.Media.MIME_TYPE} IN (?, ?, ?, ?, ?)" // Add more MIME types as needed "
    val selectionArgs = arrayOf("image/png", "image/jpeg", "image/jpg", "video/mp4", "video/mvk")
    val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

    fun cursorReturn(uri: Uri?) : Cursor? {
        return context.contentResolver.query(
            uri!!,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
    }

    val imageCursor = cursorReturn(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

    val videoCursor = cursorReturn(MediaStore.Video.Media.EXTERNAL_CONTENT_URI)

    arrayOf(imageCursor, videoCursor).forEach {
        it?.use { cursor ->
            val uri = if(it == imageCursor) MediaStore.Images.Media.EXTERNAL_CONTENT_URI else MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            while (it.moveToNext()) {
                val id =
                    it.getLong(it.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                val name =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))

                val contentUri = ContentUris.withAppendedId(
                    uri,
                    id
                )
                if(it == imageCursor)
                    listImage.add(contentUri)
                else
                    listVideo.add(contentUri)
            }
            cursor.close()
        }
    }

    GalleryDisplay(listImage = listImage, listVideo = listVideo, imageUrl)
}

@Composable
fun GalleryDisplay(
    listImage: List<Uri>,
    listVideo: List<Uri>,
    imageUrl: (Uri) -> Unit){
    var show by remember { mutableStateOf(SHOW.ALL) }
    var mode by remember { mutableStateOf(SHOW.CAMERA) }
    var showCamera by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
            .padding(top = 10.dp)
    ) {
        GalleryDisplayControl(default = show){
            when(it){
                SHOW.CAMERA -> {
                    showCamera = true
                    mode = SHOW.CAMERA
                }
                SHOW.RECORD -> {
                    showCamera = true
                    mode = SHOW.RECORD
                }
                else -> { show = it }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        MediaDisplay(listImage = listImage, listVideo = listVideo, show, imageUrl)
    }

    if(showCamera)
        CameraRead(mode, imageUrl)
}

@Composable
fun GalleryDisplayControl(
    default: SHOW,
    onShow: (SHOW) -> Unit = {}
){
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        SHOW.entries.forEach {
            Box(
                Modifier
                    .clip(shape = RoundedCornerShape(15))
                    .width(60.dp)
                    .background(color = if (it == default) Color.Gray else Color.DarkGray)
                    .clickable {
                        onShow(it)
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(painter = painterResource(id = it.icon()), contentDescription = null, modifier = Modifier.size(24.dp), colorFilter = ColorFilter.tint(color = Color.White))
                    Text(text = it.title(), fontSize = 12.sp, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun MediaDisplay(listImage: List<Uri>, listVideo: List<Uri>, show: SHOW, imageUrl: (Uri) -> Unit){
    val context = LocalContext.current
    val listImageShow: List<@Composable () -> Unit> = remember(listImage) {
        listImage.map {
            @Composable {
                AsyncImage(
                    modifier = Modifier
                        .width(150.dp)
                        .height(250.dp)
                        .padding(2.dp)
                        .clickable { imageUrl(it) },
                    model = it, contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

    val listVideoShow: List<@Composable () -> Unit> = remember(listVideo) {
        listVideo.map {
            val thumbnail = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                context.contentResolver.loadThumbnail(it, Size(150,250), null)
            }
            else {
                @Suppress("DEPRECATION")
                MediaStore.Video.Thumbnails.getThumbnail(
                    context.contentResolver,
                    ContentUris.parseId(it),
                    MediaStore.Video.Thumbnails.MINI_KIND,
                    null
                )
            }
            @Composable {
                Box(modifier = Modifier.padding(2.dp).clickable { imageUrl(it) }) {
                    Image(
                        modifier = Modifier
                            .width(150.dp)
                            .height(250.dp),
                        contentScale = ContentScale.Crop,
                        bitmap = thumbnail.asImageBitmap(), contentDescription = null,
                    )
                    Box(modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(4.dp)
                        .background(Color.Black.copy(alpha = .5f))) {
                        Text(text = "Video", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        if(show == SHOW.IMAGE || show == SHOW.ALL){
            items(listImageShow.size) {
                listImageShow[it]()
            }
        }
        if(show == SHOW.VIDEO || show == SHOW.ALL){
            items(listVideoShow.size) {
                listVideoShow[it]()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReadStoragePreview(){
}