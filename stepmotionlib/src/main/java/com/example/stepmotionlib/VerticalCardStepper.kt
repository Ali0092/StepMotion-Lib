package com.example.stepmotionlib

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VerticalCardStepper(
    modifier: Modifier = Modifier,
    steps: List<String>,
    descriptions: List<String>,
    currentStep: Int,
    activeColor: Color,
    inactiveColor: Color,
    cardBackgroundColor: Color,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
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
                    )
                    if (!isLast) {
                        CardConnectorLine(
                            modifier = Modifier
                                .weight(1f)
                                .width(3.dp),
                            isCompleted = isCompleted,
                            activeColor = activeColor,
                            inactiveColor = inactiveColor,
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Right card column
                StepCard(
                    title = title,
                    description = descriptions.getOrElse(index) { "" },
                    isCompleted = isCompleted,
                    isCurrent = isCurrent,
                    activeColor = activeColor,
                    inactiveColor = inactiveColor,
                    cardBackgroundColor = cardBackgroundColor,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = if (isLast) 0.dp else 8.dp)
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
) {
    val bgColor by animateColorAsState(
        targetValue = when {
            isCompleted || isCurrent -> activeColor
            else -> inactiveColor.copy(alpha = 0.3f)
        },
        animationSpec = tween(400),
        label = "badgeBg"
    )

    Surface(
        modifier = Modifier.size(36.dp),
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
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = stepNumber.toString(),
                        color = Color.White,
                        fontSize = 15.sp,
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
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessLow
        ),
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
    cardBackgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    val elevation by animateDpAsState(
        targetValue = if (isCurrent) 4.dp else 0.dp,
        animationSpec = tween(400),
        label = "cardElevation"
    )

    val cardBg by animateColorAsState(
        targetValue = when {
            isCurrent -> activeColor.copy(alpha = 0.06f)
            else -> cardBackgroundColor
        },
        animationSpec = tween(400),
        label = "cardBg"
    )

    val accentColor by animateColorAsState(
        targetValue = when {
            isCompleted || isCurrent -> activeColor
            else -> inactiveColor.copy(alpha = 0.2f)
        },
        animationSpec = tween(400),
        label = "accentColor"
    )

    val titleColor by animateColorAsState(
        targetValue = when {
            isCurrent -> activeColor
            isCompleted -> activeColor.copy(alpha = 0.7f)
            else -> inactiveColor
        },
        animationSpec = tween(400),
        label = "titleColor"
    )

    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        shadowElevation = elevation,
        color = cardBg,
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Left accent border
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .drawBehind {
                        drawRoundRect(
                            color = accentColor,
                            cornerRadius = CornerRadius(2.dp.toPx())
                        )
                    }
            )
            Column(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 14.dp)
            ) {
                Text(
                    text = title,
                    color = titleColor,
                    fontSize = 15.sp,
                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                )
                AnimatedVisibility(
                    visible = isCurrent && description.isNotEmpty(),
                    enter = expandVertically(
                        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                    ) + fadeIn(tween(300)),
                    exit = shrinkVertically(
                        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                    ) + fadeOut(tween(200)),
                ) {
                    Text(
                        text = description,
                        color = titleColor.copy(alpha = 0.7f),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }
        }
    }
}
