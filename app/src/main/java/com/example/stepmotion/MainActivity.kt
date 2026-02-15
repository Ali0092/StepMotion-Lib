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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFF8FAFC),
                                    Color(0xFFEEF2F7),
                                    Color(0xFFE2E8F0),
                                )
                            )
                        )
                        .verticalScroll(rememberScrollState())
                        .padding(top = 52.dp, bottom = 36.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header
                    Text(
                        text = "StepMotion",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1E293B),
                    )
                    Text(
                        text = "Animated Stepper Library",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF94A3B8),
                        modifier = Modifier.padding(bottom = 28.dp)
                    )

                    // 1. Horizontal Simple Stepper — Vibrant Blue
                    StepperSection(
                        title = "Horizontal Simple",
                        subtitle = "Clean & minimal step indicator",
                        accentColor = Color(0xFF3B82F6),
                        stepCount = 5,
                    ) { selectedIndex ->
                        HorizontalSimpleStepper(
                            modifier = Modifier.fillMaxWidth(),
                            countingList = listOf(1, 2, 3, 4, 5),
                            titleList = listOf("Details", "Address", "Payment", "Review", "Done"),
                            selectedItemIndex = selectedIndex,
                            nonSelectedItemColor = Color(0xFFCBD5E1),
                            selectedItemColor = Color(0xFF3B82F6),
                            nonSelectedTitleColor = Color(0xFF94A3B8),
                            selectedTitleColor = Color(0xFF1E40AF)
                        )
                    }

                    SectionDivider()

                    // 2. Horizontal Animated Stepper — Emerald
                    StepperSection(
                        title = "Horizontal Animated",
                        subtitle = "Pulse ring, bouncy checkmarks & progress fill",
                        accentColor = Color(0xFF10B981),
                        stepCount = 5,
                    ) { selectedIndex ->
                        HorizontalAnimatedStepper(
                            modifier = Modifier.fillMaxWidth(),
                            steps = listOf("Cart", "Shipping", "Payment", "Review", "Confirm"),
                            currentStep = selectedIndex,
                            activeColor = Color(0xFF10B981),
                            inactiveColor = Color(0xFFCBD5E1),
                            activeTitleColor = Color(0xFF065F46),
                            inactiveTitleColor = Color(0xFF94A3B8),
                        )
                    }

                    SectionDivider()

                    // 3. Vertical Simple Stepper — Warm Amber/Orange
                    StepperSection(
                        title = "Vertical Simple",
                        subtitle = "Classic vertical timeline layout",
                        accentColor = Color(0xFFF59E0B),
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
                                "Booking Complete",
                            ),
                            selectedItemIndex = selectedIndex,
                            nonSelectedItemColor = Color(0xFFE2E8F0),
                            selectedItemColor = Color(0xFFF59E0B),
                            nonSelectedTitleColor = Color(0xFF94A3B8),
                            selectedTitleColor = Color(0xFF92400E)
                        )
                    }

                    SectionDivider()

                    // 4. Vertical Card Stepper — Rich Purple
                    StepperSection(
                        title = "Vertical Card",
                        subtitle = "Cards with expand, crossfade & accent border",
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
                            inactiveColor = Color(0xFF94A3B8),
                            inActiveBackgroundColor = Color(0xFFF5F3FF),
                            cardBackgroundColor = Color(0xFFFFFFFF),
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun SectionDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 32.dp, vertical = 20.dp),
        thickness = 1.dp,
        color = Color(0xFFE2E8F0)
    )
}

@Composable
private fun StepperSection(
    title: String,
    subtitle: String,
    accentColor: Color,
    stepCount: Int,
    content: @Composable (Int) -> Unit,
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E293B),
        )
        Text(
            text = subtitle,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF94A3B8),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        content(selectedIndex)

        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = {
                selectedIndex = (selectedIndex + 1) % stepCount
            },
            colors = ButtonDefaults.buttonColors(containerColor = accentColor),
            shape = RoundedCornerShape(10.dp),
        ) {
            Text(
                text = "Next Step",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}
