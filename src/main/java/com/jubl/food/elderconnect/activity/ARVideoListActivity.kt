package com.jubl.food.elderconnect.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jubl.food.elderconnect.YouTubeVideo
import com.jubl.food.elderconnect.ui.theme.ElderConnectTheme

class ARVideoListActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val videoList = listOf(
            YouTubeVideo("Yoga asanas", "dSwOJplkjt4"),
        )
        enableEdgeToEdge()
        setContent {
            ElderConnectTheme {
                Column(
                    modifier = Modifier
                        .background(Color(color = 0xFFFEEDF1))
                        .fillMaxSize()
                        .padding(top = 60.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "AR Videos",
                            style = TextStyle(
                                fontSize = 40.sp,
                                lineHeight = 40.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF313D5B),
                            )
                        )
                    }
                    VideoList(videos = videoList, context = this@ARVideoListActivity)
                }
            }
        }
    }
}