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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.stepmotionlib.StepperDefaults
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.Star

/**
 * A horizontal stepper that shows custom icons inside step indicators.
 * Inactive steps display the custom icon in an outlined circle (no fill).
 * The current step shows the icon filled with the active color (icon in white).
 * Completed steps replace the icon with a checkmark.
 *
 * @param steps List of step labels
 * @param icons List of icons matching steps (one icon per step)
 * @param currentStep Current step index (0-based)
 * @param activeColor Color for active and completed steps
 * @param inactiveColor Color for future steps
 * @param activeTitleColor Text color for active/completed labels
 * @param inactiveTitleColor Text color for future labels
 * @param modifier Modifier applied to the stepper
 * @param circleSize Size of the step indicator circles
 * @param iconSize Size of the icons inside circles
 * @param titleFontSize Font size for step labels
 * @param spacing Vertical spacing between circles and labels
 * @param connectorThickness Thickness of connector lines
 * @param animationDuration Duration of animations in milliseconds
 */
@Composable
fun HorizontalIconStepper(
    steps: List<String>,
    icons: List<ImageVector>,
    currentStep: Int,
    activeColor: Color,
    inactiveColor: Color,
    activeTitleColor: Color,
    inactiveTitleColor: Color,
    modifier: Modifier = Modifier,
    circleSize: Dp = StepperDefaults.SmallCircleSize,
    iconSize: Dp = 18.dp,
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
            val icon = icons.getOrNull(index)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.wrapContentWidth()
            ) {
                IconHIndicator(
                    icon = icon,
                    isPrevious = index < currentStep,
                    isCurrent = index == currentStep,
                    isNext = index > currentStep,
                    activeColor = activeColor,
                    inactiveColor = inactiveColor,
                    circleSize = circleSize,
                    iconSize = iconSize,
                    animationDuration = animationDuration
                )
                Spacer(modifier = Modifier.height(spacing))
                IconHLabel(
                    title = title,
                    isActive = index <= currentStep,
                    activeColor = activeTitleColor,
                    inactiveColor = inactiveTitleColor,
                    fontSize = titleFontSize,
                    animationDuration = animationDuration
                )
            }

            if (index < steps.lastIndex) {
                IconHConnector(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.Top)
                        .padding(
                            top = circleSize / 2 - connectorThickness / 2,
                            start = 4.dp,
                            end = 4.dp
                        ),
                    isPrevious = index < currentStep,
                    activeColor = activeColor,
                    inactiveColor = inactiveColor,
                    thickness = connectorThickness
                )
            }
        }
    }
}

@Composable
private fun IconHIndicator(
    icon: ImageVector?,
    isPrevious: Boolean,
    isCurrent: Boolean,
    isNext: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    circleSize: Dp,
    iconSize: Dp,
    animationDuration: Int
) {
    val fillAlpha by animateFloatAsState(
        targetValue = if (isNext) 0f else 1f,
        animationSpec = tween(animationDuration),
        label = "fillAlpha"
    )

    val strokeColor by animateColorAsState(
        targetValue = if (isNext) inactiveColor.copy(alpha = 0.5f) else activeColor,
        animationSpec = tween(animationDuration),
        label = "strokeColor"
    )

    val iconTint by animateColorAsState(
        targetValue = if (isNext) inactiveColor.copy(alpha = 0.55f) else Color.White,
        animationSpec = tween(animationDuration),
        label = "iconTint"
    )

    val scale by animateFloatAsState(
        targetValue = if (isCurrent) 1.1f else 1f,
        animationSpec = StepperDefaults.bouncySpring(),
        label = "scale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(circleSize)
            .scale(scale)
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
                (fadeIn(tween(280)) + scaleIn(tween(280)))
                    .togetherWith(fadeOut(tween(280)) + scaleOut(tween(280)))
            },
            label = "iconContent"
        ) { showCheck ->
            if (showCheck) {
                Image(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(iconSize)
                )
            } else if (icon != null) {
                Image(
                    imageVector = icon,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(iconTint),
                    modifier = Modifier.size(iconSize)
                )
            }
        }
    }
}

@Composable
private fun IconHLabel(
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
private fun IconHConnector(
    modifier: Modifier,
    isPrevious: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    thickness: Dp
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
// ─── Previews ───────────────────────────────────────────────────────────────


private val previewIcons = listOf(
    Icons.Rounded.Person,
    Icons.Rounded.Home,
    Icons.Rounded.ShoppingCart,
    Icons.Rounded.Star
)

@Preview(name = "HorizontalIcon – Step 1", showBackground = true, widthDp = 360)
@Composable
private fun HorizontalIconPreview_Step1() {
    HorizontalIconStepper(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        steps = listOf("Profile", "Home", "Cart", "Done"),
        icons = previewIcons,
        currentStep = 0,
        activeColor = Color(0xFFF59E0B),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF78350F),
        inactiveTitleColor = Color(0xFF94A3B8)
    )
}

@Preview(name = "HorizontalIcon – Step 2", showBackground = true, widthDp = 360)
@Composable
private fun HorizontalIconPreview_Step2() {
    HorizontalIconStepper(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        steps = listOf("Profile", "Home", "Cart", "Done"),
        icons = previewIcons,
        currentStep = 2,
        activeColor = Color(0xFFF59E0B),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF78350F),
        inactiveTitleColor = Color(0xFF94A3B8)
    )
}

@Preview(name = "HorizontalIcon – Complete", showBackground = true, widthDp = 360)
@Composable
private fun HorizontalIconPreview_Complete() {
    HorizontalIconStepper(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        steps = listOf("Profile", "Home", "Cart", "Done"),
        icons = previewIcons,
        currentStep = 3,
        activeColor = Color(0xFFF59E0B),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF78350F),
        inactiveTitleColor = Color(0xFF94A3B8)
    )
}
