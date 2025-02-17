package com.csaim.valorant_clutchguide

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csaim.valorant_clutchguide.ui.theme.DarkBlueGray
import com.csaim.valorant_clutchguide.ui.theme.MuchDarkBlueGray
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme
import com.csaim.valorant_clutchguide.ui.theme.valo

class AgentScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Retrieve the selected map from MainActivity
        val selectedMap = intent.getStringExtra("mapName") ?: "Unknown Map"

        setContent {
            ValorantClutchGuideTheme {
                Column(
                    modifier = Modifier
                        .background(DarkBlueGray)
                        .statusBarsPadding()
                        .fillMaxSize()

                ) {

                    // Display the chosen map at the top
                    Text(
                        text = "Map: $selectedMap",
                        fontFamily = valo,
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    val agents = listOf("Brimstone", "Phoenix", "Sage", "Sova", "Viper", "Cypher", "Reyna", "kj", "Breach", "Omen", "Jett", "Raze", "Skye", "Yoru", "Astra", "KAYO", "Chamber", "Neon", "Fade", "Harbor", "Gekko", "Deadlock", "Iso", "Clove","Vyse","Tejo" )

                    LazyVerticalGrid(
                        modifier = Modifier.padding(horizontal = 7.dp),
                        columns = GridCells.Fixed(3)
                    ) {

                        items(agents) { agentName ->

                            AgentCard(agentName = agentName, onClick = {
                                // Pass both the map and the agent selection to SideScreen
                                startActivity(
                                    Intent(this@AgentScreen, SideScreen::class.java).apply {
                                        putExtra("mapName", selectedMap)
                                        putExtra("agentName", agentName)
                                    }
                                )
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AgentCard(agentName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 7.dp)
            .padding(bottom = 14.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MuchDarkBlueGray
        ),
        onClick = onClick,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            // Display the agent's face image.
            Image(
                painter = painterResource(id = getAgentImageRes(agentName)),
                contentDescription = "$agentName face",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = agentName,
                fontFamily = valo,
                color = Color.White,
                fontSize = 13.sp
            )
        }
    }
}

// Helper function to map agent names to drawable resource IDs.
fun getAgentImageRes(agentName: String): Int {
    return when (agentName) {
        "Viper" -> R.drawable.viper
        "Brimstone" -> R.drawable.brimstone
        "Cypher" -> R.drawable.cypher
        "Reyna" -> R.drawable.reyna
        "Clove" -> R.drawable.clove
        "Jett" -> R.drawable.jett
        "Raze" -> R.drawable.raze
        "Skye" -> R.drawable.skye
        "Yoru" -> R.drawable.yoru
        "Astra" -> R.drawable.astra
        "KAY/O" -> R.drawable.kayo
        "Chamber" -> R.drawable.chamber
        "Neon" -> R.drawable.neon
        "Fade" -> R.drawable.fade
        "Harbor" -> R.drawable.harbor
        "Gekko" -> R.drawable.gekko
        "Deadlock" -> R.drawable.deadlock
        "Iso" -> R.drawable.iso
        "Phoenix" -> R.drawable.phoenix
        "Sage" -> R.drawable.sage
        "Sova" -> R.drawable.sova
        "kj" -> R.drawable.killjoy
        "Breach" -> R.drawable.breach
        "Omen" -> R.drawable.omen
        "Vyse" -> R.drawable.vyse
        "Tejo" -> R.drawable.tejo
        else -> R.drawable.cypher // Default image if none is found
    }
}
