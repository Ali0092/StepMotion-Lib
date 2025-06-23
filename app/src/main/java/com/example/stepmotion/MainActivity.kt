package com.example.stepmotion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stepmotion.ui.theme.StepMotionTheme
import com.example.stepmotionlib.HorizontalSimpleStepper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            StepMotionTheme {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var selectedIndex by remember { mutableIntStateOf(0) }

                    HorizontalSimpleStepper(
                        modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
                        countingList = listOf(1, 2, 3,4,5),
                        titleList = listOf("Checking1", "Checking2", "Checking3", "Checking4", "Checking5"),
                        selectedItemIndex = selectedIndex,
                        nonSelectedItemColor = Color.DarkGray,
                        selectedItemColor = Color(0xff06a2c2),
                        nonSelectedTitleColor = Color.DarkGray,
                        selectedTitleColor = Color(0xff06a2c2)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = {
                            selectedIndex = (selectedIndex + 1) % 5
                        }, modifier = Modifier.padding(vertical = 32.dp)
                    ) {
                        Text(
                            text = "Next", color = Color.Black, fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}
