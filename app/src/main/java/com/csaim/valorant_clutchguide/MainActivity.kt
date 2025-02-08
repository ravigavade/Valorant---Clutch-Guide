package com.csaim.valorant_clutchguide

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            ValorantClutchGuideTheme {
                home_screen()

            }
        }
    }
}


@Composable
fun home_screen(modifier: Modifier = Modifier) {
    ////
    var searchTerm by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .background(Color.Gray)
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = searchTerm,
                onValueChange = { searchTerm = it },
                label = { Text("Search term goes here") },
            )

            Button(
                onClick = {
                    context.startActivity(Intent(context, SideScreen::class.java))
                },
                modifier = Modifier
            ) {
                Text(text = "Go")
            }
        }

        // List of maps
        val maps = listOf("Ascent", "Bind", "Breeze", "Fracture", "Haven", "Icebox", "Pearl", "Split", "Lotus", "Sunset", "Abyss")

        // Replacing RecyclerView with LazyColumn
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
        ) {
            items(maps) { mapName -> // maps is the list of maps
                // Card view for better UI
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    onClick = {
                        context.startActivity(Intent(context, AgentScreen::class.java)) // Correct way to start the activity
                    }
                ) {
                    // Box to center the text
                    Box(
                        modifier = Modifier.fillMaxSize(), // Make the Box fill the whole Card
                        contentAlignment = Alignment.Center // Center the content inside the Box
                    ) {
                        Text(
                            text = mapName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

    }
    ///
}

