package com.example.stepmotion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stepmotion.ui.theme.StepMotionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StepMotionTheme {
                HorizontalStepper()
            }
        }
    }
}

@Composable
fun HorizontalStepper(modifier: Modifier = Modifier) {
    Column(modifier
        .fillMaxSize()
        .padding(top = 60.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        StepperTopBar(items = listOf(1,2,3,4,5))
        StepperBottomBar(items = listOf("Step1","Step2","Step3","Step4","Step5"))
    }
}

@Composable
fun StepperTopBar(modifier: Modifier = Modifier, items: List<Int>) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        items.forEachIndexed { index,it->
            if (index == items.lastIndex) {
                TopStepperSingleItem(text = items[index].toString(), isEndNode = true)
            } else TopStepperSingleItem(Modifier.weight(1f), text = "1")
        }
    }

}

@Composable
fun TopStepperSingleItem(modifier: Modifier = Modifier, text: String = "", isEndNode: Boolean = false) {
    Row(modifier = modifier,verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Card(modifier = Modifier.size(50.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray)) {
            Box(Modifier.fillMaxSize()) {
                Text(text = text,modifier = Modifier.align(Alignment.Center), color = Color.White, fontSize = 18.sp)
            }
        }
        if (!isEndNode) {
            Spacer(modifier = Modifier
                .weight(1f)
                .height(2.dp)
                .background(Color.DarkGray))
        }
    }
}


@Composable
fun StepperBottomBar(modifier: Modifier = Modifier, items: List<String>) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)) {
        items.forEachIndexed { index,it->
            if (index == items.lastIndex) {
                BottomStepperSingleItem(text = items[index].toString(), isEndNode = true)
            } else  BottomStepperSingleItem(modifier.weight(1f), text = items[index].toString())
        }
    }
}


@Composable
fun BottomStepperSingleItem(modifier: Modifier = Modifier, text: String = "", isEndNode: Boolean = false) {
    Row(modifier = modifier,verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Text(text = text, color = Color.DarkGray, fontSize = 16.sp)
        if (!isEndNode) {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
