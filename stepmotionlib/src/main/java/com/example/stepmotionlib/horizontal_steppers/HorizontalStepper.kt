package com.example.stepmotionlib.horizontal_steppers

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
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
 * A horizontal stepper component that displays steps with circle indicators and text labels.
 * Features smooth animations for state changes and perfect alignment between circles and text.
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
 * @param borderWidth Width of the border around the current step
 * @param animationDuration Duration of color and scale animations in milliseconds
 */
@Composable
fun HorizontalStepper(
    modifier: Modifier = Modifier,
    steps: List<String>,
    currentStep: Int,
    activeColor: Color,
    inactiveColor: Color,
    activeTitleColor: Color,
    inactiveTitleColor: Color,
    circleSize: Dp = StepperDefaults.SmallCircleSize,
    circleFontSize: TextUnit = StepperDefaults.CircleNumberFontSize,
    titleFontSize: TextUnit = StepperDefaults.MediumTitleFontSize,
    spacing: Dp = StepperDefaults.SmallSpacing,
    connectorThickness: Dp = StepperDefaults.ThinConnector,
    borderWidth: Dp = StepperDefaults.BorderWidth,
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
                StepIndicator(
                    stepNumber = (index + 1).toString(),
                    isPrevious = index < currentStep,
                    isCurrent = index == currentStep,
                    isNext = index > currentStep,
                    activeColor = activeColor,
                    inactiveColor = inactiveColor,
                    circleSize = circleSize,
                    circleFontSize = circleFontSize,
                    borderWidth = borderWidth,
                    animationDuration = animationDuration
                )

                Spacer(modifier = Modifier.height(spacing))

                StepLabel(
                    title = title,
                    isActive = index <= currentStep,
                    activeColor = activeTitleColor,
                    inactiveColor = inactiveTitleColor,
                    fontSize = titleFontSize,
                    animationDuration = animationDuration
                )
            }

            if (index < steps.lastIndex) {
                ConnectorLine(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.Top)
                        .padding(top = circleSize / 2 - connectorThickness / 2, start = 4.dp, end = 4.dp),
                    isPrevious = index < currentStep,
                    isCurrent = index == currentStep,
                    activeColor = activeColor,
                    inactiveColor = inactiveColor,
                    thickness = connectorThickness
                )
            }
        }
    }
}

@Composable
private fun StepIndicator(
    stepNumber: String,
    isPrevious: Boolean,
    isCurrent: Boolean,
    isNext: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    circleSize: Dp,
    circleFontSize: TextUnit,
    borderWidth: Dp,
    animationDuration: Int
) {
    val containerColor by animateColorAsState(
        targetValue = if (isNext) inactiveColor.copy(alpha = 0.3f) else activeColor,
        animationSpec = tween(animationDuration),
        label = "containerColor"
    )

    val borderWidthAnimated by animateDpAsState(
        targetValue = if (isCurrent) borderWidth else 0.dp,
        animationSpec = tween(animationDuration - 100),
        label = "borderWidth"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isCurrent) activeColor else Color.Transparent,
        animationSpec = tween(animationDuration - 100),
        label = "borderColor"
    )

    val indicatorScale by animateFloatAsState(
        targetValue = if (isCurrent) 1.1f else 1.0f,
        animationSpec = StepperDefaults.bouncySpring(),
        label = "indicatorScale"
    )

    val innerPadding by animateDpAsState(
        targetValue = if (isCurrent) 4.dp else 0.dp,
        animationSpec = tween(animationDuration - 100),
        label = "innerPadding"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(circleSize)
            .scale(indicatorScale)
            .border(
                width = borderWidthAnimated,
                color = borderColor,
                shape = CircleShape
            )
    ) {
        Card(
            modifier = Modifier.padding(innerPadding),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = containerColor)
        ) {
            Box(Modifier.fillMaxSize()) {
                AnimatedContent(
                    targetState = isPrevious,
                    modifier = Modifier.align(Alignment.Center),
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
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    } else {
                        Text(
                            text = stepNumber,
                            color = Color.White,
                            fontSize = circleFontSize
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StepLabel(
    title: String,
    isActive: Boolean,
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
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun ConnectorLine(
    modifier: Modifier = Modifier,
    isPrevious: Boolean,
    isCurrent: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    thickness: Dp
) {
    val connectorFill by animateFloatAsState(
        targetValue = if (isCurrent || isPrevious) 1f else 0f,
        animationSpec = StepperDefaults.smoothSpring(),
        label = "connectorFill"
    )

    Canvas(
        modifier = modifier.height(thickness)
    ) {
        val lineY = size.height / 2
        // Inactive background
        drawLine(
            color = inactiveColor,
            start = Offset(0f, lineY),
            end = Offset(size.width, lineY),
            strokeWidth = size.height,
            cap = StrokeCap.Round,
        )
        // Animated active fill
        if (connectorFill > 0f) {
            drawLine(
                color = activeColor,
                start = Offset(0f, lineY),
                end = Offset(size.width * connectorFill, lineY),
                strokeWidth = size.height,
                cap = StrokeCap.Round,
            )
        }
    }
}

//********************************//
//            PREVIEW            //
//******************************//

@Preview(name = "HorizontalSimple – Step 1", showBackground = true, widthDp = 360)
@Composable
private fun HorizontalSimplePreview_Step1() {
    HorizontalStepper(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        steps = listOf("Details", "Address", "Payment", "Done"),
        currentStep = 0,
        activeColor = Color(0xFF3B82F6),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF1E40AF),
        inactiveTitleColor = Color(0xFF94A3B8)
    )
}

@Preview(name = "HorizontalSimple – Step 2", showBackground = true, widthDp = 360)
@Composable
private fun HorizontalSimplePreview_Step2() {
    HorizontalStepper(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        steps = listOf("Details", "Address", "Payment", "Done"),
        currentStep = 1,
        activeColor = Color(0xFF3B82F6),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF1E40AF),
        inactiveTitleColor = Color(0xFF94A3B8)
    )
}

@Preview(name = "HorizontalSimple – Complete", showBackground = true, widthDp = 360)
@Composable
private fun HorizontalSimplePreview_Complete() {
    HorizontalStepper(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        steps = listOf("Details", "Address", "Payment", "Done"),
        currentStep = 3,
        activeColor = Color(0xFF3B82F6),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF1E40AF),
        inactiveTitleColor = Color(0xFF94A3B8)
    )
}
