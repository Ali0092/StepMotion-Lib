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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.stepmotionlib.StepperDefaults
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.height

/**
 * A vertical stepper styled as a continuous progress bar with step dots placed along it.
 * The bar runs vertically on the left, fills progressively, and labels appear to the right.
 *
 * @param steps List of step labels
 * @param currentStep Current step index (0-based)
 * @param activeColor Color for the filled bar and active/completed dots
 * @param inactiveColor Color for the unfilled bar and future dots
 * @param activeTitleColor Text color for active/completed labels
 * @param inactiveTitleColor Text color for future labels
 * @param modifier Modifier applied to the stepper
 * @param dotSize Diameter of each step dot
 * @param barThickness Width/thickness of the vertical progress bar
 * @param dotFontSize Font size for numbers inside dots
 * @param titleFontSize Font size for step labels
 * @param horizontalSpacing Spacing between the bar column and labels
 * @param animationDuration Duration of animations in milliseconds
 */
@Composable
fun VerticalProgressBarStepper(
    steps: List<String>,
    currentStep: Int,
    activeColor: Color,
    inactiveColor: Color,
    activeTitleColor: Color,
    inactiveTitleColor: Color,
    modifier: Modifier = Modifier,
    dotSize: Dp = StepperDefaults.SmallCircleSize,
    barThickness: Dp = 6.dp,
    dotFontSize: TextUnit = StepperDefaults.CircleNumberFontSize,
    titleFontSize: TextUnit = StepperDefaults.CircleNumberFontSize,
    horizontalSpacing: Dp = 16.dp,
    animationDuration: Int = StepperDefaults.ColorAnimationDuration,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        // Left column: bar + dots
        Column(
            modifier = Modifier.padding(horizontal = horizontalSpacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            steps.forEachIndexed { index, _ ->
                VProgressDot(
                    stepNumber = (index + 1).toString(),
                    isPrevious = index < currentStep,
                    isCurrent = index == currentStep,
                    isNext = index > currentStep,
                    isEndNode = index == steps.lastIndex,
                    activeColor = activeColor,
                    inactiveColor = inactiveColor,
                    dotSize = dotSize,
                    barThickness = barThickness,
                    dotFontSize = dotFontSize,
                    animationDuration = animationDuration,
                    modifier = if (index == steps.lastIndex) Modifier else Modifier.weight(1f)
                )
            }
        }

        // Right column: labels
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            steps.forEachIndexed { index, title ->
                VProgressLabel(
                    title = title,
                    isActive = index <= currentStep,
                    isCurrent = index == currentStep,
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
private fun VProgressDot(
    stepNumber: String,
    isPrevious: Boolean,
    isCurrent: Boolean,
    isNext: Boolean,
    isEndNode: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    dotSize: Dp,
    barThickness: Dp,
    dotFontSize: TextUnit,
    animationDuration: Int,
    modifier: Modifier = Modifier
) {
    val fillColor by animateColorAsState(
        targetValue = when {
            isNext -> inactiveColor.copy(alpha = 0.3f)
            else -> activeColor
        },
        animationSpec = tween(animationDuration),
        label = "dotFill"
    )

    val contentColor by animateColorAsState(
        targetValue = if (isNext) inactiveColor.copy(alpha = 0.6f) else Color.White,
        animationSpec = tween(animationDuration),
        label = "dotContent"
    )

    val scale by animateFloatAsState(
        targetValue = if (isCurrent) 1.15f else 1f,
        animationSpec = StepperDefaults.bouncySpring(),
        label = "dotScale"
    )

    val segmentFill by animateFloatAsState(
        targetValue = when {
            isPrevious -> 1f
            isCurrent -> 0.5f
            else -> 0f
        },
        animationSpec = StepperDefaults.smoothSpring(),
        label = "segmentFill"
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dot indicator
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(dotSize)
                .scale(scale)
                .drawBehind {
                    drawCircle(color = fillColor)
                    drawCircle(
                        color = Color.White,
                        radius = size.minDimension / 2,
                        style = Stroke(width = 2.dp.toPx())
                    )
                    drawCircle(color = fillColor)
                }
        ) {
            AnimatedContent(
                targetState = isPrevious,
                transitionSpec = {
                    (fadeIn(tween(280)) + scaleIn(tween(280)))
                        .togetherWith(fadeOut(tween(280)) + scaleOut(tween(280)))
                },
                label = "dotContent"
            ) { showCheck ->
                if (showCheck) {
                    Image(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(dotSize * 0.5f)
                    )
                } else {
                    Text(
                        text = stepNumber,
                        color = contentColor,
                        fontSize = dotFontSize,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Bar segment below (if not the last step)
        if (!isEndNode) {
            Spacer(
                modifier = Modifier
                    .width(barThickness)
                    .weight(1f)
                    .drawBehind {
                        val x = size.width / 2
                        drawLine(
                            color = inactiveColor.copy(alpha = 0.25f),
                            start = Offset(x, 0f),
                            end = Offset(x, size.height),
                            strokeWidth = size.width,
                            cap = StrokeCap.Butt
                        )
                        if (segmentFill > 0f) {
                            drawLine(
                                color = activeColor,
                                start = Offset(x, 0f),
                                end = Offset(x, size.height * segmentFill),
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
private fun VProgressLabel(
    title: String,
    isActive: Boolean,
    isCurrent: Boolean,
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
            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal
        )
        if (!isEndNode) Spacer(modifier = Modifier.weight(1f))
    }
}


//********************************//
//            PREVIEW            //
//******************************//

@Preview(name = "VerticalProgressBar – Step 1", showBackground = true, widthDp = 360, heightDp = 320)
@Composable
private fun VerticalProgressBarPreview_Step1() {
    VerticalProgressBarStepper(
        modifier = Modifier.fillMaxWidth().height(300.dp),
        steps = listOf("Order placed", "Processing", "Shipped", "Out for delivery", "Delivered"),
        currentStep = 0,
        activeColor = Color(0xFF14B8A6),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF134E4A),
        inactiveTitleColor = Color(0xFF94A3B8),
        barThickness = 6.dp
    )
}

@Preview(name = "VerticalProgressBar – Step 3", showBackground = true, widthDp = 360, heightDp = 320)
@Composable
private fun VerticalProgressBarPreview_Step3() {
    VerticalProgressBarStepper(
        modifier = Modifier.fillMaxWidth().height(300.dp),
        steps = listOf("Order placed", "Processing", "Shipped", "Out for delivery", "Delivered"),
        currentStep = 2,
        activeColor = Color(0xFF14B8A6),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF134E4A),
        inactiveTitleColor = Color(0xFF94A3B8),
        barThickness = 6.dp
    )
}

@Preview(name = "VerticalProgressBar – Complete", showBackground = true, widthDp = 360, heightDp = 320)
@Composable
private fun VerticalProgressBarPreview_Complete() {
    VerticalProgressBarStepper(
        modifier = Modifier.fillMaxWidth().height(300.dp),
        steps = listOf("Order placed", "Processing", "Shipped", "Out for delivery", "Delivered"),
        currentStep = 4,
        activeColor = Color(0xFF14B8A6),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF134E4A),
        inactiveTitleColor = Color(0xFF94A3B8),
        barThickness = 6.dp
    )
}
