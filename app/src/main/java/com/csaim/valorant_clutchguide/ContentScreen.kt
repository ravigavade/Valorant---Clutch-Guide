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
                VideoPlayerScreen()
            }
        }
    }
}

@Composable
fun VideoPlayerScreen() {
    val context = LocalContext.current
    val videoUri = "android.resource://${context.packageName}/raw/samplevideo" // Change 'video' to your file name

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Card(
            modifier = Modifier
                //            .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium, // Rounds the corners of the card
            colors = CardDefaults.cardColors(containerColor = Color.Gray) // Card color
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {

                val exoPlayer = remember {
                    ExoPlayer.Builder(context).build().apply {
                        setMediaItem(MediaItem.fromUri(Uri.parse(videoUri)))
                        prepare()
                        playWhenReady = true
                    }
                }

                // Clean up player when the screen is disposed
                DisposableEffect(context) {
                    onDispose {
                        exoPlayer.release()
                    }
                }

                AndroidView(
                    factory = { ctx ->
                        PlayerView(ctx).apply {
                            player = exoPlayer
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
//                        .height(250.dp)

                )

            }
        }
    }

}
