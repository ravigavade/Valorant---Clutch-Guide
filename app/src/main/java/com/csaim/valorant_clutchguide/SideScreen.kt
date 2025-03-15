package com.csaim.valorant_clutchguide

import android.app.Activity
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
import com.csaim.valorant_clutchguide.ui.theme.DarkRed
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

    Box( // Box to position the button at the bottom
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlueGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlueGray)

        ) {
            Column(
                modifier = Modifier
                    .weight(1f) // This makes the rest of the content scrollable
                    .verticalScroll(scrollState)
                    .statusBarsPadding()
                    .background(DarkBlueGray)
            .padding(16.dp)

            ) {
                Text(
                    text = "Choose Your Side",
                    fontFamily = valo,
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

                SideSelectionCard("Attacker's Side", R.drawable.attackersside, selectedSide == "atkSide") {
                    selectedSide = "atkSide"
                    selectedSite = null // Reset site when changing side
                }
                SideSelectionCard("Defender's Side", R.drawable.defendersside, selectedSide == "defSide") {
                    selectedSide = "defSide"
                    selectedSite = null // Reset site when changing side
                }

//                Spacer(modifier = Modifier.height(16.dp))
//                Divider(color = DarkBlueGray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Choose Your Site",
                    fontFamily = valo,
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

                SiteSelectionCard("Site A", R.drawable.sitea, selectedSite == "siteA", selectedSide != null) {
                    selectedSite = "siteA"
                }
                SiteSelectionCard("Site B", R.drawable.siteb, selectedSite == "siteB", selectedSide != null) {
                    selectedSite = "siteB"
                }

                // ✅ Only show "Site C" if the selected map is "Haven"
                if (selectedMap == "Haven") {
                    SiteSelectionCard("Site C", R.drawable.haven, selectedSite == "siteC", selectedSide != null) {
                        selectedSite = "siteC"
                    }
                }
            }

            // ✅ Button Stuck at Bottom
            Button(
                onClick = {
                    val activity = context as? Activity // Ensure the context is an Activity before calling finish
                    context.startActivity(
                        Intent(context, ContentScreen::class.java).apply {
                            putExtra("mapName", selectedMap)
                            putExtra("agentName", selectedAgent)
                            putExtra("side", selectedSide)
                            putExtra("site", selectedSite)
                        }
                    )
                },
                enabled = selectedSide != null && (selectedSide == "atkSide" && selectedSite != null || selectedSide == "defSide" && selectedSite != null), // ✅ Fixed condition
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
//                    .padding(bottom = 5.dp)

                    .padding(bottom = 16.dp), // Ensures it's above the navigation bar
                shape = RoundedCornerShape(5.dp), // This sets the rounded corners
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkRed,
                    contentColor = Color.White
                ),
            ) {
                Text(
                    "Continue",
                    fontFamily = valo,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }


        }
    }
}

//styling the above thing
//@Composable
//fun SideSelectionContent(selectedMap: String, selectedAgent: String) {
//    val context = LocalContext.current
//    var selectedSide by remember { mutableStateOf<String?>(null) }
//    var selectedSite by remember { mutableStateOf<String?>(null) }
//    val scrollState = rememberScrollState()
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(DarkBlueGray)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(DarkBlueGray)
//        ) {
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    .verticalScroll(scrollState)
//                    .statusBarsPadding()
//                    .background(DarkBlueGray)
//                    .padding(16.dp)
//            ) {
//                Text(
//                    text = "Choose Your Side",
//                    fontFamily = valo,
//                    color = Color.White,
//                    fontSize = 25.sp,
//                    fontWeight = FontWeight.Bold
//                )
//
//                SideSelectionCard(
//                    title = "Attacker's Side",
//                    imageRes = getAttackersSideImage(selectedMap),
//                    agentRes = getAgentImage(selectedAgent),
//                    isSelected = selectedSide == "atkSide"
//                ) {
//                    selectedSide = "atkSide"
//                    selectedSite = null
//                }
//
//                SideSelectionCard(
//                    title = "Defender's Side",
//                    imageRes = getDefendersSideImage(selectedMap),
//                    agentRes = getAgentImage(selectedAgent),
//                    isSelected = selectedSide == "defSide"
//                ) {
//                    selectedSide = "defSide"
//                    selectedSite = null
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(
//                    text = "Choose Your Site",
//                    fontFamily = valo,
//                    color = Color.White,
//                    fontSize = 25.sp,
//                    fontWeight = FontWeight.Bold
//                )
//
//                SiteSelectionCard("Site A", R.drawable.sitea, selectedSite == "siteA", selectedSide != null) {
//                    selectedSite = "siteA"
//                }
//                SiteSelectionCard("Site B", R.drawable.siteb, selectedSite == "siteB", selectedSide != null) {
//                    selectedSite = "siteB"
//                }
//
//                if (selectedMap == "Haven") {
//                    SiteSelectionCard("Site C", R.drawable.haven, selectedSite == "siteC", selectedSide != null) {
//                        selectedSite = "siteC"
//                    }
//                }
//            }
//
//            Button(
//                onClick = {
//                    val activity = context as? Activity
//                    context.startActivity(
//                        Intent(context, ContentScreen::class.java).apply {
//                            putExtra("mapName", selectedMap)
//                            putExtra("agentName", selectedAgent)
//                            putExtra("side", selectedSide)
//                            putExtra("site", selectedSite)
//                        }
//                    )
//                },
//                enabled = selectedSide != null && selectedSite != null,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//                    .padding(bottom = 16.dp),
//                shape = RoundedCornerShape(5.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = DarkRed,
//                    contentColor = Color.White
//                ),
//            ) {
//                Text(
//                    "Continue",
//                    fontFamily = valo,
//                    fontSize = 18.sp,
//                    color = Color.White
//                )
//            }
//        }
//    }
//}
//
//fun getAttackersSideImage(mapName: String): Int {
//    return when (mapName) {
//        "Ascent" -> R.drawable.ascent
//        "Bind" -> R.drawable.bind
//        "Haven" -> R.drawable.haven
//        "Split" -> R.drawable.split
//        "Fracture" -> R.drawable.fracture
//        "Pearl" -> R.drawable.pearl
//        "Lotus" -> R.drawable.lotus
//        "Breeze" -> R.drawable.breeze
//        "Icebox" -> R.drawable.icebox
//        "Abyss" -> R.drawable.abyss
//        else -> R.drawable.placeholder
//    }
//}
//
//fun getDefendersSideImage(mapName: String): Int {
//    return when (mapName) {
//        "Ascent" -> R.drawable.ascent
//        "Bind" -> R.drawable.bind
//        "Haven" -> R.drawable.haven
//        "Split" -> R.drawable.split
//        "Fracture" -> R.drawable.fracture
//        "Pearl" -> R.drawable.pearl
//        "Lotus" -> R.drawable.lotus
//        "Breeze" -> R.drawable.breeze
//        "Icebox" -> R.drawable.icebox
//        "Abyss" -> R.drawable.abyss
//        else -> R.drawable.placeholder
//    }
//}

fun getAgentImage(agentName: String): Int {
    return when (agentName) {
        "Brimstone" -> R.drawable.brimstone
        "Phoenix" -> R.drawable.phoenix
        "Sage" -> R.drawable.sage
        "Sova" -> R.drawable.sova
        "Viper" -> R.drawable.viper
        "Cypher" -> R.drawable.cypher
        "Reyna" -> R.drawable.reyna
        "Killjoy" -> R.drawable.killjoy
        "Breach" -> R.drawable.breach
        "Omen" -> R.drawable.omen
        "Jett" -> R.drawable.jett
        "Raze" -> R.drawable.raze
        "Skye" -> R.drawable.skye
        "Yoru" -> R.drawable.yoru
        "Astra" -> R.drawable.astra
        "KAYO" -> R.drawable.kayo
        "Chamber" -> R.drawable.chamber
        "Neon" -> R.drawable.neon
        "Fade" -> R.drawable.fade
        "Harbor" -> R.drawable.harbor
        "Gekko" -> R.drawable.gekko
        "Deadlock" -> R.drawable.deadlock
        "Iso" -> R.drawable.iso
        "Clove" -> R.drawable.clove
        "Vyse" -> R.drawable.vyse
        "Tejo" -> R.drawable.tejo
        "Waylay" -> R.drawable.waylay
        else -> R.drawable.placeholder
    }
}


@Composable
fun SideSelectionCard(title: String, imageRes: Int, agentRes: Int, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(150.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = if (isSelected) 1f else 0.3f))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = if (isSelected) 1f else 0.3f
            )

            Image(
                painter = painterResource(agentRes),
                contentDescription = "Selected Agent",
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
            )

            Text(
                text = title,
                fontFamily = valo,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
            )
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
