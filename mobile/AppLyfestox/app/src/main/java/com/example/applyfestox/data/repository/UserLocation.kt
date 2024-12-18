package com.example.applyfestox.data.repository

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices

@SuppressLint("MissingPermission")
@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun getUserLocation(context: Context): State<Pair<Double, Double>?> {
    val locationState = remember { mutableStateOf<Pair<Double, Double>?>(null) }
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(Unit) {
        if (permissionState.status.isGranted) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    locationState.value = Pair(it.latitude, it.longitude)
                }
            }
        } else {
            permissionState.launchPermissionRequest()
        }
    }

    return locationState
}
