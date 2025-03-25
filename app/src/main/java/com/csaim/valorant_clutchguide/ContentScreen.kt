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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.csaim.valorant_clutchguide.ui.theme.DarkBlueGray
import com.csaim.valorant_clutchguide.ui.theme.MuchDarkBlueGray
import com.csaim.valorant_clutchguide.ui.theme.RedPrimary
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme
import com.csaim.valorant_clutchguide.ui.theme.valo

class ContentScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color.Black)
//                .padding(16.dp)
//                .statusBarsPadding()
//        ) {
//            Text(
//                text = "Map: $selectedMap",
//                fontFamily = valo,
//                color = Color.White,
//                fontSize = 20.sp,
//                modifier = Modifier.fillMaxWidth()
//            )
//            Text(
//                text = "Agent: $selectedAgent",
//                fontFamily = valo,
//                color = Color.White,
//                fontSize = 20.sp,
//                modifier = Modifier.fillMaxWidth()
//            )
//            Text(
//                text = "Side: $selectedSide",
//                fontFamily = valo,
//                color = Color.White,
//                fontSize = 20.sp,
//                modifier = Modifier.fillMaxWidth()
//            )
//            Text(
//                text = "Site: $selectedSite",
//                fontFamily = valo,
//                color = Color.White,
//                fontSize = 20.sp,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
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
    agent: String,
    side: String,
    site: String
) {
    var videoList by remember { mutableStateOf<List<VideoData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    var currentlyPlayingUrl by remember { mutableStateOf<String?>(null) }

    val videoManager = VideoManager()

    LaunchedEffect(Unit) {
        try {
            videoList = videoManager.retrieveVideos(map, agent, side, site)
            isError = videoList.isEmpty()
        } catch (e: Exception) {
            Log.e("Video Fetch", "Error fetching videos", e)
            isError = true
        } finally {
            isLoading = false
        }
    }

    when {
        isLoading -> Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlueGray),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = RedPrimary, strokeWidth = 4.dp)
        }

        isError -> Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MuchDarkBlueGray),
            contentAlignment = Alignment.Center
        ) {
            Text("Failed to load videos. Please try again.", fontFamily = valo, color = Color.Red)
        }

        else -> Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlueGray)
                .padding(top = 10.dp)
                .statusBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBlueGray)
            ) {
                items(videoList) { video ->
                    VideoCard(
                        video = video,
                        currentlyPlayingUrl = currentlyPlayingUrl,
                        onPlayRequested = { url ->
                            currentlyPlayingUrl = if (currentlyPlayingUrl == url) null else url
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun VideoCard(
    video: VideoData,
    currentlyPlayingUrl: String?,
    onPlayRequested: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .background(DarkBlueGray)
            .padding(bottom = 16.dp)
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = video.name,
                modifier = Modifier.padding(4.dp),
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = valo
            )
            VideoPlayer(
                videoUrl = video.url,
                isPlaying = (video.url == currentlyPlayingUrl),
                onPlay = {
                    onPlayRequested(
                        if (video.url == currentlyPlayingUrl) null else video.url
                    )
                }
            )
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    videoUrl: String,
    isPlaying: Boolean,
    onPlay: () -> Unit
) {
    val context = LocalContext.current
    val exoPlayer = remember(videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
        }
    }

    LaunchedEffect(isPlaying) {
        exoPlayer.playWhenReady = isPlaying
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clickable { onPlay() }
    ) {
        AndroidView(
            factory = {
                PlayerView(it).apply {
                    player = exoPlayer
                    useController = false
                    controllerAutoShow = false
                }
            },
            modifier = Modifier.matchParentSize()
        )

        if (!isPlaying) {
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
