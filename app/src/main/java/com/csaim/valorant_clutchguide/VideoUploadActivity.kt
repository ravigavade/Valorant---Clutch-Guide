package com.csaim.valorant_clutchguide

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme
import com.csaim.valorant_clutchguide.ui.theme.valo
import kotlinx.coroutines.launch
import java.io.File
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.compose.ui.viewinterop.AndroidView
import com.csaim.valorant_clutchguide.ui.theme.DarkBlueGray
import com.csaim.valorant_clutchguide.ui.theme.MuchDarkBlueGray
import com.csaim.valorant_clutchguide.ui.theme.RedPrimary

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
//            .padding(16.dp)
            .background(DarkBlueGray)
            .statusBarsPadding()
    ) {
//        Button(
//            onClick = {
//                val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
//                pickVideoLauncher.launch(intent)
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Pick Video")
//        }

        Text(
            "Upload a video to Community Posts",
            fontFamily = valo, color = Color.White,
            fontSize = 30.sp,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
//        Spacer(modifier = Modifier.height(16.dp))
        Text("In this section, you can upload your Valorant clips to share with the community. Please note that all submissions go through an approval process, which may take between 6 to 12 hours. Make sure to name your clips with the display name you want to appear in the community.",
//            fontFamily = valo,
//            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(16.dp)
                .clickable { val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                    pickVideoLauncher.launch(intent)},
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MuchDarkBlueGray),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_file_upload_24), // Replace with your drawable
                    contentDescription = "Pick Video",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

//        Spacer(modifier = Modifier.height(16.dp))

        selectedVideoUri?.let {
            Text("Selected Video: ", fontFamily = valo, modifier = Modifier.padding(horizontal = 16.dp))

//            Spacer(modifier = Modifier.height(16.dp))

            // Video Preview using ExoPlayer
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = true  // Show video controls
                    }
                }
            )
        }

//        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                selectedVideoUri?.let { uri ->
                    coroutineScope.launch {
                        val success = videoManager.uploadCommunityVideo(uri, context)
                        Log.d("Video Upload", "Upload successful: $success")
                        Toast.makeText(context, "Upload successful: $success", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
            enabled = selectedVideoUri != null
        ) {
            Text("Upload Video", fontFamily = valo, color = Color.White )
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
