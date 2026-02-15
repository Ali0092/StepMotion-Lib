package com.example.stepmotionlib

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * A horizontal animated stepper component with pulse effects on the current step.
 * Features smooth animations, perfect alignment, and configurable pulse animation.
 *
 * @param steps List of step labels to display
 * @param currentStep Current step index (0-based)
 * @param activeColor Color for completed and current steps
 * @param inactiveColor Color for future steps
 * @param activeTitleColor Text color for completed and current steps
 * @param inactiveTitleColor Text color for future steps
 * @param modifier Modifier to be applied to the stepper
 * @param circleSize Size of the step indicator circles
 * @param circleFontSize Font size for numbers inside circles
 * @param titleFontSize Font size for step labels
 * @param spacing Vertical spacing between circles and text
 * @param connectorThickness Thickness of connector lines between steps
 * @param animationDuration Duration of color and scale animations in milliseconds
 * @param enablePulseAnimation Enable/disable the pulse ring animation on current step
 * @param pulseAnimationDuration Duration of the pulse animation in milliseconds
 */
@Composable
fun HorizontalAnimatedStepper(
    steps: List<String>,
    currentStep: Int,
    activeColor: Color,
    inactiveColor: Color,
    activeTitleColor: Color,
    inactiveTitleColor: Color,
    modifier: Modifier = Modifier,
    circleSize: Dp = StepperDefaults.LargeCircleSize,
    circleFontSize: TextUnit = StepperDefaults.CircleNumberFontSize,
    titleFontSize: TextUnit = StepperDefaults.SmallTitleFontSize,
    spacing: Dp = StepperDefaults.MediumSpacing,
    connectorThickness: Dp = StepperDefaults.MediumConnector,
    animationDuration: Int = StepperDefaults.ColorAnimationDuration,
    enablePulseAnimation: Boolean = true,
    pulseAnimationDuration: Int = StepperDefaults.PulseAnimationDuration,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        steps.forEachIndexed { index, title ->
            // Column containing circle and text, centered
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.wrapContentWidth()
            ) {
                // Animated circle indicator with pulse effect
                AnimatedStepIndicator(
                    stepNumber = index + 1,
                    isCompleted = index < currentStep,
                    isCurrent = index == currentStep,
                    activeColor = activeColor,
                    inactiveColor = inactiveColor,
                    circleSize = circleSize,
                    circleFontSize = circleFontSize,
                    animationDuration = animationDuration,
                    enablePulse = enablePulseAnimation,
                    pulseDuration = pulseAnimationDuration
                )

                Spacer(modifier = Modifier.height(spacing))

                // Text label
                AnimatedStepLabel(
                    title = title,
                    isActive = index <= currentStep,
                    isCurrent = index == currentStep,
                    activeColor = activeTitleColor,
                    inactiveColor = inactiveTitleColor,
                    fontSize = titleFontSize,
                    animationDuration = animationDuration
                )
            }

            // Connector line (only for non-last items)
            if (index < steps.lastIndex) {
                AnimatedConnectorLine(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.Top)
                        .padding(top = circleSize / 2 - connectorThickness / 2, start = 2.dp, end = 2.dp),
                    isCompleted = index < currentStep,
                    activeColor = activeColor,
                    inactiveColor = inactiveColor,
                    thickness = connectorThickness
                )
            }
        }
    }
}

@Composable
private fun AnimatedStepIndicator(
    stepNumber: Int,
    isCompleted: Boolean,
    isCurrent: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    circleSize: Dp,
    circleFontSize: TextUnit,
    animationDuration: Int,
    enablePulse: Boolean,
    pulseDuration: Int
) {
    val bgColor by animateColorAsState(
        targetValue = when {
            isCompleted || isCurrent -> activeColor
            else -> inactiveColor.copy(alpha = 0.3f)
        },
        animationSpec = tween(animationDuration),
        label = "indicatorBg"
    )

    val scale by animateFloatAsState(
        targetValue = if (isCurrent) 1.15f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "indicatorScale"
    )

    val checkScale by animateFloatAsState(
        targetValue = if (isCompleted) 1f else 0f,
        animationSpec = spring(
            dampingRatio = 0.6f,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "checkScale"
    )

    // Pulse ring animation for current step
    val pulseScale = remember { Animatable(1f) }
    val pulseAlpha = remember { Animatable(0f) }

    LaunchedEffect(isCurrent, enablePulse) {
        if (isCurrent && enablePulse) {
            // Launch both animations in parallel
            launch {
                pulseScale.snapTo(1f)
                pulseScale.animateTo(
                    targetValue = 1.3f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(pulseDuration, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }
            launch {
                pulseAlpha.snapTo(0.6f)
                pulseAlpha.animateTo(
                    targetValue = 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(pulseDuration, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }
        } else {
            pulseScale.snapTo(1f)
            pulseAlpha.snapTo(0f)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(circleSize)
            .drawBehind {
                if (isCurrent && enablePulse) {
                    drawCircle(
                        color = activeColor.copy(alpha = pulseAlpha.value),
                        radius = (circleSize.value / 2 * pulseScale.value).dp.toPx(),
                    )
                }
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(circleSize)
                .scale(scale)
                .clip(CircleShape)
                .drawBehind {
                    drawCircle(color = bgColor)
                }
        ) {
            if (isCompleted) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(circleSize * 0.55f)
                        .scale(checkScale)
                )
            } else {
                Text(
                    text = stepNumber.toString(),
                    color = Color.White,
                    fontSize = circleFontSize,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun AnimatedStepLabel(
    title: String,
    isActive: Boolean,
    isCurrent: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    fontSize: TextUnit,
    animationDuration: Int
) {
    val titleColor by animateColorAsState(
        targetValue = if (isActive) activeColor else inactiveColor,
        animationSpec = tween(animationDuration),
        label = "titleColor"
    )

    Text(
        text = title,
        color = titleColor,
        fontSize = fontSize,
        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
        textAlign = TextAlign.Center,
        maxLines = 2,
    )
}

@Composable
private fun AnimatedConnectorLine(
    modifier: Modifier = Modifier,
    isCompleted: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    thickness: Dp
) {
    val fillFraction by animateFloatAsState(
        targetValue = if (isCompleted) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "connectorFill"
    )

    Canvas(
        modifier = modifier.height(thickness)
    ) {
        val lineY = size.height / 2
        // Draw inactive background line
        drawLine(
            color = inactiveColor.copy(alpha = 0.3f),
            start = Offset(0f, lineY),
            end = Offset(size.width, lineY),
            strokeWidth = size.height,
            cap = StrokeCap.Round,
        )
        // Draw animated active portion
        if (fillFraction > 0f) {
            drawLine(
                color = activeColor,
                start = Offset(0f, lineY),
                end = Offset(size.width * fillFraction, lineY),
                strokeWidth = size.height,
                cap = StrokeCap.Round,
            )
        }
    }
}
