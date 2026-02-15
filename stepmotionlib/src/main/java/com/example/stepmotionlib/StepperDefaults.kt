package com.example.stepmotionlib

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Contains default values used by all stepper components in the library.
 * These values can be overridden when calling stepper composables.
 */
object StepperDefaults {

    // Circle Sizes
    /**
     * Small circle size for compact steppers (32dp)
     */
    val SmallCircleSize: Dp = 32.dp

    /**
     * Medium circle size for standard steppers (36dp)
     */
    val MediumCircleSize: Dp = 36.dp

    /**
     * Large circle size for prominent steppers (40dp)
     */
    val LargeCircleSize: Dp = 40.dp

    // Typography
    /**
     * Font size for numbers/icons inside circles (16sp)
     */
    val CircleNumberFontSize: TextUnit = 16.sp

    /**
     * Small title font size (12sp)
     */
    val SmallTitleFontSize: TextUnit = 12.sp

    /**
     * Medium title font size for step labels (13sp)
     */
    val MediumTitleFontSize: TextUnit = 13.sp

    /**
     * Font size for descriptions (12sp)
     */
    val DescriptionFontSize: TextUnit = 12.sp

    // Spacing
    /**
     * Small spacing between elements (4dp)
     */
    val SmallSpacing: Dp = 4.dp

    /**
     * Medium spacing between elements (6dp)
     */
    val MediumSpacing: Dp = 6.dp

    /**
     * Large spacing between elements (8dp)
     */
    val LargeSpacing: Dp = 8.dp

    // Connector
    /**
     * Thin connector line thickness (2dp)
     */
    val ThinConnector: Dp = 2.dp

    /**
     * Medium connector line thickness (3dp)
     */
    val MediumConnector: Dp = 3.dp

    /**
     * Thick connector line thickness (4dp)
     */
    val ThickConnector: Dp = 4.dp

    // Border
    /**
     * Default border width for step indicators (2dp)
     */
    val BorderWidth: Dp = 2.dp

    // Animations
    /**
     * Duration for color change animations in milliseconds (400ms)
     */
    const val ColorAnimationDuration: Int = 400

    /**
     * Duration for scale animations in milliseconds (300ms)
     */
    const val ScaleAnimationDuration: Int = 300

    /**
     * Duration for pulse animations in milliseconds (1000ms)
     */
    const val PulseAnimationDuration: Int = 1000

    /**
     * Bouncy spring animation for playful interactions
     */
    fun <T> bouncySpring() = spring<T>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMediumLow
    )

    /**
     * Smooth spring animation for subtle transitions
     */
    fun <T> smoothSpring() = spring<T>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessLow
    )
}
