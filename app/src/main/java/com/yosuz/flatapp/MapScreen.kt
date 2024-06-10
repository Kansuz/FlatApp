package com.yosuz.flatapp

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.yosuz.flatapp.components.BottomNavigation
import com.yosuz.flatapp.components.BottomNavigationHorizontal
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
fun getCurrentLatLng(
    fusedLocationClient: FusedLocationProviderClient,
    onLocationReceived: (LatLng) -> Unit
) {
    val retryInterval = 2000L
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            val latLng = LatLng(location.latitude, location.longitude)
            onLocationReceived(latLng)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                getCurrentLatLng(fusedLocationClient, onLocationReceived)
            }, retryInterval)
        }
    }.addOnFailureListener { exception ->
        exception.printStackTrace()
        Handler(Looper.getMainLooper()).postDelayed({
            getCurrentLatLng(fusedLocationClient, onLocationReceived)
        }, retryInterval)
    }
}


@Composable
fun MapScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val cameraPositionState = rememberCameraPositionState {
        getCurrentLatLng(fusedLocationClient = fusedLocationClient) { latLng ->
            position = CameraPosition.fromLatLngZoom(latLng, 5f)
        }
    }

    var uiSettings by remember { mutableStateOf(MapUiSettings(compassEnabled = true)) }
    var properties by remember { mutableStateOf(MapProperties(mapType = MapType.TERRAIN, isMyLocationEnabled = true)) }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
            onMapClick = { latLng ->
                coroutineScope.launch {
                    cameraPositionState.animate(CameraUpdateFactory.newLatLng(latLng))
                }
            },
            onMyLocationClick = { location ->
                coroutineScope.launch {
                    cameraPositionState.animate(CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude)))
                }
            },
        ) {
        }
        Column(modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(16.dp)) {
//            Switch(
//                modifier = Modifier.padding(top = 8.dp),
//                checked = uiSettings.zoomControlsEnabled,
//                onCheckedChange = {
//                    uiSettings = uiSettings.copy(zoomControlsEnabled = it)
//                }
//            )
        }
        //BottomNavigation()
    }
}

@Composable
fun MapScreenWithBar(){
    Scaffold(
        //topBar = { UpperBar() },
        bottomBar = { BottomNavigation() }
    ) { padding ->
        Column(modifier = Modifier.consumeWindowInsets(padding)){
            MapScreen()
        }
    }
}

@Composable
fun MapScreenWithBarHorizontal(){
    Surface (color = MaterialTheme.colorScheme.background){
        Row(modifier = Modifier.fillMaxSize()){
            BottomNavigationHorizontal()
            MapScreen()
        }
    }
}

@Preview
@Composable
fun MapScreenWithBarPreview(){
    MapScreenWithBar()
}