package com.example.stepmotion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VerticalSimpleStepper(
    modifier: Modifier = Modifier,
    countingList: List<Int> = listOf(1, 2, 3),
    titleList: List<String> = listOf("Pending", "In Progress", "Successful"),
    selectedIndex: Int = 0,
    nonSelectedItemColor: Color = Color.Gray,
    selectedItemColor: Color = Color(0xff06a2c2),
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        StepperLeftBar(
            items = countingList,
            selectedItemColor = selectedItemColor,
            nonSelectedItemColor = nonSelectedItemColor,
            selectedItemIndex = selectedIndex
        )
        StepperRightBar(
            titleList = titleList, selectedIndex = selectedIndex
        )
    }
}

@Composable
fun StepperLeftBar(
    modifier: Modifier = Modifier,
    items: List<Int>,
    selectedItemIndex: Int = 0,
    nonSelectedItemColor: Color = Color.LightGray,
    selectedItemColor: Color = Color.Blue,
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        items.forEachIndexed { index, it ->
            if (index == items.lastIndex) {
                StepperLeftBarSingleItem(
                    text = items[index].toString(),
                    isEndNode = true,
                    isPrevious = index < selectedItemIndex,
                    isCurrent = selectedItemIndex == index,
                    isNext = index > selectedItemIndex,
                    nonSelectedItemColor = nonSelectedItemColor,
                    selectedItemColor = selectedItemColor
                )
            } else {
                StepperLeftBarSingleItem(
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
fun StepperLeftBarSingleItem(
    modifier: Modifier = Modifier,
    text: String = "",
    isEndNode: Boolean = false,
    isPrevious: Boolean = false,
    isCurrent: Boolean = false,
    isNext: Boolean = false,
    nonSelectedItemColor: Color = Color.LightGray,
    selectedItemColor: Color = Color.Blue,
) {
    val nonSelectedColor = selectedItemColor.copy(alpha = 0.5f)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .size(35.dp)
                .border(
                    width = if (isCurrent) 2.dp else 0.dp,
                    color = if (isCurrent) selectedItemColor else Color.Transparent,
                    shape = CircleShape
                )
        ) {
            Card(
                modifier = Modifier.padding(if (isCurrent) 6.dp else 0.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = if (isNext) nonSelectedColor else selectedItemColor)
            ) {

                Box(Modifier.fillMaxSize()) {
                    if (isPrevious) {
                        Image(
                            modifier = Modifier.align(Alignment.Center),
                            imageVector = Icons.Rounded.Check,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    } else {
                        if (isCurrent || isNext) {
                            Text(
                                text = text,
                                modifier = Modifier.align(Alignment.Center),
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                    }
                }

            }
        }

        if (!isEndNode) {
            Spacer(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .width(2.dp)
                    .weight(1f)
                    .background(
                        if (isCurrent || isPrevious) selectedItemColor else nonSelectedItemColor
                    )
            )
        }
    }
}

@Composable
fun StepperRightBar(
    modifier: Modifier = Modifier,
    titleList: List<String>,
    selectedIndex: Int = 0,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        titleList.forEachIndexed { index, it ->
            if (index == titleList.lastIndex) {
                StepperRightBarSingleItem(
                    title = titleList[index].toString(),
                    isEndNode = true,
                    isCurrent = index <= selectedIndex
                )
            } else {
                StepperRightBarSingleItem(
                    modifier = Modifier.weight(1f),
                    title = titleList[index].toString(),
                    isCurrent = index <= selectedIndex
                )
            }
        }
    }
}

@Composable
fun StepperRightBarSingleItem(
    modifier: Modifier = Modifier,
    title: String = "",
    isCurrent: Boolean = false,
    isEndNode: Boolean = false,
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            color = if (isCurrent) Color(0xff06a2c2) else Color.DarkGray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        if (!isEndNode) {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
