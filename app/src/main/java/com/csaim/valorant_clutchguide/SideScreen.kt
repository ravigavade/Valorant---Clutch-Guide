package com.csaim.valorant_clutchguide

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme

class SideScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ValorantClutchGuideTheme {
                Column {
                        Text(text = "Team screen view")
                        Text(text = "Attack screen")
                        Button(onClick ={
                            startActivity(Intent(this@SideScreen,AttackScreen::class.java))

                        }) { Text(text = "go to Attack Screen") }

                        Text(text = "Defense Screen")
                        Button(onClick ={
                            startActivity(Intent(this@SideScreen,DefenseScreen::class.java))

                        }) { Text(text = "go to Defence Screen") }

                }
            }
        }
    }
}

