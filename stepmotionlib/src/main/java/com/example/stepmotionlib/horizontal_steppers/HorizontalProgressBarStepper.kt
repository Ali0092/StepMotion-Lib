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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.stepmotionlib.StepperDefaults
import androidx.compose.ui.tooling.preview.Preview

/**
 * A horizontal stepper styled as a continuous progress bar with step dots placed on top.
 * The bar fills progressively as steps are completed, giving a clear sense of overall progress.
 * Step dots are solid circles that sit on the bar; labels appear below.
 *
 * @param steps List of step labels
 * @param currentStep Current step index (0-based)
 * @param activeColor Color for the filled bar and active/completed dots
 * @param inactiveColor Color for the unfilled bar and future dots
 * @param activeTitleColor Text color for active/completed labels
 * @param inactiveTitleColor Text color for future labels
 * @param modifier Modifier applied to the stepper
 * @param dotSize Diameter of each step dot
 * @param barThickness Height/thickness of the progress bar
 * @param dotFontSize Font size for numbers inside dots
 * @param titleFontSize Font size for step labels
 * @param labelSpacing Vertical spacing between bar row and labels
 * @param animationDuration Duration of animations in milliseconds
 */
@Composable
fun HorizontalProgressBarStepper(
    modifier: Modifier = Modifier,
    steps: List<String>,
    currentStep: Int,
    activeColor: Color,
    inactiveColor: Color,
    activeTitleColor: Color,
    inactiveTitleColor: Color,
    dotSize: Dp = StepperDefaults.SmallCircleSize,
    barThickness: Dp = 6.dp,
    dotFontSize: TextUnit = StepperDefaults.CircleNumberFontSize,
    titleFontSize: TextUnit = StepperDefaults.SmallTitleFontSize,
    labelSpacing: Dp = StepperDefaults.LargeSpacing,
    animationDuration: Int = StepperDefaults.ColorAnimationDuration,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            steps.forEachIndexed { index, _ ->
                ProgressDot(
                    stepNumber = (index + 1).toString(),
                    isPrevious = index < currentStep,
                    isCurrent = index == currentStep,
                    isNext = index > currentStep,
                    activeColor = activeColor,
                    inactiveColor = inactiveColor,
                    dotSize = dotSize,
                    dotFontSize = dotFontSize,
                    animationDuration = animationDuration
                )

                if (index < steps.lastIndex) {
                    ProgressBarSegment(
                        modifier = Modifier
                            .weight(1f)
                            .height(barThickness),
                        isPrevious = index < currentStep,
                        isCurrent = index == currentStep,
                        activeColor = activeColor,
                        inactiveColor = inactiveColor,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(labelSpacing))

        // Labels row — aligned to match dot positions
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            steps.forEachIndexed { index, title ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.wrapContentWidth()
                ) {
                    ProgressBarLabel(
                        title = title,
                        isActive = index <= currentStep,
                        isCurrent = index == currentStep,
                        activeColor = activeTitleColor,
                        inactiveColor = inactiveTitleColor,
                        fontSize = titleFontSize,
                        animationDuration = animationDuration
                    )
                }
                if (index < steps.lastIndex) Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun ProgressDot(
    stepNumber: String,
    isPrevious: Boolean,
    isCurrent: Boolean,
    isNext: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    dotSize: Dp,
    dotFontSize: TextUnit,
    animationDuration: Int
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

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(dotSize)
            .scale(scale)
            .drawBehind {
                drawCircle(color = fillColor)
                // White ring separator so dot pops off the bar
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
}

@Composable
private fun ProgressBarSegment(
    modifier: Modifier,
    isPrevious: Boolean,
    isCurrent: Boolean,
    activeColor: Color,
    inactiveColor: Color
) {
    val fill by animateFloatAsState(
        targetValue = when {
            isPrevious -> 1f
            isCurrent -> 0.5f
            else -> 0f
        },
        animationSpec = StepperDefaults.smoothSpring(),
        label = "segmentFill"
    )

    Canvas(modifier = modifier) {
        val y = size.height / 2
        // Full background
        drawLine(
            color = inactiveColor.copy(alpha = 0.25f),
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = size.height,
            cap = StrokeCap.Butt
        )
        // Animated fill
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

@Composable
private fun ProgressBarLabel(
    title: String,
    isActive: Boolean,
    isCurrent: Boolean,
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
        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 2.dp)
    )
}


//********************************//
//            PREVIEW            //
//******************************//

@Preview(name = "HorizontalProgressBar – Step 1", showBackground = true, widthDp = 360)
@Composable
private fun HorizontalProgressBarPreview_Step1() {
    HorizontalProgressBarStepper(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        steps = listOf("Start", "Design", "Build", "Test", "Ship"),
        currentStep = 0,
        activeColor = Color(0xFFEF4444),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF7F1D1D),
        inactiveTitleColor = Color(0xFF94A3B8),
        barThickness = 6.dp
    )
}

@Preview(name = "HorizontalProgressBar – Step 3", showBackground = true, widthDp = 360)
@Composable
private fun HorizontalProgressBarPreview_Step3() {
    HorizontalProgressBarStepper(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        steps = listOf("Start", "Design", "Build", "Test", "Ship"),
        currentStep = 2,
        activeColor = Color(0xFFEF4444),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF7F1D1D),
        inactiveTitleColor = Color(0xFF94A3B8),
        barThickness = 6.dp
    )
}

@Preview(name = "HorizontalProgressBar – Complete", showBackground = true, widthDp = 360)
@Composable
private fun HorizontalProgressBarPreview_Complete() {
    HorizontalProgressBarStepper(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        steps = listOf("Start", "Design", "Build", "Test", "Ship"),
        currentStep = 4,
        activeColor = Color(0xFFEF4444),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF7F1D1D),
        inactiveTitleColor = Color(0xFF94A3B8),
        barThickness = 6.dp
    )
}
