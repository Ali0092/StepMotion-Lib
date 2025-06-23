package com.example.stepmotionlib

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HorizontalSimpleStepper(
    modifier: Modifier = Modifier,
    countingList: List<Int>,
    titleList: List<String>,
    selectedItemIndex: Int ,
    nonSelectedItemColor: Color ,
    selectedItemColor: Color,
    nonSelectedTitleColor: Color,
    selectedTitleColor: Color
) {

    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
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
        items.forEachIndexed { index, it ->
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
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .size(35.dp)
                .border(
                    width = if (isCurrent) 2.dp else 0.dp,
                    color =  if (isCurrent) selectedItemColor else Color.Transparent,
                    shape = CircleShape
                )
        ) {
            Card(
                modifier = Modifier.padding(if(isCurrent) 4.dp else 0.dp),
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
                    .padding(horizontal = 4.dp)
                    .weight(1f)
                    .height(2.dp)
                    .background(
                        if (isCurrent || isPrevious) selectedItemColor else nonSelectedItemColor
                    )
            )
        }
    }
}

@Composable
fun StepperBottomBar(
    modifier: Modifier = Modifier,
    titleList: List<String>,
    selectedIndex: Int = 0,
    nonSelectedTitleColor: Color,
    selectedTitleColor : Color
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        titleList.forEachIndexed { index, it ->
            if (index == titleList.lastIndex) {
                BottomStepperSingleItem(
                    title = titleList[index].toString(),
                    isEndNode = true,
                    isCurrent =  index <= selectedIndex,
                    nonSelectedItemColor = nonSelectedTitleColor,
                    selectedTitleColor = selectedTitleColor
                )
            } else {
                BottomStepperSingleItem(
                    modifier = Modifier.weight(1f),
                    title = titleList[index].toString(),
                    isCurrent =  index <= selectedIndex,
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
    selectedTitleColor : Color
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = if (isCurrent) selectedTitleColor else nonSelectedItemColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        if (!isEndNode) {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
