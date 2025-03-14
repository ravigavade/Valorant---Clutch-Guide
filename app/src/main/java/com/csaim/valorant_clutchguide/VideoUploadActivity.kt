package com.csaim.valorant_clutchguide

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Change the status bar color to MuchDarkBlueGray
                    window.statusBarColor = DarkBlueGray.toArgb()

                    // Set the status bar text and icon color to white
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() // This keeps the icons/text white

                    // Ensure the status bar remains visible
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                } else {
                    // For older versions, you can just set the status bar color
                    window.statusBarColor = DarkBlueGray.toArgb()
                }
                    VideoUploadScreen()

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
            .padding(top=16.dp)
            .background(DarkBlueGray)
            .verticalScroll(rememberScrollState())
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
            "Clip Submission page",
            fontFamily = valo, color = RedPrimary,
            fontSize = 25.sp,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
//        Spacer(modifier = Modifier.height(16.dp))
        Text("This is your space to showcase your best gameplay moments, strategic lineups, and creative plays to the Valorant community. Whether you’ve mastered a pixel-perfect lineup, pulled off an incredible clutch, or discovered a new strategy, this platform allows you to share your insights and contribute to the collective knowledge of players.\n" +
                "\n" +
                "To maintain high-quality content, all submissions go through a review process, which typically takes between 6 to 12 hours. Once approved, your clip will be publicly available and displayed under the name you provide. Please ensure that you name your clips appropriately, as this will be the name that appears alongside your submission in the community.\n" +
                "\n" +
                "By sharing your clips, you not only help fellow players improve but also gain recognition for your expertise. Start uploading now and be a part of the growing Valorant strategy hub!",
//            fontFamily = valo,
            color = Color.White,
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
            Text("Selected Video: ", fontFamily = valo, modifier = Modifier.padding(horizontal = 16.dp), color = Color.White)

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
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
            enabled = selectedVideoUri != null
        ) {
            Text("Upload Video", fontFamily = valo, color = Color.White )
        }
        Spacer(modifier = Modifier.height(16.dp))
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
