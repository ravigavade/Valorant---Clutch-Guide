package com.csaim.valorant_clutchguide

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.csaim.valorant_clutchguide.ui.theme.DarkBlueGray
import com.csaim.valorant_clutchguide.ui.theme.RedPrimary
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme
import com.csaim.valorant_clutchguide.ui.theme.valo

class CommunityPosts : ComponentActivity() {
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
    val context = LocalContext.current  // Retrieve the context


    // State to manage dialog visibility
    var showDialog by remember { mutableStateOf(true) }  // Show dialog initially

    // Check if the user is a first-time user using shared preferences
    val isFirstTime = isFirstTimeUser(context)
    if (isFirstTime && showDialog) {
        // Show the dialog
        FirstTimeDialog(onDismiss = {
            setFirstTimeUserFlag(context)  // Set flag after first-time user sees the message
            showDialog = false  // Dismiss the dialog
        })
    }

    // Fetch videos asynchronously
    LaunchedEffect(Unit) {
        try {
            videoList = videoManager.retrieveCommunityVideos()
            isError = videoList.isEmpty()
        } catch (e: Exception) {
            Log.e("Video Fetch", "Error fetching videos", e)
            isError = true
        } finally {
            isLoading = false
        }
    }

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.White)
            }
        }
        isError -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Failed to load videos. Please try again.", color = Color.Red)
            }
        }
        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBlueGray)
                    .statusBarsPadding()
            ) {
                items(videoList) { video ->
                    VideoCard1(video)
                }
            }
        }
    }
}


@Composable
fun VideoCard1(video: VideoData, modifier: Modifier = Modifier) {
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
                modifier.padding(4.dp),
                color = Color.White,
                fontSize = 18.sp,

                fontFamily = valo,
            )
            VideoPlayer1(video.url, Modifier.fillMaxHeight()) // Assign weight dynamically
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer1(videoUrl: String, fillMaxHeight: Modifier) {
    val context = LocalContext.current
    val exoPlayer = remember(videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            repeatMode = Player.REPEAT_MODE_ONE
            prepare()
        }
    }

    // Track if video is playing
    val isPlaying = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clickable {
                // Toggle play/pause on tap
                isPlaying.value = !isPlaying.value
                exoPlayer.playWhenReady = isPlaying.value
            }
    ) {
        // Video Player
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = false // Hide default controls
                    controllerAutoShow = false
                }
            },
            modifier = Modifier.matchParentSize()
        )

        // Show play button when paused
        if (!isPlaying.value) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_play_arrow_24),
                contentDescription = "Play Button",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(60.dp)
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}

@Composable
fun FirstTimeDialog(onDismiss: () -> Unit) {
    // Dialog content with a "Got it" button
    AlertDialog(
//        containerColor = RedPrimary,
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Welcome to the Community Post",
                fontFamily = valo,
                color = RedPrimary
            ) },
        text = {
            Text("This is a community post type page where all videos are for the gamers and by the gamers. Please maintain courtesy while uploading and watching the content.")
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Got it")
            }
        }
    )
}

// Function to check if it's the user's first time
fun isFirstTimeUser(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("isFirstTime", true)
}

// Function to set the first-time user flag to false
fun setFirstTimeUserFlag(context: Context) {
    val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putBoolean("isFirstTime", false)  // Set flag to false after user sees the dialog
        apply()
    }
}
