package com.csaim.valorant_clutchguide

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csaim.valorant_clutchguide.ui.theme.DarkBlueGray
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme
import com.csaim.valorant_clutchguide.ui.theme.valo

class AgentScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ValorantClutchGuideTheme {
                Column(
                    modifier = Modifier
                        .background(Color.Black)
                        .statusBarsPadding()
                        .fillMaxSize()

                ) {

                    Spacer(modifier = Modifier.height(16.dp))

                    val agents = listOf("Brimstone", "Phoenix", "Sage", "Sova", "Viper", "Cypher", "Reyna", "Killjoy", "Breach", "Omen", "Jett", "Raze", "Skye", "Yoru", "Astra", "KAY/O", "Chamber", "Neon", "Fade", "Harbor", "Gekko", "Deadlock", "Iso", "Clove","Vyse","Tejo" )
                    LazyVerticalGrid(
                        modifier = Modifier
                            .padding(horizontal = 7.dp),
                        columns = GridCells.Fixed(3)
                    ) {

                        items(agents) { agentName ->

                            AgentCard(agentName = agentName, onClick = {
                                startActivity(Intent(this@AgentScreen, SideScreen::class.java))
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
//            .padding(7.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkBlueGray
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
            // Display the agent's face image. Replace the resource IDs with your actual images.
            Image(
                painter = painterResource(id = getAgentImageRes(agentName)),
                contentDescription = "$agentName face",
                modifier = Modifier
                    .fillMaxWidth()
//                    .size(90.dp) // adjust image size as needed
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = agentName,
                fontFamily = valo,
                color = Color.White,
                fontSize = 13.sp,
//                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
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
        "Killjoy" -> R.drawable.killjoy
        "Breach" -> R.drawable.breach
        "Omen" -> R.drawable.omen
        "Vyse" -> R.drawable.vyse
        "Tejo" -> R.drawable.tejo






        else -> R.drawable.cypher // default image if none is found
    }
}