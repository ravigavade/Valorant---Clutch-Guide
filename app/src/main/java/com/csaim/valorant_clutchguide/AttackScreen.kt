package com.csaim.valorant_clutchguide

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csaim.valorant_clutchguide.ui.theme.DarkBlueGray
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme
import com.csaim.valorant_clutchguide.ui.theme.valo

class AttackScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Retrieve extras passed from SideScreen
        val selectedMap = intent.getStringExtra("mapName") ?: "Unknown Map"
        val selectedAgent = intent.getStringExtra("agentName") ?: "Unknown Agent"
        val selectedSide = intent.getStringExtra("side") ?: "Unknown Side"

        setContent {
            ValorantClutchGuideTheme {
                AttackScreenContent(
                    selectedMap = selectedMap,
                    selectedAgent = selectedAgent,
                    selectedSide = selectedSide
                )
            }
        }
    }
}

@Composable
fun AttackScreenContent(selectedMap: String, selectedAgent: String, selectedSide: String) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(DarkBlueGray)
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        // Header: display the selected map, agent, and side at the top
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
        Spacer(modifier = Modifier.height(16.dp))

        // Site A Card
        Card(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(150.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            onClick = {
                context.startActivity(Intent(context, ContentScreen::class.java).apply {
                    putExtra("mapName", selectedMap)
                    putExtra("agentName", selectedAgent)
                    putExtra("side", selectedSide)
                    putExtra("site", "siteA")
                })
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.abyss), // Replace with your actual image resource for Site A
                    contentDescription = "Site A",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Site A",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = valo,
                )
            }
        }

        // Site B Card
        Card(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(150.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            onClick = {
                context.startActivity(Intent(context, ContentScreen::class.java).apply {
                    putExtra("mapName", selectedMap)
                    putExtra("agentName", selectedAgent)
                    putExtra("side", selectedSide)
                    putExtra("site", "siteB")
                })
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.fade), // Replace with your actual image resource for Site B
                    contentDescription = "Site B",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Site B",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = valo,
                )
            }
        }

        // Mid Card
        Card(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(150.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            onClick = {
                context.startActivity(Intent(context, ContentScreen::class.java).apply {
                    putExtra("mapName", selectedMap)
                    putExtra("agentName", selectedAgent)
                    putExtra("side", selectedSide)
                    putExtra("site", "Mid")
                })
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.astra), // Replace with your actual image resource for Mid
                    contentDescription = "Mid",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Mid",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = valo,
                )
            }
        }
    }
}


