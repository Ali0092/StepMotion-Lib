package com.example.stepmotionlib

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HorizontalSimpleStepper(
    modifier: Modifier = Modifier,
    countingList: List<Int>,
    titleList: List<String>,
    selectedItemIndex: Int,
    nonSelectedItemColor: Color,
    selectedItemColor: Color,
    nonSelectedTitleColor: Color,
    selectedTitleColor: Color
) {

    Column(
        modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StepperTopBar(
            items = countingList,
            selectedItemColor = selectedItemColor,
            nonSelectedItemColor = nonSelectedItemColor,
            selectedItemIndex = selectedItemIndex
        )
        Spacer(modifier = Modifier.height(4.dp))
        StepperBottomBar(
            titleList = titleList,
            selectedIndex = selectedItemIndex,
            nonSelectedTitleColor = nonSelectedTitleColor,
            selectedTitleColor = selectedTitleColor
        )

    }
}

@Composable
fun StepperTopBar(
    modifier: Modifier = Modifier,
    items: List<Int>,
    selectedItemIndex: Int = 0,
    nonSelectedItemColor: Color = Color.LightGray,
    selectedItemColor: Color = Color.Blue,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        items.forEachIndexed { index, _ ->
            if (index == items.lastIndex) {
                TopStepperSingleItem(
                    text = items[index].toString(),
                    isEndNode = true,
                    isPrevious = index < selectedItemIndex,
                    isCurrent = selectedItemIndex == index,
                    isNext = index > selectedItemIndex,
                    nonSelectedItemColor = nonSelectedItemColor,
                    selectedItemColor = selectedItemColor
                )
            } else {
                TopStepperSingleItem(
                    modifier = Modifier.weight(1f),
                    text = items[index].toString(),
                    isPrevious = index < selectedItemIndex,
                    isCurrent = selectedItemIndex == index,
                    isNext = index > selectedItemIndex,
                    nonSelectedItemColor = nonSelectedItemColor,
                    selectedItemColor = selectedItemColor
                )
            }
        }
    }

}

@Composable
fun TopStepperSingleItem(
    modifier: Modifier = Modifier,
    text: String = "",
    isEndNode: Boolean = false,
    isPrevious: Boolean = false,
    isCurrent: Boolean = false,
    isNext: Boolean = false,
    nonSelectedItemColor: Color = Color.LightGray,
    selectedItemColor: Color = Color.Blue,
) {
    val nonSelectedColor = selectedItemColor.copy(alpha = 0.3f)

    val containerColor by animateColorAsState(
        targetValue = if (isNext) nonSelectedColor else selectedItemColor,
        animationSpec = tween(400),
        label = "containerColor"
    )

    val borderWidth by animateDpAsState(
        targetValue = if (isCurrent) 2.dp else 0.dp,
        animationSpec = tween(300),
        label = "borderWidth"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isCurrent) selectedItemColor else Color.Transparent,
        animationSpec = tween(300),
        label = "borderColor"
    )

    val indicatorScale by animateFloatAsState(
        targetValue = if (isCurrent) 1.1f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "indicatorScale"
    )

    val innerPadding by animateDpAsState(
        targetValue = if (isCurrent) 4.dp else 0.dp,
        animationSpec = tween(300),
        label = "innerPadding"
    )

    // Connector line animation
    val connectorFill by animateFloatAsState(
        targetValue = if (isCurrent || isPrevious) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "connectorFill"
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .size(32.dp)
                .scale(indicatorScale)
                .border(
                    width = borderWidth,
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
                                text = text,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                    }
                }

            }
        }

        if (!isEndNode) {
            Canvas(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f)
                    .height(2.dp)
            ) {
                val lineY = size.height / 2
                // Inactive background
                drawLine(
                    color = nonSelectedItemColor,
                    start = Offset(0f, lineY),
                    end = Offset(size.width, lineY),
                    strokeWidth = size.height,
                    cap = StrokeCap.Round,
                )
                // Animated active fill
                if (connectorFill > 0f) {
                    drawLine(
                        color = selectedItemColor,
                        start = Offset(0f, lineY),
                        end = Offset(size.width * connectorFill, lineY),
                        strokeWidth = size.height,
                        cap = StrokeCap.Round,
                    )
                }
            }
        }
    }
}

@Composable
fun StepperBottomBar(
    modifier: Modifier = Modifier,
    titleList: List<String>,
    selectedIndex: Int = 0,
    nonSelectedTitleColor: Color,
    selectedTitleColor: Color
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        titleList.forEachIndexed { index, _ ->
            if (index == titleList.lastIndex) {
                BottomStepperSingleItem(
                    title = titleList[index].toString(),
                    isEndNode = true,
                    isCurrent = index <= selectedIndex,
                    nonSelectedItemColor = nonSelectedTitleColor,
                    selectedTitleColor = selectedTitleColor
                )
            } else {
                BottomStepperSingleItem(
                    modifier = Modifier.weight(1f),
                    title = titleList[index].toString(),
                    isCurrent = index <= selectedIndex,
                    nonSelectedItemColor = nonSelectedTitleColor,
                    selectedTitleColor = selectedTitleColor
                )
            }
        }
    }
}

@Composable
fun BottomStepperSingleItem(
    modifier: Modifier = Modifier,
    title: String = "",
    isCurrent: Boolean = false,
    isEndNode: Boolean = false,
    nonSelectedItemColor: Color,
    selectedTitleColor: Color
) {
    val titleColor by animateColorAsState(
        targetValue = if (isCurrent) selectedTitleColor else nonSelectedItemColor,
        animationSpec = tween(400),
        label = "titleColor"
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = titleColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        if (!isEndNode) {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
