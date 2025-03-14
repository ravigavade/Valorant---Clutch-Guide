package com.csaim.valorant_clutchguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csaim.valorant_clutchguide.ui.theme.DarkBlueGray
import com.csaim.valorant_clutchguide.ui.theme.MuchDarkBlueGray
import com.csaim.valorant_clutchguide.ui.theme.RedPrimary
import com.csaim.valorant_clutchguide.ui.theme.ValorantClutchGuideTheme
import com.csaim.valorant_clutchguide.ui.theme.valo

class AboutScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ValorantClutchGuideTheme {
                Screen()
            }
        }
    }
}

@Composable
fun Screen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlueGray)
            .padding(20.dp)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 App Title with More Spacing
        Text(
            text = "ABOUT VALOHUB",
            fontFamily = valo,
            fontSize = 28.sp,
            color = RedPrimary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 Shortened & Cleaner Introduction
        Text(
            text = "ValoHub is your go-to platform for mastering **Valorant lineups**. Find, learn, and share the best strategies to dominate every match!",
            fontFamily = valo,
            fontSize = 18.sp,
            color = Color.White,
            lineHeight = 26.sp // Improved readability
        )

        Spacer(modifier = Modifier.height(24.dp))


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MuchDarkBlueGray),
//                .padding(horizontal = 16.dp),
        ) {

        // 🔹 Key Features Section (Bullet Points for Better Readability)
        FeatureItem("Default Lineups", "Access pre-made setups for every map and agent.")
        FeatureItem("Community Uploads", "Share & discover user-submitted strategies.")
        FeatureItem("Tactical Insights", "Improve your game with expert strats.")
        }

        Spacer(modifier = Modifier.height(30.dp))
//
        // 🔹 Approval Process Note
        Text(
            text = "Your submissions go through a **6-12 hour approval process** to ensure quality.",
            fontFamily = valo,
            fontSize = 16.sp,
            color = Color.White,
            lineHeight = 24.sp,
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 Call to Action
        Text(
            text = "**Join the community and level up your gameplay!**",
            fontFamily = valo,
            fontSize = 18.sp,
            color = RedPrimary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 🔹 Feedback Button with More Space & Centered Alignments
//        Button(
//            onClick = { /* Implement feedback navigation */ },
//            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
//            shape = RoundedCornerShape(5.dp),
//            modifier = Modifier.padding(horizontal = 32.dp)
//        ) {
//            Text("Give Feedback", fontSize = 16.sp, color = Color.White)
//        }

        Spacer(modifier = Modifier.height(20.dp)) // Ensures padding at bottom
    }
}

// 🔥 **Reusable FeatureItem Component** to improve readability
@Composable
fun FeatureItem(title: String, description: String) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MuchDarkBlueGray),
        horizontalAlignment = Alignment.CenterHorizontally  // ✅ Centers all child elements horizontally
    ) {
    Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "🔹 $title",
            fontFamily = valo,
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,  // ✅ Ensures text itself is centered
            modifier = Modifier.fillMaxWidth() // ✅ Makes text take full width for proper centering
        )
        Text(
            text = description,
            fontFamily = valo,
            fontSize = 16.sp,
            color = Color.White.copy(alpha = 0.8f),
            lineHeight = 22.sp,

            textAlign = TextAlign.Center,  // ✅ Centers text inside the Text component
            modifier = Modifier.fillMaxWidth() // ✅ Ensures text fills the width so centering works
        )
        Spacer(modifier = Modifier.height(10.dp))
    }

}
