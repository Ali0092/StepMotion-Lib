package com.example.stepmotion

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HorizontalStepper(
    modifier: Modifier = Modifier,
    countingList: List<Int> = listOf(1, 2, 3),
    textList: List<String> = listOf("Pending", "In Progress", "Successful"),
    selectedItemIndex: Int = 0,
    nonSelectedItemColor: Color = Color.Gray,
    selectedItemColor: Color = Color(0xff06a2c2),
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier
            .fillMaxSize()
            .padding(top = 60.dp)
            .clickable {
                selectedIndex = (selectedIndex + 1) % countingList.size
            }, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StepperTopBar(
            items = countingList,
            selectedItemColor = selectedItemColor,
            nonSelectedItemColor = nonSelectedItemColor,
            selectedItemIndex = selectedIndex
        )
        StepperBottomBar(
            items = textList,
            selectedItemIndex = selectedIndex,
            nonSelectedItemColor = Color.Black,
            selectedItemColor = selectedItemColor)

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
                    isSelected = selectedItemIndex >= index,
                    nonSelectedItemColor = nonSelectedItemColor,
                    selectedItemColor = selectedItemColor
                )
            } else {
                TopStepperSingleItem(
                    modifier = Modifier.weight(1f),
                    text = items[index].toString(),
                    isSelected = selectedItemIndex >= index,
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
    isSelected: Boolean = false,
    nonSelectedItemColor: Color = Color.LightGray,
    selectedItemColor: Color = Color.Blue,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(40.dp)
                .border(
                    width = 2.dp,
                    color = if (isSelected) selectedItemColor else nonSelectedItemColor,
                    shape = CircleShape
                )
        ) {
            Card(
                modifier = Modifier.padding(6.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = if (isSelected) selectedItemColor else nonSelectedItemColor)
            ) {

                Box(Modifier.fillMaxSize()) {
                    Text(
                        text = text,
                        modifier = Modifier.align(Alignment.Center),
                        color = if(isSelected) Color.White else Color.Black,
                        fontSize = 18.sp
                    )
                }

            }
        }

        if (!isEndNode) {
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(2.dp)
                    .background(
                        if (isSelected) selectedItemColor else nonSelectedItemColor
                    )
            )
        }
    }
}

@Composable
fun StepperBottomBar(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedItemIndex: Int = 0,
    nonSelectedItemColor: Color = Color.LightGray,
    selectedItemColor: Color = Color.Blue,

    ) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        items.forEachIndexed { index, it ->
            if (index == items.lastIndex) {
                BottomStepperSingleItem(
                    text = items[index].toString(),
                    isEndNode = true,
                    isSelected = selectedItemIndex >= index,
                    nonSelectedItemColor = nonSelectedItemColor,
                    selectedItemColor = selectedItemColor
                )
            } else {
                BottomStepperSingleItem(
                    modifier = Modifier.weight(1f),
                    text = items[index].toString(),
                    isSelected = selectedItemIndex >= index,
                    nonSelectedItemColor = nonSelectedItemColor,
                    selectedItemColor = selectedItemColor
                )
            }
        }
    }
}

@Composable
fun BottomStepperSingleItem(
    modifier: Modifier = Modifier,
    text: String = "",
    isEndNode: Boolean = false,
    isSelected: Boolean = false,
    nonSelectedItemColor: Color = Color.LightGray,
    selectedItemColor: Color = Color.Blue,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) selectedItemColor else nonSelectedItemColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        if (!isEndNode) {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
