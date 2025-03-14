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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csaim.valorant_clutchguide.ui.theme.DarkBlueGray
import com.csaim.valorant_clutchguide.ui.theme.RedPrimary
import com.csaim.valorant_clutchguide.ui.theme.valo

class ContactUs : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactUsScreen()
        }
    }
}

@Composable
fun ContactUsScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlueGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Page Title
        Text(
            "CONTACT US",
            fontFamily = valo,
            fontSize = 28.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Intro Text
        Text(
            "Got questions or suggestions? Reach out to us! Whether it’s feedback, support, or just to connect with the ValoHub community, we’d love to hear from you.",
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.8f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Email Section
        ContactItem(
            iconRes = R.drawable.cypher,
            title = "Email Us",
            description = "support@valohub.gg",
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:ravibramhadev.gavade@gwu.edu")
                }
                context.startActivity(intent)
            }
        )

        // Discord Section
//        ContactItem(
//            iconRes = R.drawable.cypher,
//            title = "Join Our Discord",
//            description = "Connect with the community",
//            onClick = {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.gg/valohub"))
//                context.startActivity(intent)
//            }
//        )

        // Twitter Section
//        ContactItem(
//            iconRes = R.drawable.cypher,
//            title = "Follow Us on Twitter",
//            description = "@ValoHubGG",
//            onClick = {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/ValoHubGG"))
//                context.startActivity(intent)
//            }
//        )

        // Instagram Section
        ContactItem(
            iconRes = R.drawable.insta,
            title = "Instagram",
            description = "@ValoHub",
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/_valo_hub_/"))
                context.startActivity(intent)
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Feedback Button
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:support@valohub.gg")
                    putExtra(Intent.EXTRA_SUBJECT, "ValoHub Feedback")
                }
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send Feedback", fontSize = 16.sp, color = Color.White)
        }
    }
}


@Composable
fun ContactItem(iconRes: Int, title: String, description: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(title, fontSize = 18.sp, fontFamily = valo, color = Color.White)
            Text(description, fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
        }
    }
}
