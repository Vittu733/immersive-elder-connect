package com.jubl.food.elderconnect.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jubl.food.elderconnect.ui.theme.ElderConnectTheme

class CommunityHelpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElderConnectTheme {

                TwoCards { activityName ->
                    val intent = when (activityName) {
                        "NGO" -> Intent(this, NGODetailsActivity::class.java)
                        else -> Intent(
                            this,
                            ElderNeedsActivity::class.java
                        )
                    }
                    startActivity(intent)
                }
            }
        }
    }
}



@Composable
fun TwoCards(onCardClick: (String) -> Unit) {
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
                "Community Help",
                style = TextStyle(
                    fontSize = 40f.sp,
                    lineHeight = 40.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF313D5B),
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(color = 0xFFFEEDF1)),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Card(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .clickable(onClick = { onCardClick("NGO") })
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "NGO's\nDetails",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF313D5B)
                        )
                    )
                }
            }
            Card(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .clickable(onClick = { onCardClick("Needs") })

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),

                    contentAlignment = Alignment.Center,

                    ) {
                    Text(
                        text = "Needs For Elders",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF313D5B),
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}
