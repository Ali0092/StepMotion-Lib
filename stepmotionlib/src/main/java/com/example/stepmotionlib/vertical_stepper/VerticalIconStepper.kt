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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.stepmotionlib.StepperDefaults
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Star
import androidx.compose.foundation.layout.height

/**
 * A vertical stepper that shows custom icons inside step indicators.
 * Inactive steps display the custom icon in an outlined circle (no fill).
 * The current step shows the icon filled with active color (icon in white).
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
 * @param horizontalSpacing Spacing between the indicator column and labels
 * @param verticalSpacing Spacing between connector line and circles
 * @param connectorThickness Thickness of connector lines
 * @param animationDuration Duration of animations in milliseconds
 */
@Composable
fun VerticalIconStepper(
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
                IconVIndicator(
                    icon = icons.getOrNull(index),
                    isPrevious = index < currentStep,
                    isCurrent = index == currentStep,
                    isNext = index > currentStep,
                    isEndNode = index == steps.lastIndex,
                    activeColor = activeColor,
                    inactiveColor = inactiveColor,
                    circleSize = circleSize,
                    iconSize = iconSize,
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
                IconVLabel(
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
private fun IconVIndicator(
    icon: ImageVector?,
    isPrevious: Boolean,
    isCurrent: Boolean,
    isNext: Boolean,
    isEndNode: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    circleSize: Dp,
    iconSize: Dp,
    verticalSpacing: Dp,
    connectorThickness: Dp,
    animationDuration: Int,
    modifier: Modifier = Modifier
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
private fun IconVLabel(
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

private val vIconPreviewIcons = listOf(
    Icons.Rounded.Person,
    Icons.Rounded.Home,
    Icons.Rounded.Star,
    Icons.Rounded.Favorite
)

@Preview(name = "VerticalIcon – Step 1", showBackground = true, widthDp = 360, heightDp = 260)
@Composable
private fun VerticalIconPreview_Step1() {
    VerticalIconStepper(
        modifier = Modifier.fillMaxWidth().height(240.dp),
        steps = listOf("Account", "Address", "Wishlist", "Favourites"),
        icons = vIconPreviewIcons,
        currentStep = 0,
        activeColor = Color(0xFFEC4899),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF831843),
        inactiveTitleColor = Color(0xFF94A3B8)
    )
}

@Preview(name = "VerticalIcon – Step 2", showBackground = true, widthDp = 360, heightDp = 260)
@Composable
private fun VerticalIconPreview_Step2() {
    VerticalIconStepper(
        modifier = Modifier.fillMaxWidth().height(240.dp),
        steps = listOf("Account", "Address", "Wishlist", "Favourites"),
        icons = vIconPreviewIcons,
        currentStep = 2,
        activeColor = Color(0xFFEC4899),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF831843),
        inactiveTitleColor = Color(0xFF94A3B8)
    )
}

@Preview(name = "VerticalIcon – Complete", showBackground = true, widthDp = 360, heightDp = 260)
@Composable
private fun VerticalIconPreview_Complete() {
    VerticalIconStepper(
        modifier = Modifier.fillMaxWidth().height(240.dp),
        steps = listOf("Account", "Address", "Wishlist", "Favourites"),
        icons = vIconPreviewIcons,
        currentStep = 3,
        activeColor = Color(0xFFEC4899),
        inactiveColor = Color(0xFFCBD5E1),
        activeTitleColor = Color(0xFF831843),
        inactiveTitleColor = Color(0xFF94A3B8)
    )
}
