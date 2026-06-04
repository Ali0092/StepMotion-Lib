package com.example.stepmotionlib.horizontal_steppers

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.stepmotionlib.StepperDefaults
import androidx.compose.ui.tooling.preview.Preview

/**
 * A horizontal stepper using rounded-rectangle badges instead of circles.
 * Inactive steps show a muted filled badge, current step is full active color,
 * and completed steps display a checkmark. Corner radius is configurable —
 * not fully rounded (not a pill/circle), just softly cornered.
 *
 * @param steps List of step labels
 * @param currentStep Current step index (0-based)
 * @param activeColor Color for active and completed steps
 * @param inactiveColor Color for future steps
 * @param activeTitleColor Text color for active/completed labels
 * @param inactiveTitleColor Text color for future labels
 * @param modifier Modifier applied to the stepper
 * @param badgeWidth Width of each step badge
 * @param badgeHeight Height of each step badge
 * @param cornerRadius Corner radius of the badge (not fully rounded)
 * @param badgeFontSize Font size for numbers/check inside badges
 * @param titleFontSize Font size for step labels below badges
 * @param spacing Vertical spacing between badges and labels
 * @param connectorThickness Thickness of connector lines
 * @param animationDuration Duration of animations in milliseconds
 */
@Composable
fun HorizontalRoundedStepper(
    modifier: Modifier = Modifier,
    steps: List<String>,
    currentStep: Int,
    activeColor: Color,
    inactiveColor: Color,
    activeTitleColor: Color,
    inactiveTitleColor: Color,
    badgeWidth: Dp = 44.dp,
    badgeHeight: Dp = 32.dp,
    cornerRadius: Dp = 10.dp,
    badgeFontSize: TextUnit = StepperDefaults.CircleNumberFontSize,
    titleFontSize: TextUnit = StepperDefaults.MediumTitleFontSize,
    spacing: Dp = StepperDefaults.SmallSpacing,
    connectorThickness: Dp = StepperDefaults.ThinConnector,
    animationDuration: Int = StepperDefaults.ColorAnimationDuration,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        steps.forEachIndexed { index, title ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.wrapContentWidth()
            ) {
                RoundedHBadge(
                    stepNumber = (index + 1).toString(),
                    isPrevious = index < currentStep,
                    isCurrent = index == currentStep,
                    isNext = index > currentStep,
                    activeColor = activeColor,
                    inactiveColor = inactiveColor,
                    badgeWidth = badgeWidth,
                    badgeHeight = badgeHeight,
                    cornerRadius = cornerRadius,
                    badgeFontSize = badgeFontSize,
                    animationDuration = animationDuration
                )
                Spacer(modifier = Modifier.height(spacing))
                RoundedHLabel(
                    title = title,
                    isActive = index <= currentStep,
                    activeColor = activeTitleColor,
                    inactiveColor = inactiveTitleColor,
                    fontSize = titleFontSize,
                    animationDuration = animationDuration
                )
            }

            if (index < steps.lastIndex) {
                RoundedHConnector(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.Top)
                        .padding(
                            top = badgeHeight / 2 - connectorThickness / 2,
                            start = 4.dp,
                            end = 4.dp
                        ),
                    isPrevious = index < currentStep,
                    activeColor = activeColor,
                    inactiveColor = inactiveColor,
                    thickness = connectorThickness,
                    animationDuration = animationDuration
                )
            }
        }
    }
}

@Composable
private fun RoundedHBadge(
    stepNumber: String,
    isPrevious: Boolean,
    isCurrent: Boolean,
    isNext: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    badgeWidth: Dp,
    badgeHeight: Dp,
    cornerRadius: Dp,
    badgeFontSize: TextUnit,
    animationDuration: Int
) {
    val badgeColor by animateColorAsState(
        targetValue = when {
            isNext -> inactiveColor.copy(alpha = 0.2f)
            else -> activeColor
        },
        animationSpec = tween(animationDuration),
        label = "badgeColor"
    )

    val contentColor by animateColorAsState(
        targetValue = when {
            isNext -> inactiveColor.copy(alpha = 0.5f)
            else -> Color.White
        },
        animationSpec = tween(animationDuration),
        label = "contentColor"
    )

    val scale by animateFloatAsState(
        targetValue = if (isCurrent) 1.08f else 1f,
        animationSpec = StepperDefaults.bouncySpring(),
        label = "scale"
    )

    Card(
        modifier = Modifier
            .size(width = badgeWidth, height = badgeHeight)
            .scale(scale),
        shape = RoundedCornerShape(cornerRadius),
        colors = CardDefaults.cardColors(containerColor = badgeColor),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isCurrent) 4.dp else 0.dp
        )
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            AnimatedContent(
                targetState = isPrevious,
                transitionSpec = {
                    (fadeIn(tween(280)) + scaleIn(tween(280)))
                        .togetherWith(fadeOut(tween(280)) + scaleOut(tween(280)))
                },
                label = "badgeContent"
            ) { showCheck ->
                if (showCheck) {
                    Image(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(badgeHeight * 0.55f)
                    )
                } else {
                    Text(
                        text = stepNumber,
                        color = contentColor,
                        fontSize = badgeFontSize,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun RoundedHLabel(
    title: String,
    isActive: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    fontSize: TextUnit,
    animationDuration: Int
) {
    val color by animateColorAsState(
        targetValue = if (isActive) activeColor else inactiveColor,
        animationSpec = tween(animationDuration),
        label = "labelColor"
    )
    Text(
        text = title,
        color = color,
        fontSize = fontSize,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun RoundedHConnector(
    modifier: Modifier,
    isPrevious: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    thickness: Dp,
    animationDuration: Int
) {
    val fill by animateFloatAsState(
        targetValue = if (isPrevious) 1f else 0f,
        animationSpec = StepperDefaults.smoothSpring(),
        label = "fill"
    )
    Canvas(modifier = modifier.height(thickness)) {
        val y = size.height / 2
        drawLine(
            color = inactiveColor.copy(alpha = 0.3f),
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = size.height,
            cap = StrokeCap.Butt
        )
        if (fill > 0f) {
            drawLine(
                color = activeColor,
                start = Offset(0f, y),
                end = Offset(size.width * fill, y),
                strokeWidth = size.height,
                cap = StrokeCap.Butt
            )
        }
    }
}



//********************************//
//            PREVIEW            //
//******************************//

@Preview(name = "HorizontalRounded – Step 1", showBackground = true, widthDp = 360)
@Composable
private fun HorizontalRoundedPreview_Step1() {
    HorizontalRoundedStepper(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        steps = listOf("Symptoms", "History", "Rx", "Done"),
        currentStep = 0,
        activeColor = Color(0xFF10B981),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF064E3B),
        inactiveTitleColor = Color(0xFF94A3B8),
        cornerRadius = 10.dp
    )
}

@Preview(name = "HorizontalRounded – Step 2", showBackground = true, widthDp = 360)
@Composable
private fun HorizontalRoundedPreview_Step2() {
    HorizontalRoundedStepper(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        steps = listOf("Symptoms", "History", "Rx", "Done"),
        currentStep = 2,
        activeColor = Color(0xFF10B981),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF064E3B),
        inactiveTitleColor = Color(0xFF94A3B8),
        cornerRadius = 10.dp
    )
}

@Preview(name = "HorizontalRounded – Complete", showBackground = true, widthDp = 360)
@Composable
private fun HorizontalRoundedPreview_Complete() {
    HorizontalRoundedStepper(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        steps = listOf("Symptoms", "History", "Rx", "Done"),
        currentStep = 3,
        activeColor = Color(0xFF10B981),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF064E3B),
        inactiveTitleColor = Color(0xFF94A3B8),
        cornerRadius = 10.dp
    )
}
