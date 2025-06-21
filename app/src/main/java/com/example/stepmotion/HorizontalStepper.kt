package com.example.stepmotion

import android.R
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
    countingList: List<Int> = listOf(1, 2, 3),
    titleList: List<String> = listOf("Pending", "In Progress", "Successful"),
    selectedIndex: Int = 0,
    nonSelectedItemColor: Color = Color.Gray,
    selectedItemColor: Color = Color(0xff06a2c2),
) {

    Column(
        modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StepperTopBar(
            items = countingList,
            selectedItemColor = selectedItemColor,
            nonSelectedItemColor = nonSelectedItemColor,
            selectedItemIndex = selectedIndex
        )
        StepperBottomBar(
            titleList = titleList,
            selectedIndex = selectedIndex
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
            .padding(horizontal = 16.dp)
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
    val nonSelectedColor = selectedItemColor.copy(alpha = 0.5f)
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
                modifier = Modifier.padding(if(isCurrent) 6.dp else 0.dp),
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
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        titleList.forEachIndexed { index, it ->
            if (index == titleList.lastIndex) {
                BottomStepperSingleItem(
                    title = titleList[index].toString(),
                    isEndNode = true,
                    isCurrent =  index <= selectedIndex
                )
            } else {
                BottomStepperSingleItem(
                    modifier = Modifier.weight(1f),
                    title = titleList[index].toString(),
                    isCurrent =  index <= selectedIndex
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
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = if (isCurrent) Color(0xff06a2c2) else  Color.DarkGray,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        if (!isEndNode) {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
