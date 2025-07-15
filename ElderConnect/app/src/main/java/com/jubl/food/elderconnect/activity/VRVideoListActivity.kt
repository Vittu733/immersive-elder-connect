package com.jubl.food.elderconnect.activity


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jubl.food.elderconnect.R
import com.jubl.food.elderconnect.YouTubeVideo
import com.jubl.food.elderconnect.ui.theme.ElderConnectTheme

class VRVideoListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val videoList = listOf(
            YouTubeVideo("Kedarnath Temple", "OYiHRowaw2U"),
            YouTubeVideo("Kasi Temple", "gHq3Rm5vwGo"),
            YouTubeVideo("Underwater Life", "eKumVFvGHFA")
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
                            "VR Videos",
                            style = TextStyle(
                                fontSize = 40.sp,
                                lineHeight = 40.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF313D5B),
                            )
                        )
                    }
                    VideoList(videos = videoList, context = this@VRVideoListActivity)
                }
            }
        }
    }
}

@Composable
fun VideoList(videos: List<YouTubeVideo>, context: Context) {
    HorizontalDivider(thickness = 1.dp, color = Color.LightGray) // Add divider
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        items(videos) { video ->
            VideoListItem(video = video, context = context)
        }
    }
}

@Composable
fun VideoListItem(video: YouTubeVideo, context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // Open YouTube with the video ID
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=${video.videoId}")
                )
                context.startActivity(intent)
            },
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Replace with a thumbnail image for the video (assumes a thumbnail URL in the video object)
            Image(
                painter = painterResource(R.drawable.video_icon),
                contentDescription = "Video Thumbnail",
                modifier = Modifier
                    .size(50.dp) // Keep the size consistent
                    .padding(end = 16.dp)
            )
            Column(
                modifier = Modifier.weight(1f) // Allow the text to take remaining space
            ) {
                Text(
                    text = video.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold, // Bold title
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis // Handle overflow
                )
            }
        }
    }
}
