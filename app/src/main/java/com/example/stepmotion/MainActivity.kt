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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stepmotion.ui.theme.StepMotionTheme
import com.example.stepmotionlib.horizontal_steppers.HorizontalAnimatedStepper
import com.example.stepmotionlib.horizontal_steppers.HorizontalIconStepper
import com.example.stepmotionlib.horizontal_steppers.HorizontalNumberedStepper
import com.example.stepmotionlib.horizontal_steppers.HorizontalProgressBarStepper
import com.example.stepmotionlib.horizontal_steppers.HorizontalRoundedStepper
import com.example.stepmotionlib.horizontal_steppers.HorizontalStepper
import com.example.stepmotionlib.vertical_stepper.VerticalCardStepper
import com.example.stepmotionlib.vertical_stepper.VerticalIconStepper
import com.example.stepmotionlib.vertical_stepper.VerticalNumberedStepper
import com.example.stepmotionlib.vertical_stepper.VerticalProgressBarStepper
import com.example.stepmotionlib.vertical_stepper.VerticalRoundedStepper
import com.example.stepmotionlib.vertical_stepper.VerticalSimpleStepper

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
                            Color.White
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

                    // 1. Horizontal Simple Stepper — Default Configuration
                    StepperSection(
                        title = "Horizontal Simple - Default",
                        subtitle = "Clean & minimal step indicator with new API",
                        accentColor = Color(0xFF3B82F6),
                        stepCount = 5,
                    ) { selectedIndex ->
                        HorizontalStepper(
                            modifier = Modifier.fillMaxWidth(),
                            steps = listOf("Details", "Address", "Payment", "Review", "Done"),
                            currentStep = selectedIndex,
                            activeColor = Color(0xFF3B82F6),
                            inactiveColor = Color(0xFFCBD5E1),
                            activeTitleColor = Color(0xFF1E40AF),
                            inactiveTitleColor = Color(0xFF94A3B8)
                        )
                    }

                    SectionDivider()

                    // 2. Horizontal Simple Stepper — Large & Custom
                    StepperSection(
                        title = "Horizontal Simple - Large Custom",
                        subtitle = "Larger circles, custom spacing & thick connectors",
                        accentColor = Color(0xFF8B5CF6),
                        stepCount = 3,
                    ) { selectedIndex ->
                        HorizontalStepper(
                            modifier = Modifier.fillMaxWidth(),
                            steps = listOf("Start", "Process", "Finish"),
                            currentStep = selectedIndex,
                            activeColor = Color(0xFF8B5CF6),
                            inactiveColor = Color(0xFFCBD5E1),
                            activeTitleColor = Color(0xFF6B21A8),
                            inactiveTitleColor = Color(0xFF94A3B8),
                            // Custom sizes
                            circleSize = 42.dp,
                            titleFontSize = 16.sp,
                            spacing = 8.dp,
                            connectorThickness = 4.dp
                        )
                    }

                    SectionDivider()

                    // 3. Horizontal Animated Stepper — Default
                    StepperSection(
                        title = "Horizontal Animated - Default",
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

                    // 4. Horizontal Animated Stepper — Custom Fast Animation
                    StepperSection(
                        title = "Horizontal Animated - Fast & Compact",
                        subtitle = "Smaller circles, faster animations, no pulse",
                        accentColor = Color(0xFFEF4444),
                        stepCount = 4,
                    ) { selectedIndex ->
                        HorizontalAnimatedStepper(
                            modifier = Modifier.fillMaxWidth(),
                            steps = listOf("Login", "Verify", "Setup", "Done"),
                            currentStep = selectedIndex,
                            activeColor = Color(0xFFEF4444),
                            inactiveColor = Color(0xFFCBD5E1),
                            activeTitleColor = Color(0xFF991B1B),
                            inactiveTitleColor = Color(0xFF94A3B8),
                            // Custom configuration
                            circleSize = 32.dp,
                            titleFontSize = 11.sp,
                            spacing = 4.dp,
                            animationDuration = 250,
                            enablePulseAnimation = false
                        )
                    }

                    SectionDivider()

                    // 5. Vertical Simple Stepper — Default
                    StepperSection(
                        title = "Vertical Simple - Default",
                        subtitle = "Classic vertical timeline layout",
                        accentColor = Color(0xFFF59E0B),
                        stepCount = 5,
                    ) { selectedIndex ->
                        VerticalSimpleStepper(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp),
                            steps = listOf(
                                "Select Service",
                                "Choose Provider",
                                "Pick Date & Time",
                                "Confirm Details",
                                "Booking Complete",
                            ),
                            currentStep = selectedIndex,
                            activeColor = Color(0xFFF59E0B),
                            inactiveColor = Color(0xFFE2E8F0),
                            activeTitleColor = Color(0xFF92400E),
                            inactiveTitleColor = Color(0xFF94A3B8)
                        )
                    }

                    SectionDivider()

                    // 6. Vertical Simple Stepper — Large & Spacious
                    StepperSection(
                        title = "Vertical Simple - Large & Spacious",
                        subtitle = "Bigger circles, larger fonts, more spacing",
                        accentColor = Color(0xFF06B6D4),
                        stepCount = 4,
                    ) { selectedIndex ->
                        VerticalSimpleStepper(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp),
                            steps = listOf(
                                "Design",
                                "Develop",
                                "Test",
                                "Deploy",
                            ),
                            currentStep = selectedIndex,
                            activeColor = Color(0xFF06B6D4),
                            inactiveColor = Color(0xFFE2E8F0),
                            activeTitleColor = Color(0xFF164E63),
                            inactiveTitleColor = Color(0xFF94A3B8),
                            // Custom sizes
                            circleSize = 44.dp,
                            circleFontSize = 20.sp,
                            titleFontSize = 18.sp,
                            verticalSpacing = 8.dp,
                            connectorThickness = 3.dp
                        )
                    }

                    SectionDivider()

                    // 7. Vertical Card Stepper — Default
                    StepperSection(
                        title = "Vertical Card - Default",
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
                            activeCardBackgroundColor = Color(0xFFF5F3FF),
                            cardBackgroundColor = Color(0xFFFFFFFF),
                        )
                    }

                    SectionDivider()

                    // 8. Vertical Card Stepper — Custom Compact Style
                    StepperSection(
                        title = "Vertical Card - Compact Custom",
                        subtitle = "Smaller cards, tight spacing, custom styling",
                        accentColor = Color(0xFFEC4899),
                        stepCount = 3,
                    ) { selectedIndex ->
                        VerticalCardStepper(
                            modifier = Modifier.fillMaxWidth(),
                            steps = listOf(
                                "Upload",
                                "Process",
                                "Complete",
                            ),
                            descriptions = listOf(
                                "Select and upload your files",
                                "Processing your documents",
                                "All done! Download your results",
                            ),
                            currentStep = selectedIndex,
                            activeColor = Color(0xFFEC4899),
                            inactiveColor = Color(0xFF94A3B8),
                            activeCardBackgroundColor = Color(0xFFFDF2F8),
                            cardBackgroundColor = Color(0xFFFFFFFF),
                            // Custom styling
                            circleSize = 32.dp,
                            circleFontSize = 14.sp,
                            titleFontSize = 14.sp,
                            descriptionFontSize = 11.sp,
                            cardRadius = 8.dp,
                            cardPadding = 10.dp,
                            cardSpacing = 6.dp,
                            connectorThickness = 2.dp,
                            accentBorderWidth = 3.dp
                        )
                    }

                    SectionDivider()

                    // 9. Horizontal Numbered Stepper — Default (NFT/flow style)
                    StepperSection(
                        title = "Horizontal Numbered - Default",
                        subtitle = "Outlined inactive circles, filled active, outer ring on current",
                        accentColor = Color(0xFF7C3AED),
                        stepCount = 4,
                    ) { selectedIndex ->
                        HorizontalNumberedStepper(
                            modifier = Modifier.fillMaxWidth(),
                            steps = listOf("Details", "Review", "Payment", "Confirm"),
                            currentStep = selectedIndex,
                            activeColor = Color(0xFF7C3AED),
                            inactiveColor = Color(0xFFCBD5E1),
                            activeTitleColor = Color(0xFF4C1D95),
                            inactiveTitleColor = Color(0xFF94A3B8)
                        )
                    }

                    SectionDivider()

                    // 10. Vertical Numbered Stepper — Default
                    StepperSection(
                        title = "Vertical Numbered - Default",
                        subtitle = "Outlined inactive circles with outer ring on current step",
                        accentColor = Color(0xFF0EA5E9),
                        stepCount = 4,
                    ) { selectedIndex ->
                        VerticalNumberedStepper(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp),
                            steps = listOf("Setup", "Configure", "Preview", "Launch"),
                            currentStep = selectedIndex,
                            activeColor = Color(0xFF0EA5E9),
                            inactiveColor = Color(0xFFCBD5E1),
                            activeTitleColor = Color(0xFF0C4A6E),
                            inactiveTitleColor = Color(0xFF94A3B8)
                        )
                    }

                    SectionDivider()

                    // 11. Horizontal Icon Stepper — Custom icons per step
                    StepperSection(
                        title = "Horizontal Icon - Custom Icons",
                        subtitle = "Per-step icons: outlined when inactive, white when active",
                        accentColor = Color(0xFFF59E0B),
                        stepCount = 4,
                    ) { selectedIndex ->
                        HorizontalIconStepper(
                            modifier = Modifier.fillMaxWidth(),
                            steps = listOf("Profile", "Home", "Cart", "Done"),
                            icons = listOf(
                                Icons.Rounded.Person,
                                Icons.Rounded.Home,
                                Icons.Rounded.ShoppingCart,
                                Icons.Rounded.Star
                            ),
                            currentStep = selectedIndex,
                            activeColor = Color(0xFFF59E0B),
                            inactiveColor = Color(0xFFCBD5E1),
                            activeTitleColor = Color(0xFF78350F),
                            inactiveTitleColor = Color(0xFF94A3B8)
                        )
                    }

                    SectionDivider()

                    // 12. Vertical Icon Stepper — Custom icons per step
                    StepperSection(
                        title = "Vertical Icon - Custom Icons",
                        subtitle = "Vertical layout with per-step icons",
                        accentColor = Color(0xFFEC4899),
                        stepCount = 4,
                    ) { selectedIndex ->
                        VerticalIconStepper(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp),
                            steps = listOf("Account", "Address", "Wishlist", "Favourites"),
                            icons = listOf(
                                Icons.Rounded.Person,
                                Icons.Rounded.Home,
                                Icons.Rounded.Star,
                                Icons.Rounded.Favorite
                            ),
                            currentStep = selectedIndex,
                            activeColor = Color(0xFFEC4899),
                            inactiveColor = Color(0xFFCBD5E1),
                            activeTitleColor = Color(0xFF831843),
                            inactiveTitleColor = Color(0xFF94A3B8)
                        )
                    }

                    SectionDivider()

                    // 13. Horizontal Rounded Stepper — Nightingale style
                    StepperSection(
                        title = "Horizontal Rounded - Badge Style",
                        subtitle = "Rounded-rectangle badges, not fully rounded corners",
                        accentColor = Color(0xFF10B981),
                        stepCount = 4,
                    ) { selectedIndex ->
                        HorizontalRoundedStepper(
                            modifier = Modifier.fillMaxWidth(),
                            steps = listOf("Symptoms", "History", "Rx", "Done"),
                            currentStep = selectedIndex,
                            activeColor = Color(0xFF10B981),
                            inactiveColor = Color(0xFFCBD5E1),
                            activeTitleColor = Color(0xFF064E3B),
                            inactiveTitleColor = Color(0xFF94A3B8),
                            cornerRadius = 10.dp
                        )
                    }

                    SectionDivider()

                    // 14. Vertical Rounded Stepper — Nightingale style
                    StepperSection(
                        title = "Vertical Rounded - Badge Style",
                        subtitle = "Vertical layout with softly-cornered rectangular badges",
                        accentColor = Color(0xFF6366F1),
                        stepCount = 4,
                    ) { selectedIndex ->
                        VerticalRoundedStepper(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp),
                            steps = listOf("Intake", "Diagnosis", "Prescription", "Follow-up"),
                            currentStep = selectedIndex,
                            activeColor = Color(0xFF6366F1),
                            inactiveColor = Color(0xFFCBD5E1),
                            activeTitleColor = Color(0xFF312E81),
                            inactiveTitleColor = Color(0xFF94A3B8),
                            cornerRadius = 10.dp
                        )
                    }

                    SectionDivider()

                    // 15. Horizontal Progress Bar Stepper
                    StepperSection(
                        title = "Horizontal Progress Bar",
                        subtitle = "Continuous bar with dots on top, half-fill on current step",
                        accentColor = Color(0xFFEF4444),
                        stepCount = 5,
                    ) { selectedIndex ->
                        HorizontalProgressBarStepper(
                            modifier = Modifier.fillMaxWidth(),
                            steps = listOf("Start", "Design", "Build", "Test", "Ship"),
                            currentStep = selectedIndex,
                            activeColor = Color(0xFFEF4444),
                            inactiveColor = Color(0xFFCBD5E1),
                            activeTitleColor = Color(0xFF7F1D1D),
                            inactiveTitleColor = Color(0xFF94A3B8),
                            barThickness = 6.dp
                        )
                    }

                    SectionDivider()

                    // 16. Vertical Progress Bar Stepper
                    StepperSection(
                        title = "Vertical Progress Bar",
                        subtitle = "Vertical continuous bar with dots, labels to the right",
                        accentColor = Color(0xFF14B8A6),
                        stepCount = 5,
                    ) { selectedIndex ->
                        VerticalProgressBarStepper(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            steps = listOf("Order placed", "Processing", "Shipped", "Out for delivery", "Delivered"),
                            currentStep = selectedIndex,
                            activeColor = Color(0xFF14B8A6),
                            inactiveColor = Color(0xFFCBD5E1),
                            activeTitleColor = Color(0xFF134E4A),
                            inactiveTitleColor = Color(0xFF94A3B8),
                            barThickness = 6.dp
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
