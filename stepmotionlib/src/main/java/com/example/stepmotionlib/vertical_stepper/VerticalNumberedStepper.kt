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
 * A vertical stepper where inactive steps are outlined circles (no fill),
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
 * @param horizontalSpacing Spacing between the indicator column and labels
 * @param verticalSpacing Spacing between connector line and circles
 * @param connectorThickness Thickness of connector lines
 * @param outerRingWidth Width of the outer ring around the current step
 * @param animationDuration Duration of animations in milliseconds
 */
@Composable
fun VerticalNumberedStepper(
    steps: List<String>,
    currentStep: Int,
    activeColor: Color,
    inactiveColor: Color,
    activeTitleColor: Color,
    inactiveTitleColor: Color,
    modifier: Modifier = Modifier,
    circleSize: Dp = StepperDefaults.SmallCircleSize,
    circleFontSize: TextUnit = StepperDefaults.CircleNumberFontSize,
    titleFontSize: TextUnit = StepperDefaults.CircleNumberFontSize,
    horizontalSpacing: Dp = 16.dp,
    verticalSpacing: Dp = StepperDefaults.SmallSpacing,
    connectorThickness: Dp = StepperDefaults.ThinConnector,
    outerRingWidth: Dp = 2.dp,
    animationDuration: Int = StepperDefaults.ColorAnimationDuration,
) {
    val ringPadding = outerRingWidth + 4.dp
    val totalSize = circleSize + ringPadding * 2

    Row(modifier = modifier.fillMaxWidth()) {
        // Left column: indicators + connectors
        Column(
            modifier = Modifier.padding(horizontal = horizontalSpacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            steps.forEachIndexed { index, _ ->
                NumberedVIndicator(
                    stepNumber = (index + 1).toString(),
                    isPrevious = index < currentStep,
                    isCurrent = index == currentStep,
                    isNext = index > currentStep,
                    isEndNode = index == steps.lastIndex,
                    activeColor = activeColor,
                    inactiveColor = inactiveColor,
                    circleSize = circleSize,
                    totalSize = totalSize,
                    circleFontSize = circleFontSize,
                    verticalSpacing = verticalSpacing,
                    connectorThickness = connectorThickness,
                    outerRingWidth = outerRingWidth,
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
                NumberedVLabel(
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
private fun NumberedVIndicator(
    stepNumber: String,
    isPrevious: Boolean,
    isCurrent: Boolean,
    isNext: Boolean,
    isEndNode: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    circleSize: Dp,
    totalSize: Dp,
    circleFontSize: TextUnit,
    verticalSpacing: Dp,
    connectorThickness: Dp,
    outerRingWidth: Dp,
    animationDuration: Int,
    modifier: Modifier = Modifier
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
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(totalSize)
                .scale(scale)
        ) {
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
                            cap = StrokeCap.Round
                        )
                        if (connectorFill > 0f) {
                            drawLine(
                                color = activeColor,
                                start = Offset(x, 0f),
                                end = Offset(x, size.height * connectorFill),
                                strokeWidth = size.width,
                                cap = StrokeCap.Round
                            )
                        }
                    }
            )
        }
    }
}

@Composable
private fun NumberedVLabel(
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
            fontWeight = FontWeight.SemiBold
        )
        if (!isEndNode) Spacer(modifier = Modifier.weight(1f))
    }
}


//********************************//
//            PREVIEW            //
//******************************//

@Preview(name = "VerticalNumbered – Step 1", showBackground = true, widthDp = 360, heightDp = 260)
@Composable
private fun VerticalNumberedPreview_Step1() {
    VerticalNumberedStepper(
        modifier = Modifier.fillMaxWidth().height(240.dp),
        steps = listOf("Setup", "Configure", "Preview", "Launch"),
        currentStep = 0,
        activeColor = Color(0xFF0EA5E9),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF0C4A6E),
        inactiveTitleColor = Color(0xFF94A3B8)
    )
}

@Preview(name = "VerticalNumbered – Step 2", showBackground = true, widthDp = 360, heightDp = 260)
@Composable
private fun VerticalNumberedPreview_Step2() {
    VerticalNumberedStepper(
        modifier = Modifier.fillMaxWidth().height(240.dp),
        steps = listOf("Setup", "Configure", "Preview", "Launch"),
        currentStep = 2,
        activeColor = Color(0xFF0EA5E9),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF0C4A6E),
        inactiveTitleColor = Color(0xFF94A3B8)
    )
}

@Preview(name = "VerticalNumbered – Complete", showBackground = true, widthDp = 360, heightDp = 260)
@Composable
private fun VerticalNumberedPreview_Complete() {
    VerticalNumberedStepper(
        modifier = Modifier.fillMaxWidth().height(240.dp),
        steps = listOf("Setup", "Configure", "Preview", "Launch"),
        currentStep = 3,
        activeColor = Color(0xFF0EA5E9),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF0C4A6E),
        inactiveTitleColor = Color(0xFF94A3B8)
    )
}
