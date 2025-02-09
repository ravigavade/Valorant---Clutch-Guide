package com.csaim.valorant_clutchguide

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

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
            ModalDrawerSheet() {
                Text(
                    "Valorant - Clutch Guide",
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
//                    .background(Color(0xFFFF4654)) // Use the hex code directly
                    .background(Color(0xFFBA3A46)) // Use the hex code directly
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Header of the home screen
                    IconButton(onClick = { drawerState = !drawerState }) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }
//
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                    )
                    Button(
                        onClick = {
                            context.startActivity(Intent(context, SideScreen::class.java))
                        },
                        modifier = Modifier
//                            .weight(1f)
                    ) {
                        Text(text = "Our logo")
                    }
                }



                val navController = rememberNavController()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFBA3A46)) // Use the hex code directly
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween // This adds space between the Texts
                ) {
                    Text(
                        text = "Active Maps",
                        color = Color.White,

                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .weight(1f) // Each Text gets equal space
                            .clickable {

                                navController.navigate("home")
                                       },
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Other Maps",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .weight(1f) // Each Text gets equal space
                            .clickable { navController.navigate("details") },
                        textAlign = TextAlign.Center
                    )
                }

                // Navigation setup
                NavHost(navController, startDestination = "home") {
                    composable("home") { ActiveMaps(navController) }
                    composable("details") { NonActiveMaps(navController) }
                }

                // NavController for navigating between screens



            }
        }
    )
}

@Composable
fun ActiveMaps(navController: NavController) {
    val context = LocalContext.current
    val maps = listOf("Bind", "Fracture", "Haven", "Pearl", "Split", "Lotus", "Abyss")

    Column(
        modifier = Modifier
            .background(Color(0xFFFF4654)) // Use the hex code directly
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 13.dp)
//                .background(Color.White)
        ) {
            items(maps) { mapName ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .height(150.dp),
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
                        Image(
                            painter = painterResource(
                                if(mapName == "Bind") R.drawable.bind
                                else if(mapName == "Fracture") R.drawable.fracture
                                else if(mapName == "Haven") R.drawable.haven
                                else if(mapName == "Pearl") R.drawable.pearl
                                else if(mapName == "Split") R.drawable.split
                                else if(mapName == "Lotus") R.drawable.lotus
                                else if(mapName == "Abyss") R.drawable.abyss
                                else R.drawable.cypher
                            ), // Replace with your image resource
                            contentDescription = "Card background image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop // Crop or fit the image as needed
                        )
                        Text(
                            text = mapName,
                            color = Color.White,
                            fontSize = 24.sp,
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
fun NonActiveMaps(navController: NavController) {
    val maps = listOf("Ascent", "Breeze", "Icebox", "Sunset")
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .background(Color(0xFFFF4654)) // Use the hex code directly
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 13.dp)
        ) {
            items(maps) { mapName ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .height(150.dp),
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
                        Image(
                            painter = painterResource(
                                if(mapName == "Ascent") R.drawable.ascent
                                else if(mapName == "Breeze") R.drawable.breeze
                                else if(mapName == "Icebox") R.drawable.icebox
                                else if(mapName == "Sunset") R.drawable.sunset
                                else R.drawable.cypher
                            ), // Replace with your image resource
                            contentDescription = "Card background image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop // Crop or fit the image as needed
                        )
                        Text(
                            text = mapName,
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}




