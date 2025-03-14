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
import androidx.compose.ui.Alignment
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
import android.database.Cursor
import android.provider.OpenableColumns

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
    val videoManager = remember { VideoManager() }
    var selectedVideoUri by remember { mutableStateOf<Uri?>(null) }
    var showSizeLimitDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var isUploading by remember { mutableStateOf(false) }  // Progress tracking

    // 🔥 Fix: Move ExoPlayer to remember to avoid flickering
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }

    val pickVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                val file = uriToFile(uri, context)
                if (file != null) {
                    val fileSizeMB = file.length() / (1024 * 1024)
                    if (fileSizeMB > 50) {
                        showSizeLimitDialog = true
                    } else {
                        selectedVideoUri = uri
                        Log.d("Video Upload", "Selected video: Size: ${fileSizeMB}MB")

                        // 🔥 Fix: Reset ExoPlayer when new video is selected
                        exoPlayer.apply {
                            stop()
                            clearMediaItems()
                            setMediaItem(MediaItem.fromUri(uri))
                            prepare()
                        }
                    }
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .background(DarkBlueGray)
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
    ) {
        Text(
            "Clip Submission Page",
            fontFamily = valo, color = RedPrimary,
            fontSize = 25.sp,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )

        Text(
            "This is your space to showcase your best gameplay moments, strategic lineups, and creative plays to the Valorant community. [...]",
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(16.dp)
                .clickable {
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                    pickVideoLauncher.launch(intent)
                },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MuchDarkBlueGray),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_file_upload_24),
                    contentDescription = "Pick Video",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        selectedVideoUri?.let {
            Text("Selected Video:", fontFamily = valo, modifier = Modifier.padding(horizontal = 16.dp), color = Color.White)

            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = true
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Fix: Show progress bar when uploading
        if (isUploading) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = RedPrimary)
            }
        }

        Button(
            onClick = {
                selectedVideoUri?.let { uri ->
                    isUploading = true  // Show progress bar
                    coroutineScope.launch {
                        val success = videoManager.uploadCommunityVideo(uri, context)
                        Log.d("Video Upload", "Upload successful: $success")
                        Toast.makeText(context, "Upload successful: $success", Toast.LENGTH_SHORT).show()
                        isUploading = false  // Hide progress bar
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
            enabled = selectedVideoUri != null && !isUploading  // Disable while uploading
        ) {
            Text("Upload Video", fontFamily = valo, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }

    // Show AlertDialog if file is too large
    if (showSizeLimitDialog) {
        AlertDialog(
            onDismissRequest = { showSizeLimitDialog = false },
            confirmButton = {
                Button(onClick = { showSizeLimitDialog = false }) {
                    Text("OK", fontFamily = valo)
                }
            },
            title = { Text("File Size Too Large", fontFamily = valo) },
            text = { Text("Please select a video smaller than 50MB.", fontFamily = valo) }
        )
    }
}

fun uriToFile(uri: Uri, context: Context): File? {
    return try {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: return null

        // Extract the original file name
        val originalFileName = getFileNameFromUri(context, uri) ?: "video_upload.mp4"

        val tempFile = File(context.cacheDir, originalFileName) // Use original name
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

// Function to extract the real file name from Uri
fun getFileNameFromUri(context: Context, uri: Uri): String? {
    var name: String? = null
    val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) {
                name = it.getString(nameIndex)
            }
        }
    }
    return name
}

