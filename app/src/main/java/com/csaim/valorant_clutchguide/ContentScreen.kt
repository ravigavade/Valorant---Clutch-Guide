package com.csaim.valorant_clutchguide

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme

class ContentScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ValorantClutchGuideTheme {

                VideoScreen()
            }
        }
    }
}

@Composable
fun VideoScreen() {
    var videoUrl by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val videoManager = VideoManager()

    // Launch the coroutine when the composable is composed
    LaunchedEffect(Unit) {
        isLoading = true
        // Fetch video URL from the API
        videoUrl = videoManager.retrieveVideo()
        isLoading = false
    }

    // Show loading spinner or display the video
    if (isLoading) {
        // You can show a loading indicator
        Text("Loading...")
    } else {
        if (videoUrl != null) {
            VideoPlayer(videoUrl = videoUrl!!)
        } else {
            // Handle error if video URL is null
            Text("Failed to fetch video.")
        }
    }
}

@Composable
fun VideoPlayer(videoUrl: String) {
    // Use ExoPlayer to play the video
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
        }
    }

    // Use PlayerView to display the video
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp) // Set a fixed height for video player
    )

    // Free resources when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}