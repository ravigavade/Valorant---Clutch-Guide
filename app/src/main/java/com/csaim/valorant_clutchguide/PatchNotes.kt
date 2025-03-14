package com.csaim.valorant_clutchguide

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.csaim.valorant_clutchguide.ui.theme.DarkBlueGray
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme
import com.csaim.valorant_clutchguide.ui.theme.valo

class PatchNotes : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ValorantClutchGuideTheme {
                PatchNotesScreen()
            }
        }
    }
}

@Composable
fun PatchNotesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlueGray)
            .statusBarsPadding()
    ) {
        // WebView takes all available space above the bottom bar
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    loadUrl("https://playvalorant.com/en-us/news/game-updates")
                }
            },
            modifier = Modifier.weight(1f) // This makes WebView fill all available space
        )

        // Bottom bar (previously top bar)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black) // Optional background for visibility
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "VALOHub",
                fontFamily = valo,
                color = Color.White,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Card(
                shape = RoundedCornerShape(50.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Icon",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}

