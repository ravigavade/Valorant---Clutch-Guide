package com.csaim.valorant_clutchguide

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.csaim.valorant_clutchguide.ui.theme.DarkBlueGray
import com.csaim.valorant_clutchguide.ui.theme.DarkBlueGray2
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme
import com.csaim.valorant_clutchguide.ui.theme.valo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ValorantClutchGuideTheme {
                // For API >= 23, change status bar text and icon color to white
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                home_screen()
            }
        }
    }
}

@Composable
fun home_screen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var drawerState by remember { mutableStateOf(false) }
    val instagram = "https://www.instagram.com/valorant_clutch_guide/"

    // This is the drawer
    ModalNavigationDrawer(
        drawerState = DrawerState(if (drawerState) DrawerValue.Open else DrawerValue.Closed),
        modifier = Modifier.background(Color.Gray),
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(250.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        "Valorant - Clutch Guide",
                        fontFamily = valo,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                    Divider()

                    // Menu items
                    Row(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .clickable { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(instagram))) }
                    ) {
                        Card(
                            shape = RoundedCornerShape(5.dp),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.yt),
                                contentDescription = "Youtube Icon",
                                modifier = Modifier.size(50.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text(
                            "Youtube",
                            fontSize = 18.sp,
                            fontFamily = valo,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .clickable { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(instagram))) }
                    ) {
                        Card(
                            shape = RoundedCornerShape(5.dp),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.insta),
                                contentDescription = "Instagram Icon",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                        Text(
                            "Instagram",
                            fontSize = 18.sp,
                            fontFamily = valo,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Row {
                        Card(
                            shape = RoundedCornerShape(5.dp),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.icon),
                                contentDescription = "About Us Icon",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                        Text(
                            "About us",
                            fontSize = 18.sp,
                            fontFamily = valo,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .background(Color.Black) // Set the dark background
                    .statusBarsPadding()
            ) {
                // Top Menu Bar
                Row(
                    modifier = Modifier.padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { drawerState = !drawerState }) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Card(
                        shape = RoundedCornerShape(5.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "App Icon",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }

                val navController = rememberNavController()
                var selectedTab by remember { mutableStateOf("home") }

                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black)
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Home Tab
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    selectedTab = "home"
                                    navController.navigate("home")
                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Active Maps",
                                color = Color.White,
                                fontFamily = valo,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            if (selectedTab == "home") {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(Color.White)
                                )
                            }
                        }

                        // Other Maps Tab
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    selectedTab = "details"
                                    navController.navigate("details")
                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Other Maps",
                                color = Color.White,
                                fontFamily = valo,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            if (selectedTab == "details") {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(Color.White)
                                )
                            }
                        }
                    }
                }

                // Navigation setup
                NavHost(navController, startDestination = "home") {
                    composable("home") { ActiveMaps(navController) }
                    composable("details") { NonActiveMaps(navController) }
                }
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
            .background(DarkBlueGray)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(maps) { mapName ->
                Card(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .height(150.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    onClick = {
                        // Pass the selected map to AgentScreen using an extra
                        val intent = Intent(context, AgentScreen::class.java).apply {
                            putExtra("mapName", mapName)
                        }
                        context.startActivity(intent)
                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(
                                when (mapName) {
                                    "Bind" -> R.drawable.bind
                                    "Fracture" -> R.drawable.fracture
                                    "Haven" -> R.drawable.haven
                                    "Pearl" -> R.drawable.pearl
                                    "Split" -> R.drawable.split
                                    "Lotus" -> R.drawable.lotus
                                    "Abyss" -> R.drawable.abyss
                                    else -> R.drawable.cypher
                                }
                            ),
                            contentDescription = "Card background image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = mapName,
                            fontFamily = valo,
                            color = Color.White,
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NonActiveMaps(navController: NavController) {
    val maps = listOf("Ascent", "Breeze", "Icebox", "Sunset")
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .background(DarkBlueGray2)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(maps) { mapName ->
                Card(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .height(150.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    onClick = {
                        // Pass the selected map to AgentScreen using an extra
                        val intent = Intent(context, AgentScreen::class.java).apply {
                            putExtra("mapName", mapName)
                        }
                        context.startActivity(intent)
                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(
                                when (mapName) {
                                    "Ascent" -> R.drawable.ascent
                                    "Breeze" -> R.drawable.breeze
                                    "Icebox" -> R.drawable.icebox
                                    "Sunset" -> R.drawable.sunset
                                    else -> R.drawable.cypher
                                }
                            ),
                            contentDescription = "Card background image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = mapName,
                            fontFamily = valo,
                            color = Color.White,
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}
