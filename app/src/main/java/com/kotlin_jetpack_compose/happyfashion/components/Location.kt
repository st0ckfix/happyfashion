package com.kotlin_jetpack_compose.happyfashion.components

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun Location(
    onLocation: (Address?) -> Unit,
    onFail: () -> Unit
){
    val context = LocalContext.current
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val geocoder = Geocoder(context, Locale.getDefault())

    var LatandLong by remember { mutableStateOf(Pair(0.0, 0.0)) }

    RequestLocationPermission(
        onPermissionGranted = {
            println("Permission granted")
            if (
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationProviderClient.getCurrentLocation(
                    Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    CancellationTokenSource().token
                ).addOnSuccessListener { location ->
                    try {
                        LatandLong = Pair(location.latitude, location.longitude)
                    }
                    catch (e: Exception){
                        println(e.message)
                        onFail()
                    }

                }.addOnFailureListener {
                    println(it.message)
                    onFail()
                }
            }
        },
        onPermissionDenied = {
            println("Permission denied")
            onFail()
        },
        onPermissionsRevoked = {
            println("Permissions revoked")
            onFail()
        })

    LaunchedEffect(LatandLong) {
        onLocation(geocoder.getAddress(LatandLong.first, LatandLong.second))
    }
}

private suspend fun Geocoder.getAddress(
    latitude: Double,
    longitude: Double,
): Address? = withContext(Dispatchers.IO) {
    try {
        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            suspendCoroutine { cont ->
                getFromLocation(latitude, longitude, 1) {
                    cont.resume(it.firstOrNull())
                }
            }
        } else {
            suspendCoroutine { cont ->
                @Suppress("DEPRECATION")
                val address = getFromLocation(latitude, longitude, 1)?.firstOrNull()
                cont.resume(address)
            }
        }
    } catch (e: Exception) {
        Timber.e(e)
        null
    }
}