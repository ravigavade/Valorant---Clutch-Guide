package com.csaim.valorant_clutchguide

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme
import com.csaim.valorant_clutchguide.ui.theme.valo
import kotlinx.coroutines.launch
import java.io.File
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.compose.ui.viewinterop.AndroidView

class VideoUploadActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ValorantClutchGuideTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    VideoUploadScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun VideoUploadScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val videoManager = remember { VideoManager() }  // Initialize VideoManager
    var selectedVideoUri by remember { mutableStateOf<Uri?>(null) }
    var showSizeLimitDialog by remember { mutableStateOf(false) }  // State for showing dialog
    val coroutineScope = rememberCoroutineScope()

    // ExoPlayer Instance
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    val pickVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                val file = uriToFile(uri, context)
                if (file != null) {
                    val fileSizeMB = file.length() / (1024 * 1024)  // Convert bytes to MB
                    if (fileSizeMB > 30) {
                        showSizeLimitDialog = true  // Show error dialog if file is too large
                    } else {
                        selectedVideoUri = uri
                        Log.d("Video Upload", "Selected video: Size: ${fileSizeMB}MB")

                        // Prepare the video for playback
                        val mediaItem = MediaItem.fromUri(uri)
                        exoPlayer.setMediaItem(mediaItem)
                        exoPlayer.prepare()
                    }
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                pickVideoLauncher.launch(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pick Video")
        }

        Spacer(modifier = Modifier.height(16.dp))

        selectedVideoUri?.let {
            Text("Selected Video: ", fontFamily = valo)

            Spacer(modifier = Modifier.height(16.dp))

            // Video Preview using ExoPlayer
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = true  // Show video controls
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                selectedVideoUri?.let { uri ->
                    coroutineScope.launch {
                        val success = videoManager.uploadCommunityVideo(uri, context)
                        Log.d("Video Upload", "Upload successful: $success")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedVideoUri != null
        ) {
            Text("Upload Video")
        }
    }

    // Show an AlertDialog if file is too large
    if (showSizeLimitDialog) {
        AlertDialog(
            onDismissRequest = { showSizeLimitDialog = false },
            confirmButton = {
                Button(onClick = { showSizeLimitDialog = false }) {
                    Text("OK", fontFamily = valo)
                }
            },
            title = { Text("File Size Too Large", fontFamily = valo) },
            text = { Text("Please select a video smaller than 30MB.", fontFamily = valo) }
        )
    }
}

fun uriToFile(uri: Uri, context: Context): File? {
    return try {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val tempFile = File.createTempFile("upload_video", ".mp4", context.cacheDir)
        inputStream.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        tempFile
    } catch (e: Exception) {
        Log.e("File Conversion", "Error converting URI to File: ${e.message}")
        null
    }
}
