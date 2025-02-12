package com.csaim.valorant_clutchguide

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme
import com.csaim.valorant_clutchguide.ui.theme.valo

class ContentScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Retrieve accumulated extras from previous screens
        val selectedMap = intent.getStringExtra("mapName") ?: "Unknown Map"
        val selectedAgent = intent.getStringExtra("agentName") ?: "Unknown Agent"
        val selectedSide = intent.getStringExtra("side") ?: "Unknown Side"
        val selectedSite = intent.getStringExtra("site") ?: "Unknown Site"

        setContent {
            ValorantClutchGuideTheme {
                ContentScreenContent(
                    selectedMap = selectedMap,
                    selectedAgent = selectedAgent,
                    selectedSide = selectedSide,
                    selectedSite = selectedSite
                )
            }
        }
    }
}

@Composable
fun ContentScreenContent(
    selectedMap: String,
    selectedAgent: String,
    selectedSide: String,
    selectedSite: String
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Header displaying the accumulated selections
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            Text(
                text = "Map: $selectedMap",
                fontFamily = valo,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Agent: $selectedAgent",
                fontFamily = valo,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Side: $selectedSide",
                fontFamily = valo,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Site: $selectedSite",
                fontFamily = valo,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
        // Video list area; using Box with weight(1f) so it takes the remaining space.
        Box(modifier = Modifier.weight(1f)) {
            VideoScreen()
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
        // Show a loading message or indicator
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Loading videos...", color = Color.White)
        }
    } else if (isError) {
        // Show an error message if the fetch fails
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Failed to load videos. Please try again.", color = Color.Red)
        }
    } else {
        // Display the videos in a scrollable list
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
                text = video.name,
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
    // Create and remember the ExoPlayer instance
    val context = LocalContext.current
    val exoPlayer = remember(videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
        }
    }

    // Use AndroidView to integrate the PlayerView
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )

    // Release ExoPlayer when no longer needed
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}
