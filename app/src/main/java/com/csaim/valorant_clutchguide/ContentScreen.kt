package com.csaim.valorant_clutchguide

import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
    var videoList by remember { mutableStateOf<List<VideoData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val videoManager = VideoManager()

    // Fetch videos asynchronously
    LaunchedEffect(Unit) {
        try {
            videoList = videoManager.retrieveVideos()
            isError = videoList.isEmpty()
        } catch (e: Exception) {
            Log.e("Video Fetch", "Error fetching videos", e)
            isError = true
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        // Show loading indicator while fetching
        Text("Loading videos...", color = Color.White)
//        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    } else if (isError) {
        // Show error message if the fetch fails
        Text("Failed to load videos. Please try again.", color = Color.Red)
    } else {
        // Display the videos if available
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(videoList) { video ->
                VideoCard(video)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Composable
fun VideoCard(video: VideoData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = video.name, // Now this should work as the 'name' property exists
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            VideoPlayer(video.url)
        }
    }
}






@Composable
fun VideoPlayer(videoUrl: String) {
    // Remember the ExoPlayer instance to avoid reinitialization on recomposition
    val context = LocalContext.current
    val exoPlayer = remember(videoUrl) {
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
