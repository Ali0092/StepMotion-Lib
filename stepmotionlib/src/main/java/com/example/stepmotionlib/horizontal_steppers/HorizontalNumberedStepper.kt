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
 * A horizontal stepper where inactive steps are outlined circles (no fill),
 * the current step is a filled circle with a translucent outer ring, and
 * completed steps show a checkmark. Inspired by modern NFT/flow UI designs.
 *
 * @param steps List of step labels to display
 * @param currentStep Current step index (0-based)
 * @param activeColor Color for completed and current steps
 * @param inactiveColor Color for future steps
 * @param activeTitleColor Text color for active/completed steps
 * @param inactiveTitleColor Text color for future steps
 * @param modifier Modifier to be applied to the stepper
 * @param circleSize Size of the step indicator circles
 * @param circleFontSize Font size for numbers inside circles
 * @param titleFontSize Font size for step labels
 * @param spacing Vertical spacing between circles and labels
 * @param connectorThickness Thickness of connector lines
 * @param outerRingWidth Width of the outer ring drawn around the current step
 * @param animationDuration Duration of animations in milliseconds
 */
@Composable
fun HorizontalNumberedStepper(
    steps: List<String>,
    currentStep: Int,
    activeColor: Color,
    inactiveColor: Color,
    activeTitleColor: Color,
    inactiveTitleColor: Color,
    modifier: Modifier = Modifier,
    circleSize: Dp = StepperDefaults.SmallCircleSize,
    circleFontSize: TextUnit = StepperDefaults.CircleNumberFontSize,
    titleFontSize: TextUnit = StepperDefaults.MediumTitleFontSize,
    spacing: Dp = StepperDefaults.SmallSpacing,
    connectorThickness: Dp = StepperDefaults.MediumConnector,
    outerRingWidth: Dp = 2.dp,
    animationDuration: Int = StepperDefaults.ColorAnimationDuration,
) {
    // Extra space around circleSize to accommodate the outer ring
    val ringPadding = outerRingWidth + 4.dp
    val totalSize = circleSize + ringPadding * 2

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        steps.forEachIndexed { index, title ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.wrapContentWidth()
            ) {
                NumberedHIndicator(
                    stepNumber = (index + 1).toString(),
                    isPrevious = index < currentStep,
                    isCurrent = index == currentStep,
                    isNext = index > currentStep,
                    activeColor = activeColor,
                    inactiveColor = inactiveColor,
                    circleSize = circleSize,
                    totalSize = totalSize,
                    circleFontSize = circleFontSize,
                    outerRingWidth = outerRingWidth,
                    animationDuration = animationDuration
                )
                Spacer(modifier = Modifier.height(spacing))
                NumberedHLabel(
                    title = title,
                    isActive = index <= currentStep,
                    activeColor = activeTitleColor,
                    inactiveColor = inactiveTitleColor,
                    fontSize = titleFontSize,
                    animationDuration = animationDuration
                )
            }

            if (index < steps.lastIndex) {
                NumberedHConnector(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.Top)
                        .padding(
                            top = totalSize / 2 - connectorThickness / 2,
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
private fun NumberedHIndicator(
    stepNumber: String,
    isPrevious: Boolean,
    isCurrent: Boolean,
    isNext: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    circleSize: Dp,
    totalSize: Dp,
    circleFontSize: TextUnit,
    outerRingWidth: Dp,
    animationDuration: Int
) {
    val fillAlpha by animateFloatAsState(
        targetValue = if (isNext) 0f else 1f,
        animationSpec = tween(animationDuration),
        label = "fillAlpha"
    )

    val strokeColor by animateColorAsState(
        targetValue = if (isNext) inactiveColor.copy(alpha = 0.45f) else activeColor,
        animationSpec = tween(animationDuration),
        label = "strokeColor"
    )

    val contentColor by animateColorAsState(
        targetValue = if (isNext) inactiveColor.copy(alpha = 0.55f) else Color.White,
        animationSpec = tween(animationDuration),
        label = "contentColor"
    )

    val outerRingAlpha by animateFloatAsState(
        targetValue = if (isCurrent) 0.25f else 0f,
        animationSpec = tween(animationDuration),
        label = "outerRingAlpha"
    )

    val scale by animateFloatAsState(
        targetValue = if (isCurrent) 1.06f else 1f,
        animationSpec = StepperDefaults.bouncySpring(),
        label = "scale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(totalSize)
            .scale(scale)
    ) {
        // Outer translucent ring for current step
        Box(
            modifier = Modifier
                .size(totalSize)
                .drawBehind {
                    if (outerRingAlpha > 0f) {
                        drawCircle(
                            color = activeColor.copy(alpha = outerRingAlpha),
                            radius = size.minDimension / 2,
                            style = Stroke(width = outerRingWidth.toPx() * 2.5f)
                        )
                    }
                }
        )
        // Main step circle
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(circleSize)
                .drawBehind {
                    drawCircle(color = activeColor.copy(alpha = fillAlpha))
                    drawCircle(
                        color = strokeColor,
                        style = Stroke(width = 2.dp.toPx())
                    )
                }
        ) {
            AnimatedContent(
                targetState = isPrevious,
                transitionSpec = {
                    (fadeIn(tween(300)) + scaleIn(tween(300)))
                        .togetherWith(fadeOut(tween(300)) + scaleOut(tween(300)))
                },
                label = "stepContent"
            ) { showCheck ->
                if (showCheck) {
                    Image(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(circleSize * 0.55f)
                    )
                } else {
                    Text(
                        text = stepNumber,
                        color = contentColor,
                        fontSize = circleFontSize,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun NumberedHLabel(
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
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun NumberedHConnector(
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
            color = inactiveColor.copy(alpha = 0.35f),
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = size.height,
            cap = StrokeCap.Round
        )
        if (fill > 0f) {
            drawLine(
                color = activeColor,
                start = Offset(0f, y),
                end = Offset(size.width * fill, y),
                strokeWidth = size.height,
                cap = StrokeCap.Round
            )
        }
    }
}


//********************************//
//            PREVIEW            //
//******************************//

@Preview(name = "HorizontalNumbered – Step 1", showBackground = true, widthDp = 360)
@Composable
private fun HorizontalNumberedPreview_Step1() {
    HorizontalNumberedStepper(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        steps = listOf("Details", "Review", "Payment", "Confirm"),
        currentStep = 0,
        activeColor = Color(0xFF7C3AED),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF4C1D95),
        inactiveTitleColor = Color(0xFF94A3B8)
    )
}

@Preview(name = "HorizontalNumbered – Step 2", showBackground = true, widthDp = 360)
@Composable
private fun HorizontalNumberedPreview_Step2() {
    HorizontalNumberedStepper(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        steps = listOf("Details", "Review", "Payment", "Confirm"),
        currentStep = 2,
        activeColor = Color(0xFF7C3AED),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF4C1D95),
        inactiveTitleColor = Color(0xFF94A3B8)
    )
}

@Preview(name = "HorizontalNumbered – Complete", showBackground = true, widthDp = 360)
@Composable
private fun HorizontalNumberedPreview_Complete() {
    HorizontalNumberedStepper(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        steps = listOf("Details", "Review", "Payment", "Confirm"),
        currentStep = 3,
        activeColor = Color(0xFF7C3AED),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF4C1D95),
        inactiveTitleColor = Color(0xFF94A3B8)
    )
}
