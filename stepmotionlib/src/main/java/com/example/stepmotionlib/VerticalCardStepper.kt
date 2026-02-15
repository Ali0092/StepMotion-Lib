package com.example.stepmotionlib

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A vertical card stepper component that displays steps with circle indicators and expandable cards.
 * Features smooth animations and shows descriptions for the current step.
 *
 * @param steps List of step labels to display
 * @param descriptions List of descriptions for each step (shown when current)
 * @param currentStep Current step index (0-based)
 * @param activeColor Color for completed and current steps
 * @param inactiveColor Color for future steps
 * @param cardBackgroundColor Background color for non-current step cards
 * @param activeCardBackgroundColor Background color for the current step card
 * @param modifier Modifier to be applied to the stepper
 * @param circleSize Size of the step indicator circles
 * @param circleFontSize Font size for numbers inside circles
 * @param titleFontSize Font size for step titles
 * @param descriptionFontSize Font size for step descriptions
 * @param cardRadius Corner radius for step cards
 * @param cardPadding Padding inside step cards
 * @param cardSpacing Spacing between step cards
 * @param horizontalSpacing Horizontal spacing between circles and cards
 * @param connectorThickness Thickness of connector lines between steps
 * @param accentBorderWidth Width of the colored accent border on cards
 * @param animationDuration Duration of color and elevation animations in milliseconds
 */
@Composable
fun VerticalCardStepper(
    steps: List<String>,
    descriptions: List<String>,
    currentStep: Int,
    activeColor: Color,
    inactiveColor: Color,
    cardBackgroundColor: Color,
    activeCardBackgroundColor: Color,
    modifier: Modifier = Modifier,
    circleSize: Dp = StepperDefaults.SmallCircleSize,
    circleFontSize: TextUnit = 15.sp,
    titleFontSize: TextUnit = 15.sp,
    descriptionFontSize: TextUnit = StepperDefaults.DescriptionFontSize,
    cardRadius: Dp = 12.dp,
    cardPadding: Dp = 14.dp,
    cardSpacing: Dp = StepperDefaults.LargeSpacing,
    horizontalSpacing: Dp = 12.dp,
    connectorThickness: Dp = StepperDefaults.MediumConnector,
    accentBorderWidth: Dp = 4.dp,
    animationDuration: Int = StepperDefaults.ColorAnimationDuration,
    // Legacy parameter for backward compatibility (prefer using activeCardBackgroundColor)
    inActiveBackgroundColor: Color? = null,
) {
    // Handle backward compatibility
    val actualActiveCardBg = inActiveBackgroundColor ?: activeCardBackgroundColor

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        steps.forEachIndexed { index, title ->
            val isCompleted = index < currentStep
            val isCurrent = index == currentStep
            val isLast = index == steps.lastIndex

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                // Left indicator column (circle + connector)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    StepBadge(
                        stepNumber = index + 1,
                        isCompleted = isCompleted,
                        isCurrent = isCurrent,
                        activeColor = activeColor,
                        inactiveColor = inactiveColor,
                        circleSize = circleSize,
                        fontSize = circleFontSize,
                        animationDuration = animationDuration
                    )
                    if (!isLast) {
                        CardConnectorLine(
                            modifier = Modifier
                                .weight(1f)
                                .width(connectorThickness),
                            isCompleted = isCompleted,
                            activeColor = activeColor,
                            inactiveColor = inactiveColor
                        )
                    }
                }

                Spacer(modifier = Modifier.width(horizontalSpacing))

                // Right card column
                StepCard(
                    title = title,
                    description = descriptions.getOrElse(index) { "" },
                    isCompleted = isCompleted,
                    isCurrent = isCurrent,
                    activeColor = activeColor,
                    inactiveColor = inactiveColor,
                    activeCardBackgroundColor = actualActiveCardBg,
                    cardBackgroundColor = cardBackgroundColor,
                    titleFontSize = titleFontSize,
                    descriptionFontSize = descriptionFontSize,
                    cardRadius = cardRadius,
                    cardPadding = cardPadding,
                    accentBorderWidth = accentBorderWidth,
                    animationDuration = animationDuration,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = if (isLast) 0.dp else cardSpacing)
                )
            }
        }
    }
}

@Composable
private fun StepBadge(
    stepNumber: Int,
    isCompleted: Boolean,
    isCurrent: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    circleSize: Dp,
    fontSize: TextUnit,
    animationDuration: Int
) {
    val bgColor by animateColorAsState(
        targetValue = when {
            isCompleted || isCurrent -> activeColor
            else -> inactiveColor.copy(alpha = 0.3f)
        },
        animationSpec = tween(animationDuration),
        label = "badgeBg"
    )

    Surface(
        modifier = Modifier.size(circleSize),
        shape = CircleShape,
        color = bgColor,
    ) {
        Box(contentAlignment = Alignment.Center) {
            AnimatedContent(
                targetState = isCompleted,
                transitionSpec = {
                    (fadeIn(tween(300)) + scaleIn(tween(300)))
                        .togetherWith(fadeOut(tween(300)) + scaleOut(tween(300)))
                },
                label = "badgeContent"
            ) { completed ->
                if (completed) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(circleSize * 0.55f)
                    )
                } else {
                    Text(
                        text = stepNumber.toString(),
                        color = Color.White,
                        fontSize = fontSize,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Composable
private fun CardConnectorLine(
    modifier: Modifier = Modifier,
    isCompleted: Boolean,
    activeColor: Color,
    inactiveColor: Color,
) {
    val fillFraction by animateFloatAsState(
        targetValue = if (isCompleted) 1f else 0f,
        animationSpec = StepperDefaults.smoothSpring(),
        label = "cardConnectorFill"
    )

    Box(
        modifier = modifier
            .drawBehind {
                val lineWidth = size.width
                val lineX = (size.width - lineWidth) / 2
                // Inactive background
                drawRoundRect(
                    color = inactiveColor.copy(alpha = 0.2f),
                    topLeft = Offset(lineX, 0f),
                    size = Size(lineWidth, size.height),
                    cornerRadius = CornerRadius(lineWidth / 2)
                )
                // Active fill
                if (fillFraction > 0f) {
                    drawRoundRect(
                        color = activeColor,
                        topLeft = Offset(lineX, 0f),
                        size = Size(lineWidth, size.height * fillFraction),
                        cornerRadius = CornerRadius(lineWidth / 2)
                    )
                }
            }
    )
}

@Composable
private fun StepCard(
    title: String,
    description: String,
    isCompleted: Boolean,
    isCurrent: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    activeCardBackgroundColor: Color,
    cardBackgroundColor: Color,
    titleFontSize: TextUnit,
    descriptionFontSize: TextUnit,
    cardRadius: Dp,
    cardPadding: Dp,
    accentBorderWidth: Dp,
    animationDuration: Int,
    modifier: Modifier = Modifier,
) {
    val elevation by animateDpAsState(
        targetValue = if (isCurrent) 2.dp else 0.dp,
        animationSpec = tween(animationDuration),
        label = "cardElevation"
    )

    val cardBg by animateColorAsState(
        targetValue = when {
            isCurrent -> activeCardBackgroundColor
            else -> cardBackgroundColor
        },
        animationSpec = tween(animationDuration),
        label = "cardBg"
    )

    val accentColor by animateColorAsState(
        targetValue = when {
            isCompleted || isCurrent -> activeColor
            else -> inactiveColor.copy(alpha = 0.2f)
        },
        animationSpec = tween(animationDuration),
        label = "accentColor"
    )

    val titleColor by animateColorAsState(
        targetValue = when {
            isCurrent -> activeColor
            isCompleted -> activeColor.copy(alpha = 0.7f)
            else -> inactiveColor
        },
        animationSpec = tween(animationDuration),
        label = "titleColor"
    )

    Surface(
        modifier = modifier.clip(RoundedCornerShape(cardRadius)),
        shape = RoundedCornerShape(cardRadius),
        shadowElevation = elevation,
        color = cardBg,
    ) {
        Row(modifier = Modifier.fillMaxWidth().background(cardBg)) {
            // Left accent border
            Box(
                modifier = Modifier
                    .width(accentBorderWidth)
                    .fillMaxHeight()
                    .drawBehind {
                        drawRoundRect(
                            color = accentColor,
                            cornerRadius = CornerRadius(2.dp.toPx())
                        )
                    }
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = cardPadding, vertical = cardPadding)
                    .animateContentSize(
                        animationSpec = StepperDefaults.bouncySpring()
                    )
            ) {
                Text(
                    text = title,
                    color = titleColor,
                    fontSize = titleFontSize,
                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                )
                if (isCurrent && description.isNotEmpty()) {
                    Text(
                        text = description,
                        color = titleColor.copy(alpha = 0.7f),
                        fontSize = descriptionFontSize,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }
        }
    }
}
