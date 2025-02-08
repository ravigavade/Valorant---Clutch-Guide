package com.csaim.valorant_clutchguide

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme

class AgentScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ValorantClutchGuideTheme {
                val agents = listOf("Clove","Jett", "Viper", "Brimstone", "Cypher", "Reyna", )
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
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

@Composable
fun AgentCard(agentName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .width(120.dp)
            .height(120.dp), // increased height for a larger button than an iPhone app icon

        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            // Display the agent's face image. Replace the resource IDs with your actual images.
            Image(
                painter = painterResource(id = getAgentImageRes(agentName)),
                contentDescription = "$agentName face",
                modifier = Modifier.size(90.dp) // adjust image size as needed
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = agentName,
                fontSize = 18.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
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


        else -> R.drawable.placeholder // default image if none is found
    }
}
