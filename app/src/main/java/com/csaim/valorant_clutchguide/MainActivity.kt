package com.csaim.valorant_clutchguide

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    var searchTerm by remember { mutableStateOf("") }
    val context = LocalContext.current
    var drawerState by remember { mutableStateOf(false) }
    val instagram = "https://www.instagram.com/valorant_clutch_guide/"

    // This is the drawer
    ModalNavigationDrawer(
        drawerState = DrawerState(if (drawerState) DrawerValue.Open else DrawerValue.Closed),
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "Menu",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                Divider()
                Text(
                    "Youtube",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clickable { /* Handle Click */ }
                )
                Text(
                    "Instagram",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clickable {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(instagram)))
                        }

                )
                Text(
                    "About us",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clickable { /* Handle Click */ }
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Header of the home screen
                    IconButton(onClick = { drawerState = !drawerState }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                    OutlinedTextField(
                        modifier = Modifier
                            .weight(2f),
                        value = searchTerm,
                        onValueChange = { searchTerm = it },
                        label = { Text("Search term goes here") },
                    )
                    Button(
                        onClick = {
                            context.startActivity(Intent(context, SideScreen::class.java))
                        },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(text = "Go")
                    }
                }

                Divider()

                // NavController for navigating between screens
                val navController = rememberNavController()

                Row(
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .weight(1f),
                        onClick = { navController.navigate("home") }
                    ) {
                        Text("Home Screen")
                    }


                    Button(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .weight(1f),
                        onClick = { navController.navigate("details") }
                    ) {
                        Text("Details Screen")
                    }
                }

                // Navigation setup
                NavHost(navController, startDestination = "home") {
                    composable("home") { HomeScreen(navController) }
                    composable("details") { DetailsScreen(navController) }
                }
            }
        }
    )
}

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val maps = listOf("Bind", "Fracture", "Haven", "Pearl", "Split", "Lotus", "Abyss")

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(maps) { mapName ->
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    onClick = {
                        context.startActivity(Intent(context, AgentScreen::class.java))
                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
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
        Button(onClick = { navController.navigate("details") }) {
            Text("Go to Details")
        }
    }
}

@Composable
fun DetailsScreen(navController: NavController) {
    val maps = listOf("Ascent", "Breeze", "Icebox", "Sunset")
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(maps) { mapName ->
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    onClick = {
                        context.startActivity(Intent(context, AgentScreen::class.java))
                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
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
}
