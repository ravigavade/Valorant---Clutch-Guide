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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme
import com.csaim.valorant_clutchguide.ui.theme.valo

class SideScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ValorantClutchGuideTheme {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .background(Color(0xFFBA3A46))
                        .fillMaxSize()

                ) {

                    //Attackers
                    Card(
                        modifier = Modifier
                            .padding(16.dp)
//                            .fillMaxWidth(),
                            .height(150.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        onClick = {( startActivity(Intent(this@SideScreen,AttackScreen::class.java)))}

                    ){
                        Box(
                            modifier = Modifier.fillMaxSize(), // Make the Box fill the whole Card
                            contentAlignment = Alignment.Center // Center the content inside the Box

                        ){
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

                    //defenders
                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(150.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        onClick = {( startActivity(Intent(this@SideScreen,DefenseScreen::class.java)))}


                    ){
                        Box(
                            modifier = Modifier.fillMaxSize(), // Make the Box fill the whole Card
                            contentAlignment = Alignment.Center // Center the content inside the Box

                        ){

                            Image(
                                painter = painterResource(R.drawable.defendersside),
                                contentDescription = "Attackers Side",
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



                }
            }
        }
    }
}

