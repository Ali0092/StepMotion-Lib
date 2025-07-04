package com.example.stepmotion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stepmotion.ui.theme.StepMotionTheme
import com.example.stepmotionlib.HorizontalSimpleStepper
import com.example.stepmotionlib.VerticalSimpleStepper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            StepMotionTheme {
                Column(
                    modifier = Modifier.background( brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFDEDEDE), Color(0xFFA8AABC))
                    )),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var selectedIndex by remember { mutableIntStateOf(0) }

                    HorizontalSimpleStepper(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 64.dp),
                        countingList = listOf(1, 2, 3, 4, 5),
                        titleList = listOf(
                            "Step 1",
                            "Step 2",
                            "Step 3",
                            "Step 4",
                            "Step 5"
                        ),
                        selectedItemIndex = selectedIndex,
                        nonSelectedItemColor = Color.Gray,
                        selectedItemColor = Color(0xff007BFF),
                        nonSelectedTitleColor = Color.Gray,
                        selectedTitleColor = Color(0xff007BFF)
                    )

                    VerticalSimpleStepper(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(top = 32.dp),
                        countingList = listOf(1, 2, 3, 4, 5),
                        titleList = listOf(
                            "Select Service",
                            "Choose Provider",
                            "Pick Date & Time",
                            "Confirm Details",
                            "Appointment Booked",
                        ),
                        selectedItemIndex = selectedIndex,
                        nonSelectedItemColor = Color.Gray,
                        selectedItemColor = Color(0xFFFF6D00),
                        nonSelectedTitleColor = Color.Gray,
                        selectedTitleColor = Color(0xFFFF6D00)
                    )

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
