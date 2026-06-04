package com.example.stepmotionlib.vertical_stepper

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.stepmotionlib.StepperDefaults
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.height

/**
 * A vertical stepper using rounded-rectangle badges instead of circles.
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
 * @param titleFontSize Font size for step labels
 * @param horizontalSpacing Spacing between the badge column and labels
 * @param verticalSpacing Spacing between connector and badge
 * @param connectorThickness Thickness of connector lines
 * @param animationDuration Duration of animations in milliseconds
 */
@Composable
fun VerticalRoundedStepper(
    steps: List<String>,
    currentStep: Int,
    activeColor: Color,
    inactiveColor: Color,
    activeTitleColor: Color,
    inactiveTitleColor: Color,
    modifier: Modifier = Modifier,
    badgeWidth: Dp = 44.dp,
    badgeHeight: Dp = 32.dp,
    cornerRadius: Dp = 10.dp,
    badgeFontSize: TextUnit = StepperDefaults.CircleNumberFontSize,
    titleFontSize: TextUnit = StepperDefaults.CircleNumberFontSize,
    horizontalSpacing: Dp = 16.dp,
    verticalSpacing: Dp = StepperDefaults.SmallSpacing,
    connectorThickness: Dp = StepperDefaults.ThinConnector,
    animationDuration: Int = StepperDefaults.ColorAnimationDuration,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(horizontal = horizontalSpacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            steps.forEachIndexed { index, _ ->
                RoundedVBadge(
                    stepNumber = (index + 1).toString(),
                    isPrevious = index < currentStep,
                    isCurrent = index == currentStep,
                    isNext = index > currentStep,
                    isEndNode = index == steps.lastIndex,
                    activeColor = activeColor,
                    inactiveColor = inactiveColor,
                    badgeWidth = badgeWidth,
                    badgeHeight = badgeHeight,
                    cornerRadius = cornerRadius,
                    badgeFontSize = badgeFontSize,
                    verticalSpacing = verticalSpacing,
                    connectorThickness = connectorThickness,
                    animationDuration = animationDuration,
                    modifier = if (index == steps.lastIndex) Modifier else Modifier.weight(1f)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            steps.forEachIndexed { index, title ->
                RoundedVLabel(
                    title = title,
                    isActive = index <= currentStep,
                    activeColor = activeTitleColor,
                    inactiveColor = inactiveTitleColor,
                    fontSize = titleFontSize,
                    isEndNode = index == steps.lastIndex,
                    animationDuration = animationDuration,
                    modifier = if (index == steps.lastIndex) Modifier else Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun RoundedVBadge(
    stepNumber: String,
    isPrevious: Boolean,
    isCurrent: Boolean,
    isNext: Boolean,
    isEndNode: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    badgeWidth: Dp,
    badgeHeight: Dp,
    cornerRadius: Dp,
    badgeFontSize: TextUnit,
    verticalSpacing: Dp,
    connectorThickness: Dp,
    animationDuration: Int,
    modifier: Modifier = Modifier
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

    val connectorFill by animateFloatAsState(
        targetValue = if (isCurrent || isPrevious) 1f else 0f,
        animationSpec = StepperDefaults.smoothSpring(),
        label = "connectorFill"
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

        if (!isEndNode) {
            Spacer(
                modifier = Modifier
                    .padding(vertical = verticalSpacing)
                    .width(connectorThickness)
                    .weight(1f)
                    .drawBehind {
                        val x = size.width / 2
                        drawLine(
                            color = inactiveColor.copy(alpha = 0.3f),
                            start = Offset(x, 0f),
                            end = Offset(x, size.height),
                            strokeWidth = size.width,
                            cap = StrokeCap.Butt
                        )
                        if (connectorFill > 0f) {
                            drawLine(
                                color = activeColor,
                                start = Offset(x, 0f),
                                end = Offset(x, size.height * connectorFill),
                                strokeWidth = size.width,
                                cap = StrokeCap.Butt
                            )
                        }
                    }
            )
        }
    }
}

@Composable
private fun RoundedVLabel(
    title: String,
    isActive: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    fontSize: TextUnit,
    isEndNode: Boolean,
    animationDuration: Int,
    modifier: Modifier = Modifier
) {
    val color by animateColorAsState(
        targetValue = if (isActive) activeColor else inactiveColor,
        animationSpec = tween(animationDuration),
        label = "labelColor"
    )
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = color,
            fontSize = fontSize,
            fontWeight = FontWeight.Medium
        )
        if (!isEndNode) Spacer(modifier = Modifier.weight(1f))
    }
}


//********************************//
//            PREVIEW            //
//******************************//

@Preview(name = "VerticalRounded – Step 1", showBackground = true, widthDp = 360, heightDp = 280)
@Composable
private fun VerticalRoundedPreview_Step1() {
    VerticalRoundedStepper(
        modifier = Modifier.fillMaxWidth().height(260.dp),
        steps = listOf("Intake", "Diagnosis", "Prescription", "Follow-up"),
        currentStep = 0,
        activeColor = Color(0xFF6366F1),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF312E81),
        inactiveTitleColor = Color(0xFF94A3B8),
        cornerRadius = 10.dp
    )
}

@Preview(name = "VerticalRounded – Step 2", showBackground = true, widthDp = 360, heightDp = 280)
@Composable
private fun VerticalRoundedPreview_Step2() {
    VerticalRoundedStepper(
        modifier = Modifier.fillMaxWidth().height(260.dp),
        steps = listOf("Intake", "Diagnosis", "Prescription", "Follow-up"),
        currentStep = 2,
        activeColor = Color(0xFF6366F1),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF312E81),
        inactiveTitleColor = Color(0xFF94A3B8),
        cornerRadius = 10.dp
    )
}

@Preview(name = "VerticalRounded – Complete", showBackground = true, widthDp = 360, heightDp = 280)
@Composable
private fun VerticalRoundedPreview_Complete() {
    VerticalRoundedStepper(
        modifier = Modifier.fillMaxWidth().height(260.dp),
        steps = listOf("Intake", "Diagnosis", "Prescription", "Follow-up"),
        currentStep = 3,
        activeColor = Color(0xFF6366F1),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF312E81),
        inactiveTitleColor = Color(0xFF94A3B8),
        cornerRadius = 10.dp
    )
}
