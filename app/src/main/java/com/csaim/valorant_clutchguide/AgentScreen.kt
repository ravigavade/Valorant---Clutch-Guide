package com.csaim.valorant_clutchguide

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivities
import androidx.core.content.ContextCompat.startActivity
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme

class AgentScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ValorantClutchGuideTheme {
            main()

            }
        }
    }
}

@Composable
fun main(modifier: Modifier = Modifier) {
    val agents= listOf("Viper","Brimstone","Cypher","Reina","Iso", "Clove")
    val context = LocalContext.current // Get the context

    Column {

        //replancement of recycler view haha2
        LazyVerticalGrid(

            modifier = Modifier
                .padding(16.dp),
            columns = GridCells.Fixed(2),

        ) {
            items(agents){ agentName->//maps is the list of maps
                //card view for better ui
                Card(
                    modifier = Modifier
                        .padding(vertical = 8.dp)// Avoid excessive padding
//                        .fillParentMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//                    onClick = { startActivity(Intent(this@AgentScreen,SideScreen::class.java)) }
                    onClick = {
                        context.startActivity(Intent(context,SideScreen::class.java))

                    }

                ) {
                    //box to center the text
                    Box(
                        modifier = Modifier.fillMaxSize(), // Make the Box fill the whole Card
                        contentAlignment = Alignment.Center // Center the content inside the Box
                    ) {
                        Text(
                            text = agentName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

}

