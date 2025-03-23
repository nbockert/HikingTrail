package com.example.polyline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HikingTrail()

        }
    }
}

@Composable
fun HikingTrail() {
    val context = LocalContext.current
    var polylineColor by remember { mutableStateOf(Color.Red) }
    var strokeWidth by remember { mutableStateOf(8f) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(37.7780, -122.4170), 14f)
    }
    val trailSegments = listOf(
        Pair("Candlestick Point", listOf(
            LatLng(37.7136, -122.3860),
            LatLng(37.7213, -122.4047)
        )),
        Pair("Visitacion Valley Greenway", listOf(
            LatLng(37.7213, -122.4047),
            LatLng(37.7289, -122.4194)
        )),
        Pair("McLaren Park", listOf(
            LatLng(37.7289, -122.4194),
            LatLng(37.7385, -122.4350)
        ))
    )
    val parkArea = listOf(
        LatLng(37.7715, -122.4547),
        LatLng(37.7715, -122.5116),
        LatLng(37.7645, -122.5116),
        LatLng(37.7645, -122.4547)
    )
    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Row(horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = { polylineColor = Color.Red },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text("Red", color = Color.White)
                    }

                    Button(
                        onClick = { polylineColor = Color.Green },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text("Green", color = Color.White)
                    }

                    Button(
                        onClick = { polylineColor = Color.Blue },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text("Blue", color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Width: ${strokeWidth.toInt()}")
                Slider(
                    value = strokeWidth,
                    onValueChange = { strokeWidth = it },
                    valueRange = 1f..30f
                )
            }
        }
    ){ paddingValues ->
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            cameraPositionState = cameraPositionState
        ){
            trailSegments.forEach { (name, path) ->
                Polyline(
                    points = path,
                    color = polylineColor,
                    width = strokeWidth,
                    clickable = true,
                    onClick = {
                        Toast.makeText(context, "Trail: $name", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            Polygon(
                points = parkArea,
                fillColor = polylineColor.copy(alpha = 0.3f),
                strokeColor = polylineColor,
                strokeWidth = strokeWidth,
                clickable = true,
                onClick = {
                    Toast.makeText(context, "Park Info: Golden Gate Park", Toast.LENGTH_SHORT).show()
                }
            )
        }

    }
}

