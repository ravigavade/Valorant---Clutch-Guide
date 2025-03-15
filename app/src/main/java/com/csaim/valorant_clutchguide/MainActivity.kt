package com.csaim.valorant_clutchguide

import android.content.Intent
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.csaim.valorant_clutchguide.ui.theme.DarkBlueGray
import com.csaim.valorant_clutchguide.ui.theme.DarkBlueGray2
import com.csaim.valorant_clutchguide.ui.theme.DarkRed
import com.csaim.valorant_clutchguide.ui.theme.MuchDarkBlueGray
import com.csaim.valorant_clutchguide.ui.theme.RedPrimary
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme
import com.csaim.valorant_clutchguide.ui.theme.valo

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        enableEdgeToEdge()
        setContent {
            ValorantClutchGuideTheme {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Change the status bar color to MuchDarkBlueGray
                    window.statusBarColor = DarkBlueGray.toArgb()

                    // Set the status bar text and icon color to white
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() // This keeps the icons/text white

                    // Ensure the status bar remains visible
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                } else {
                    // For older versions, you can just set the status bar color
                    window.statusBarColor = DarkBlueGray.toArgb()
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
    val instagram = "https://www.instagram.com/_valo_hub_/"
    val yt = "https://www.youtube.com/channel/UC1Smq7r743syyVgEzzVDHzQ"
    val PrivacyPolicy= "https://github.com/ravigavade/ValoHub-Privacy-Policy/blob/main/Privacy%20Policy"

    // This is the drawer
    ModalNavigationDrawer(
        drawerState = DrawerState(if (drawerState) DrawerValue.Open else DrawerValue.Closed),
        modifier = Modifier.background(Color.Gray),
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(250.dp).statusBarsPadding().padding(bottom = 16.dp),
                drawerContainerColor = DarkBlueGray,
                drawerContentColor = Color.White,
                drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .padding(8.dp)
                        .background(DarkBlueGray2)
                        .fillMaxSize()
                ) {
                    Text(
                        "ValoHub",
                        fontFamily = valo,
                        fontSize = 30.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)

                    )
                    Divider()

                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()

                            .clickable {  val intent = Intent(context, CommunityPosts::class.java)
                                context.startActivity(intent)
                            }
                    ) {

                        Card(
                            shape = RoundedCornerShape(5.dp),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.nade),
                                contentDescription = "About Us Icon",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                        Text(
                            "Community",
                            fontFamily = valo,
                            fontSize = 18.sp,
                            color = Color.White,

//                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()

                            .clickable {  val intent = Intent(context, PatchNotes::class.java)
                                context.startActivity(intent)
                            }
                    ) {
                        Card(
                            shape = RoundedCornerShape(5.dp),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.cypher),
                                contentDescription = "About Us Icon",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                        Text(
                            "Patch notes",
                            fontSize = 18.sp,
                            color = Color.White,

                            fontFamily = valo,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()


                            .clickable {  val intent = Intent(context, VideoUploadActivity::class.java)
                                context.startActivity(intent)
                            }
                    ) {

                            Image(
                                painter = painterResource(id = R.drawable.postvideos),
                                contentDescription = "Upload Clips Icon",
                                modifier = Modifier.size(50.dp)
                            )

                        Text(
                            "Upload Clip",
                            color = Color.White,

                            fontSize = 18.sp,
                            fontFamily = valo,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    Divider()



                    // Menu items
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()


                            .clickable {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(yt)))
                            }
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
                            color = Color.White,

                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()


                            .clickable {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(instagram)))
                            }
                    ) {

                            Image(
                                painter = painterResource(id = R.drawable.insta),
                                contentDescription = "Instagram Icon",
                                modifier = Modifier.size(50.dp)
                            )

                        Text(
                            "Instagram",
                            color = Color.White,

                            fontSize = 18.sp,
                            fontFamily = valo,
                            modifier = Modifier.padding(16.dp)
                        )
                    }


                    //privacy policy
//                    Row(
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .fillMaxWidth()
//                            .clickable {
//                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(PrivacyPolicy)))
//                            }
//
////
//                    ) {
//                            Image(
//                                painter = painterResource(id = R.drawable.privacy),
//                                contentDescription = "Privacy Policy",
//                                modifier = Modifier.size(50.dp)
//                            )
//                        Text(
//                            "Policy",
//                            fontFamily = valo,
//                            fontSize = 18.sp,
//                            color = Color.White,
//
////                            fontWeight = FontWeight.Bold,
//                            modifier = Modifier.padding(16.dp)
//                        )
//                    }


                    //contact us
//                    Row(
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .fillMaxWidth()
//
//                            .clickable {  val intent = Intent(context, ContactUs()::class.java)
//                                context.startActivity(intent)
//                            }
//                    ) {
//
//                            Image(
//                                painter = painterResource(id = R.drawable.help_99dp_ff4654),
//                                contentDescription = "App Icon",
//                                modifier = Modifier
//                                    .size(50.dp)
//                                    .clip(CircleShape)
//                            )
//
//                        Text(
//                            "Contact us",
//                            fontSize = 18.sp,
//                            color = Color.White,
//
//                            fontFamily = valo,
//                            modifier = Modifier.padding(16.dp)
//                        )
//                    }




                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()

                            .clickable {  val intent = Intent(context, AboutScreen::class.java)
                                context.startActivity(intent)
                            }
                    ) {
                        Card(
                            shape = CircleShape,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "App Icon",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                            )
                        }
                        Text(
                            "About us",
                            fontSize = 18.sp,
                            color = Color.White,

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
                    .fillMaxSize()
                    .background(Color.White)
            ) {

                Column(
                    modifier = Modifier
                        .background(DarkBlueGray) // Set the dark background
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
                            modifier = Modifier
                                .padding(top = 10.dp),

                            shape = RoundedCornerShape(50.dp),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "App Icon",
                                modifier = Modifier
                                    .size(50.dp)
                            )
                        }
                    }

                    val navController = rememberNavController()
                    var selectedTab by remember { mutableStateOf("home") }

                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(DarkBlueGray)
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


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .weight(1f)
                            .background(DarkBlueGray)
                    ){

                        // Navigation setup
                        NavHost(navController, startDestination = "home") {
                            composable("home") { ActiveMaps(navController) }
                            composable("details") { NonActiveMaps(navController) }

                        }

                    }


                    //the bottom bar hahahahhaha
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(95.dp)
                            .background(DarkBlueGray),
                        contentAlignment = Alignment.Center
                    ){
                        DropDownDemo()
                    }



                }//


            }


        }
    )
}

@Composable
fun ActiveMaps(navController: NavController) {
    val context = LocalContext.current
    val maps = listOf("Abyss","Pearl","Fracture","Lotus","Split", "Haven", "bind")

    Column(
        modifier = Modifier
            .background(MuchDarkBlueGray)
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
                                    "Abyss" -> R.drawable.abyss
                                    "Pearl" -> R.drawable.pearl
                                    "Fracture" -> R.drawable.fracture
                                    "Split" -> R.drawable.split
                                    "bind" -> R.drawable.bind
                                    "Haven" -> R.drawable.haven
                                    "Lotus" -> R.drawable.lotus
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
    val maps = listOf("Breeze","Icebox","Ascent", "Sunset")
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .background(MuchDarkBlueGray)
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
//                        val intent = Intent(context, AgentScreen::class.java).apply {
//                            putExtra("mapName", mapName)
//                        }
//                        context.startActivity(intent)
                        Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
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

@Composable
fun DropDownDemo() {

    val isMapDropDownExpanded = remember { mutableStateOf(false) }
    val isAgentDropDownExpanded = remember { mutableStateOf(false) }
    val isSideExpanded = remember { mutableStateOf(false) }
    val isSiteExpanded = remember { mutableStateOf(false) }

    val itemPosition = remember { mutableStateOf(0) }
    val itemPosition1 = remember { mutableStateOf(0) }
    val itemPosition3 = remember { mutableStateOf(0) }
    val itemPosition4 = remember { mutableStateOf(0) }

    val maps = listOf("Abyss", "Pearl", "Fracture", "Split", "Haven", "Lotus", "bind")
    val agents = listOf("Brimstone", "Viper", "Omen", "Phoenix", "Jett", "Sage", "Breach", "Cypher", "Sova", "Raze", "kj", "Reyna", "Skye", "Yoru", "Astra", "KAY/O")
    val sides = listOf("atkSide", "defSide")
    val sites = listOf("siteA", "siteB")


    //map
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlueGray)
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
//                .background(Color.Green)
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,

        )
        {

            // Maps Dropdown
            Box{

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                        isMapDropDownExpanded.value = !isMapDropDownExpanded.value
                    }
                ) {
                    Text(
                        text = maps[itemPosition.value],
                        fontFamily = valo,
                        color = Color.White
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                        contentDescription = "Map Dropdown Icon",
                        tint = RedPrimary
                    )
                }

                DropdownMenu(
                    modifier = Modifier
                        .height(300.dp)
                        .background(MuchDarkBlueGray),
                    expanded = isMapDropDownExpanded.value,
                    onDismissRequest = { isMapDropDownExpanded.value = false },
                ) {
                    maps.forEachIndexed { index, agentname ->
                        DropdownMenuItem(text = {
                            Text(
                                text = agentname,
                                fontFamily = valo,
                                color = Color.White
                            )
        //                    Divider()
                        },
                            onClick = {
                                isMapDropDownExpanded.value = false
                                itemPosition.value = index
                            })
                    }
                }

            }



            // Agents Dropdown
            Box{


                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        isAgentDropDownExpanded.value = !isAgentDropDownExpanded.value
                    }
                ) {
                    Text(
                        text = agents[itemPosition1.value],
                        fontFamily = valo,
                        color = Color.White
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                        contentDescription = "Agent Dropdown Icon",
                        tint = RedPrimary
                    )
                }

                DropdownMenu(
                    modifier = Modifier
                        .height(300.dp)
                        .background(MuchDarkBlueGray),
                    expanded = isAgentDropDownExpanded.value,
                    onDismissRequest = { isAgentDropDownExpanded.value = false }
                ) {
                    agents.forEachIndexed { index, agentname ->
                        DropdownMenuItem(
        //                    modifier = Modifier.background(MuchDarkBlueGray),
                            text = {
                            Text(
                                text = agentname,
                                fontFamily = valo,
                                color = Color.White
                            )
                        },
                            onClick = {
                                isAgentDropDownExpanded.value = false
                                itemPosition1.value = index
                            })
                    }
                }
            }


            //side dropdown
            Box{


                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        isSideExpanded.value = !isSideExpanded.value
                    }
                ) {
                    Text(
                        text = sides[itemPosition3.value],
                        fontFamily = valo,
                        color = Color.White
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                        contentDescription = "Side Dropdown Icon",
                        tint = RedPrimary
                    )
                }
                DropdownMenu(
                    modifier = Modifier
                        .height(120.dp)
                        .background(MuchDarkBlueGray),
                    expanded = isSideExpanded.value,
                    onDismissRequest = { isSideExpanded.value = false }
                ) {

                    sides.forEachIndexed { index, sidename ->
                        DropdownMenuItem(
        //                    modifier = Modifier.background(MuchDarkBlueGray),
                            text = {
                                Text(
                                    text = sidename,
                                    fontFamily = valo,
                                    color = Color.White
                                )
                            },
                            onClick = {
                                isSideExpanded.value = false
                                itemPosition3.value = index
                            })
                    }
                }
            }




            //site dropdown
            Box{

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        isSiteExpanded.value = !isSiteExpanded.value
                    }
                ) {

                    Text(
                        text = sites[itemPosition4.value],
                        fontFamily = valo,
                        color = Color.White
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                        contentDescription = "Site Dropdown Icon",
                        tint = RedPrimary // Set the tint color to white
                    )
                }

                DropdownMenu(
                    modifier = Modifier
                        .height(120.dp)
                        .background(MuchDarkBlueGray),
                    expanded = isSiteExpanded.value,
                    onDismissRequest = { isSiteExpanded.value = false }
                ) {
                    sites.forEachIndexed { index, sitename ->
                        DropdownMenuItem(
        //                    modifier = Modifier.background(MuchDarkBlueGray),
                            text = {
                                Text(
                                    text = sitename,
                                    fontFamily = valo,
                                    color = Color.White
                                )
                            },
                            onClick = {
                                isSiteExpanded.value = false
                                itemPosition4.value = index
                            })
                    }
                }


            }

        }


        //quick search btn
            val context = LocalContext.current
            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 5.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(5.dp), // This sets the rounded corners
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkRed,
                    contentColor = Color.White
                ),
                onClick = {
                    val intent = Intent(context, ContentScreen::class.java).apply {
                        putExtra("mapName", maps[itemPosition.value])
                        putExtra("agentName", agents[itemPosition1.value])
                        putExtra("side", sides[itemPosition3.value])
                        putExtra("site", sites[itemPosition4.value])
                    }
                    context.startActivity(intent)
//                    Toast.makeText(context,
//                        "MAP: ${maps[itemPosition.value]}" +
//                        "AGENT: ${agents[itemPosition1.value]}" +
//                        "SIDE: ${sides[itemPosition3.value]}" +
//                        "SITE: ${sites[itemPosition4.value]}",
//
//                        Toast.LENGTH_SHORT).show()

                }
            )

            {
                Text(
                    "quick search",
                    fontFamily = valo
                )
            }

    }


}



