package com.csaim.valorant_clutchguide

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.csaim.valorant_clutchguide.ui.theme.DarkBlueGray
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
            VideoScreen(
                selectedMap,
                selectedAgent,
                selectedSide,
                selectedSite
            )
        }
    }
}

@Composable
fun VideoScreen(
    map: String,
    agent:String,
    side:String,
    site: String
) {
    var videoList by remember { mutableStateOf<List<VideoData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val videoManager = VideoManager()

    // Fetch videos asynchronously
    LaunchedEffect(Unit) {
        try {
            videoList = videoManager.retrieveVideos(map,agent,side,site)
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
//                .padding(16.dp)
                .background(DarkBlueGray)
                .statusBarsPadding()

        ) {
            items(videoList) { video ->
                VideoCard(video)
//                Spacer(modifier = Modifier.height(16.dp))
//                Divider()//
            }
        }
    }
}

@Composable
fun VideoCard(video: VideoData, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
//            .fillMaxWidth()
            .background(DarkBlueGray)
            .padding(bottom = 16.dp)
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
//                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = video.name,
//                modifier.padding(top = 8.dp),
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = valo,
            )
            VideoPlayer(video.url, Modifier.fillMaxHeight()) // Assign weight dynamically
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(videoUrl: String, weight: Modifier) {
    // Remember the ExoPlayer instance to avoid reinitialization on recomposition

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

                useController = true // Enables controls, but we customize them
                // Hide unnecessary buttons and UI elements
                setShowFastForwardButton(false)
                setShowRewindButton(false)
                setShowNextButton(false)
                setShowPreviousButton(false)
                setShowShuffleButton(false)
                setShowVrButton(false)
                setShowMultiWindowTimeBar(false) // Hides the timeline

                controllerShowTimeoutMs = 1500 // Controls auto-hide after 3 sec
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f) // Adjusts the video aspect ratio
    )


    // Free resources when the composable is disposed

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}
