package com.example.stepmotion

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HorizontalShapedStepper(
    modifier: Modifier = Modifier,
    countingList: List<Int> = listOf(1, 2, 3),
    textList: List<String> = listOf("Pending", "In Progress", "Successful"),
    shape: Shape = CircleShape,
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
        ShapedStepperTopBar(
            items = countingList,
            shape = shape,
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
fun ShapedStepperTopBar(
    modifier: Modifier = Modifier,
    items: List<Int>,
    shape: Shape = CircleShape,
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
                TopShapedStepperSingleItem(
                    text = items[index].toString(),
                    isEndNode = true,
                    shape = shape,
                    isSelected = selectedItemIndex >= index,
                    nonSelectedItemColor = nonSelectedItemColor,
                    selectedItemColor = selectedItemColor
                )
            } else {
                TopShapedStepperSingleItem(
                    modifier = Modifier.weight(1f),
                    text = items[index].toString(),
                    shape = shape,
                    isSelected = selectedItemIndex >= index,
                    nonSelectedItemColor = nonSelectedItemColor,
                    selectedItemColor = selectedItemColor
                )
            }
        }
    }

}

@Composable
fun TopShapedStepperSingleItem(
    modifier: Modifier = Modifier,
    text: String = "",
    shape: Shape = CircleShape,
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
        Card(
            modifier = Modifier.size(40.dp),
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = if (isSelected) selectedItemColor else Color.Transparent),
            border = BorderStroke(width = 1.dp,color = if (isSelected) selectedItemColor else nonSelectedItemColor)
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
