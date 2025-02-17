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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csaim.valorant_clutchguide.ui.theme.DarkBlueGray
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme
import com.csaim.valorant_clutchguide.ui.theme.valo

class SideScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Retrieve the selected map and agent from the previous screen
        val selectedMap = intent.getStringExtra("mapName") ?: "Unknown Map"
        val selectedAgent = intent.getStringExtra("agentName") ?: "Unknown Agent"

        setContent {
            ValorantClutchGuideTheme {
                Column(
                    modifier = Modifier
                        .background(DarkBlueGray)
                        .fillMaxSize()
                        .padding(16.dp)
                        .statusBarsPadding()
                ) {
                    // Display the map and agent at the top of the screen
                    Text(
                        text = "Map: $selectedMap",
                        fontFamily = valo,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Agent: $selectedAgent",
                        fontFamily = valo,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Attackers Card
                    Card(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                            .height(150.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        onClick = {
                            // Pass along the map, agent, and side information
                            startActivity(
                                Intent(this@SideScreen, AttackScreen::class.java).apply {
                                    putExtra("mapName", selectedMap)
                                    putExtra("agentName", selectedAgent)
                                    putExtra("side", "atkSide")
                                }
                            )
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.attackersside),
                                contentDescription = "Attackers Side",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = "Attacker's Side",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontFamily = valo,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Defenders Card
                    Card(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                            .height(150.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        onClick = {
                            // Pass along the map, agent, and side information
                            startActivity(
                                Intent(this@SideScreen, DefenseScreen::class.java).apply {
                                    putExtra("mapName", selectedMap)
                                    putExtra("agentName", selectedAgent)
                                    putExtra("side", "Defender")
                                }
                            )
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.defendersside),
                                contentDescription = "Defenders Side",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = "Defender's Side",
                                fontFamily = valo,
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Divider()


                }
            }
        }
    }
}


