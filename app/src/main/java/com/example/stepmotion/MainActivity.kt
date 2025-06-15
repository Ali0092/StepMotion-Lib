package com.example.stepmotion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stepmotion.ui.theme.StepMotionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StepMotionTheme {
                StepMotion(modifier = Modifier.fillMaxSize().background(Color.White))
            }
        }
    }
}

@Composable
fun StepMotion(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        StepperItem("1")
        Spacer(modifier = Modifier.height(1.dp).weight(1f).background(Color.DarkGray))
        StepperItem("2")
        Spacer(modifier = Modifier.height(1.dp).weight(1f).background(Color.DarkGray))
        StepperItem("3")
    }
}

@Composable
fun StepperItem(text: String = "1") {
    Card(modifier = Modifier.size(50.dp), shape = CircleShape, colors = CardDefaults.cardColors(containerColor = Color.DarkGray)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(modifier = Modifier.align(Alignment.Center), text = text,color = Color.Black)
        }
    }

}
