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

class DefenseScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ValorantClutchGuideTheme {
                Column {
                    Text(text = "Defence Screen")
                    Button(onClick = {
                        startActivity(Intent(this@DefenseScreen,ContentScreen::class.java))
                    }) {
                        Text(text = "view content")
                    }
                }
            }
        }
    }
}
