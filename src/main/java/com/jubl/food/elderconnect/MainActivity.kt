package com.jubl.food.elderconnect

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jubl.food.elderconnect.activity.ARVideoListActivity
import com.jubl.food.elderconnect.activity.CommunityHelpActivity
import com.jubl.food.elderconnect.activity.VRVideoListActivity
import com.jubl.food.elderconnect.ui.theme.ElderConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElderConnectTheme {
                TwoRowImageGrid { activityName ->
                    val intent = when (activityName) {
                        "VR_Videos" -> Intent(this, VRVideoListActivity::class.java)
                        "AR_Videos" -> Intent(
                            this,
                            ARVideoListActivity::class.java
                        )
                        else -> Intent(this, CommunityHelpActivity::class.java)
                    }
                    startActivity(intent)
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 50.dp),
                    contentAlignment = Alignment.BottomCenter,
                ) {
                    Text(
                        text = "Crafted with ❤\uFE0F in VR Siddhartha",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Gray
                        )
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 75.dp),
                contentAlignment = Alignment.TopCenter,
            ) {
                Text(
                    text = "Elder Connect",
                    style = TextStyle(
                        fontSize = 40.sp,
                        lineHeight = 40.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF313D5B),
                    )
                )
            }
        }
    }
}

@Composable
fun TwoRowImageGrid(onImageClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .background(Color(color = 0xFFFEEDF1))
            .padding(16.dp, top = 175.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ImageCard(
                imageResId = R.drawable.vr_videos,
                title = "VR Videos",
                onClick = { onImageClick("VR_Videos") }
            )
            ImageCard(
                imageResId = R.drawable.ar_videos,
                title = "AR Videos",
                onClick = { onImageClick("AR_Videos") }
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ImageCard(
                imageResId = R.drawable.community_help,
                title = "Community Help",
                onClick = { onImageClick("Community Help") }
            )
            Spacer(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
            ) // Leave an empty space for alignment
        }
    }
}

@Composable
fun ImageCard(imageResId: Int, title: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .clickable { onClick() },
    ) {
        Column(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .width(IntrinsicSize.Min)
                .background(Color.White)
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
            )
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = title, style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 12.sp,
                        fontWeight = FontWeight(700),
                        color = Color.Black,
                    )
                )
            }
        }
    }
}

