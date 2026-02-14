package com.example.stepmotion

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stepmotion.ui.theme.StepMotionTheme
import com.example.stepmotionlib.HorizontalAnimatedStepper
import com.example.stepmotionlib.HorizontalSimpleStepper
import com.example.stepmotionlib.VerticalCardStepper
import com.example.stepmotionlib.VerticalSimpleStepper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StepMotionTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .verticalScroll(rememberScrollState())
                        .padding(top = 48.dp, bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 1. Horizontal Simple Stepper
                    StepperSection(
                        title = "Horizontal Simple",
                        accentColor = Color(0xFF007BFF),
                        stepCount = 5,
                    ) { selectedIndex ->
                        HorizontalSimpleStepper(
                            modifier = Modifier.fillMaxWidth(),
                            countingList = listOf(1, 2, 3, 4, 5),
                            titleList = listOf("Step 1", "Step 2", "Step 3", "Step 4", "Step 5"),
                            selectedItemIndex = selectedIndex,
                            nonSelectedItemColor = Color.Gray,
                            selectedItemColor = Color(0xFF007BFF),
                            nonSelectedTitleColor = Color.Gray,
                            selectedTitleColor = Color(0xFF007BFF)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 2. Horizontal Animated Stepper
                    StepperSection(
                        title = "Horizontal Animated",
                        accentColor = Color(0xFF10B981),
                        stepCount = 5,
                    ) { selectedIndex ->
                        HorizontalAnimatedStepper(
                            modifier = Modifier.fillMaxWidth(),
                            steps = listOf("Cart", "Address", "Payment", "Review", "Done"),
                            currentStep = selectedIndex,
                            activeColor = Color(0xFF10B981),
                            inactiveColor = Color.Gray,
                            activeTitleColor = Color(0xFF10B981),
                            inactiveTitleColor = Color.Gray,
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 3. Vertical Simple Stepper
                    StepperSection(
                        title = "Vertical Simple",
                        accentColor = Color(0xFFFF6D00),
                        stepCount = 5,
                    ) { selectedIndex ->
                        VerticalSimpleStepper(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp),
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
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 4. Vertical Card Stepper
                    StepperSection(
                        title = "Vertical Card",
                        accentColor = Color(0xFF8B5CF6),
                        stepCount = 5,
                    ) { selectedIndex ->
                        VerticalCardStepper(
                            modifier = Modifier.fillMaxWidth(),
                            steps = listOf(
                                "Create Account",
                                "Personal Info",
                                "Preferences",
                                "Verification",
                                "All Set!",
                            ),
                            descriptions = listOf(
                                "Sign up with your email and create a password",
                                "Tell us your name and contact details",
                                "Choose your notification and display preferences",
                                "Verify your email address to continue",
                                "Your account is ready to use",
                            ),
                            currentStep = selectedIndex,
                            activeColor = Color(0xFF8B5CF6),
                            inactiveColor = Color.DarkGray,
                            inActiveBackgroundColor = Color(0xFFFAFAFA),
                            cardBackgroundColor = Color(0xFFD3D3D3),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StepperSection(
    title: String,
    accentColor: Color,
    stepCount: Int,
    content: @Composable (Int) -> Unit,
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        content(selectedIndex)

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                selectedIndex = (selectedIndex + 1) % stepCount
            },
            colors = ButtonDefaults.buttonColors(containerColor = accentColor)
        ) {
            Text(
                text = "Next Step",
                color = Color.White,
                fontSize = 14.sp,
            )
        }
    }
}
