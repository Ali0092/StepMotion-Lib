package com.example.stepmotionlib

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import kotlinx.coroutines.launch
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HorizontalAnimatedStepper(
    modifier: Modifier = Modifier,
    steps: List<String>,
    currentStep: Int,
    activeColor: Color,
    inactiveColor: Color,
    activeTitleColor: Color,
    inactiveTitleColor: Color,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            steps.forEachIndexed { index, _ ->
                AnimatedStepIndicator(
                    stepNumber = index + 1,
                    isCompleted = index < currentStep,
                    isCurrent = index == currentStep,
                    activeColor = activeColor,
                    inactiveColor = inactiveColor,
                )
                if (index < steps.lastIndex) {
                    AnimatedConnectorLine(
                        modifier = Modifier.weight(1f),
                        isCompleted = index < currentStep,
                        activeColor = activeColor,
                        inactiveColor = inactiveColor,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            steps.forEachIndexed { index, title ->
                val titleColor by animateColorAsState(
                    targetValue = if (index <= currentStep) activeTitleColor else inactiveTitleColor,
                    animationSpec = tween(400),
                    label = "titleColor"
                )
                Text(
                    text = title,
                    color = titleColor,
                    fontSize = 12.sp,
                    fontWeight = if (index == currentStep) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
private fun AnimatedStepIndicator(
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
        label = "indicatorBg"
    )

    val scale by animateFloatAsState(
        targetValue = if (isCurrent) 1.15f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "indicatorScale"
    )

    val checkScale by animateFloatAsState(
        targetValue = if (isCompleted) 1f else 0f,
        animationSpec = spring(
            dampingRatio = 0.6f,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "checkScale"
    )

    // Pulse ring animation for current step
    val pulseScale = remember { Animatable(1f) }
    val pulseAlpha = remember { Animatable(0f) }

    LaunchedEffect(isCurrent) {
        if (isCurrent) {
            // Launch both animations in parallel
            launch {
                pulseScale.snapTo(1f)
                pulseScale.animateTo(
                    targetValue = 1.3f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }
            launch {
                pulseAlpha.snapTo(0.6f)
                pulseAlpha.animateTo(
                    targetValue = 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }
        } else {
            pulseScale.snapTo(1f)
            pulseAlpha.snapTo(0f)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .drawBehind {
                if (isCurrent) {
                    drawCircle(
                        color = activeColor.copy(alpha = pulseAlpha.value),
                        radius = (20.dp * pulseScale.value).toPx(),
                    )
                }
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .scale(scale)
                .clip(CircleShape)
                .drawBehind {
                    drawCircle(color = bgColor)
                }
        ) {
            if (isCompleted) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(22.dp)
                        .scale(checkScale)
                )
            } else {
                Text(
                    text = stepNumber.toString(),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun AnimatedConnectorLine(
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
        label = "connectorFill"
    )

    Canvas(
        modifier = modifier
            .height(3.dp)
            .padding(horizontal = 2.dp)
    ) {
        val lineY = size.height / 2
        // Draw inactive background line
        drawLine(
            color = inactiveColor.copy(alpha = 0.3f),
            start = Offset(0f, lineY),
            end = Offset(size.width, lineY),
            strokeWidth = size.height,
            cap = StrokeCap.Round,
        )
        // Draw animated active portion
        if (fillFraction > 0f) {
            drawLine(
                color = activeColor,
                start = Offset(0f, lineY),
                end = Offset(size.width * fillFraction, lineY),
                strokeWidth = size.height,
                cap = StrokeCap.Round,
            )
        }
    }
}
