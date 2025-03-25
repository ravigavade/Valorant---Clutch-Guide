package com.csaim.valorant_clutchguide

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.csaim.valorant_clutchguide.ui.theme.*

class CommunityPosts : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ValorantClutchGuideTheme {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    window.statusBarColor = DarkBlueGray.toArgb()
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                } else {
                    window.statusBarColor = DarkBlueGray.toArgb()
                }
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
    var currentlyPlayingUrl by remember { mutableStateOf<String?>(null) }

    val videoManager = VideoManager()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(true) }
    val isFirstTime = isFirstTimeUser(context)

    if (isFirstTime && showDialog) {
        FirstTimeDialog(onDismiss = {
            setFirstTimeUserFlag(context)
            showDialog = false
        })
    }

    LaunchedEffect(Unit) {
        try {
            videoList = videoManager.retrieveCommunityVideos().shuffled()
            isError = videoList.isEmpty()
        } catch (e: Exception) {
            Log.e("Video Fetch", "Error fetching videos", e)
            isError = true
        } finally {
            isLoading = false
        }
    }

    when {
        isLoading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            CircularProgressIndicator(color = Color.White)
        }

        isError -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            Text("Failed to load videos. Please try again.", color = Color.Red)
        }

        else -> Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MuchDarkBlueGray)
                .statusBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkBlueGray)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Community Clips",
                    fontFamily = valo,
                    color = Color.White,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Card(shape = RoundedCornerShape(50.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "App Icon",
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
                    .background(MuchDarkBlueGray)
                    .statusBarsPadding()
            ) {
                items(videoList) { video ->
                    VideoCard1(
                        video = video,
                        currentlyPlayingUrl = currentlyPlayingUrl,
                        onPlayRequested = { url ->
                            currentlyPlayingUrl =
                                if (currentlyPlayingUrl == url) null else url
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun VideoCard1(
    video: VideoData,
    currentlyPlayingUrl: String?,
    onPlayRequested: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .background(MuchDarkBlueGray)
            .padding(bottom = 16.dp)
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlueGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "@" + video.name,
                modifier = Modifier.padding(4.dp),
                color = Color.White,
                fontSize = 18.sp,
            )
            VideoPlayer1(
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
fun VideoPlayer1(
    videoUrl: String,
    isPlaying: Boolean,
    onPlay: () -> Unit
) {
    val context = LocalContext.current

    val exoPlayer = remember(videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            repeatMode = Player.REPEAT_MODE_ONE
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
                    controllerHideOnTouch = true
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

@Composable
fun FirstTimeDialog(onDismiss: () -> Unit) {
    AlertDialog(
        containerColor = MuchDarkBlueGray,
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Welcome to the Community Posts",
                fontFamily = valo,
                color = RedPrimary
            )
        },
        text = {
            Text(
                "Welcome to the Community Posts section – a place where Valorant players share, learn, and showcase their best lineups and outplays. This space is built by gamers, for gamers. Please be respectful while uploading and engaging with content. Let's keep the community helpful and competitive!\n\n" +
                        "For the best viewing experience, rotate your phone to landscape mode while watching clips.",
                color = Color.White
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Got it", color = RedPrimary, fontFamily = valo)
            }
        }
    )
}

fun isFirstTimeUser(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("isFirstTime", true)
}

fun setFirstTimeUserFlag(context: Context) {
    val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putBoolean("isFirstTime", false)
        apply()
    }
}
