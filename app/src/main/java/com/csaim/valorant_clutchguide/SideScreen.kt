package com.csaim.valorant_clutchguide

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.csaim.valorant_clutchguide.ui.theme.RedPrimary
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme
import com.csaim.valorant_clutchguide.ui.theme.valo

class SideScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val selectedMap = intent.getStringExtra("mapName") ?: "Unknown Map"
        val selectedAgent = intent.getStringExtra("agentName") ?: "Unknown Agent"

        setContent {
            ValorantClutchGuideTheme {
                SideSelectionContent(selectedMap, selectedAgent)
            }
        }
    }
}

@Composable
fun SideSelectionContent(selectedMap: String, selectedAgent: String) {
    val context = LocalContext.current
    var selectedSide by remember { mutableStateOf<String?>(null) }
    var selectedSite by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(DarkBlueGray)
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
            .statusBarsPadding()
    ) {
//        Text(text = "Map: $selectedMap", fontFamily = valo, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
//        Text(text = "Agent: $selectedAgent", fontFamily = valo, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
//        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Choose Your Side", fontFamily = valo, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        SideSelectionCard("Attacker's Side", R.drawable.attackersside, selectedSide == "atkSide") {
            selectedSide = "atkSide"
            selectedSite = null // Reset site when changing side
        }
        SideSelectionCard("Defender's Side", R.drawable.defendersside, selectedSide == "defSide") {
            selectedSide = "defSide"
            selectedSite = null // Reset site when changing side
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Choose Your Site", fontFamily = valo, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        SiteSelectionCard("Site A", R.drawable.abyss, selectedSite == "siteA", selectedSide != null) {
            selectedSite = "siteA"
        }
        SiteSelectionCard("Site B", R.drawable.fade, selectedSite == "siteB", selectedSide != null) {
            selectedSite = "siteB"
        }
        SiteSelectionCard("Mid", R.drawable.cypher, selectedSite == "mid", selectedSide != null) {
            selectedSite = "mid"
        }


        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                context.startActivity(Intent(context, ContentScreen::class.java).apply {
                    putExtra("mapName", selectedMap)
                    putExtra("agentName", selectedAgent)
                    putExtra("side", selectedSide)
                    putExtra("site", selectedSite)
                })
            },
            enabled = selectedSide != null && (selectedSide == "defSide" || selectedSite != null),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
        ) {
            Text("Continue", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun SideSelectionCard(title: String, imageRes: Int, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(150.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) Color.White else Color.White.copy(alpha = 0.3f))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(painter = painterResource(imageRes), contentDescription = title, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop, alpha = if (isSelected) 1f else 0.3f)
            Text(text = title, fontFamily = valo, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SiteSelectionCard(title: String, imageRes: Int, isSelected: Boolean, isEnabled: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(120.dp)
            .clickable(enabled = isEnabled, onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = if (isEnabled) (if (isSelected) Color.White else Color.White.copy(alpha = 0.3f)) else Color.Gray.copy(alpha = 0.5f))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(painter = painterResource(imageRes), contentDescription = title, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop, alpha = if (isEnabled) (if (isSelected) 1f else 0.3f) else 0.2f)
            Text(text = title, fontFamily = valo, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
    }
}
