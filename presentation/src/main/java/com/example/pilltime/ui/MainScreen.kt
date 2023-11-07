package com.example.pilltime.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pilltime.ui.theme.BlueSky

/**
 * 2023-11-07
 * pureum
 */

@Composable
fun test() {
    MainScreen(list = listOf("광고 1", "faasfdasfsfssdf", "dfasdf"))
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    list: List<String>,
) {
    Column(
        modifier = modifier
            .background(BlueSky)
            .fillMaxSize()
    ) {
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp),
        ) {
            items(list) { item ->
                AdCard(item)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = modifier.padding(vertical = 50.dp),
                fontSize = 25.sp,
                text = "30분 후에 알람이 울립니다.",
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
        Row(
            Modifier.fillMaxWidth().padding(end = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "", Modifier.size(50.dp,50.dp))
            }
        }
        LazyColumn(
            verticalArrangement  = Arrangement.spacedBy(10.dp),
            modifier = modifier
                .fillMaxHeight()
                .padding(horizontal = 5.dp)
        ) {
            items(list) { item ->
                NoticeComponent(item)
            }
        }
    }
}

@Preview
@Composable
fun test2() {
    AdCard("hello")
}

@Composable
fun AdCard(
    name: String,
    modifier: Modifier = Modifier,
) {
    val gradientColors = listOf(
        Color.Red,
        Color.Magenta,
        Color.Blue,
        Color.Cyan,
        Color.Green,
        Color.Yellow,
        Color.Red
    )
    Row(
        modifier = modifier
            .width(LocalConfiguration.current.screenWidthDp.dp)
            .height(90.dp)
            .border(1.5.dp, Brush.horizontalGradient(gradientColors), RectangleShape)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = name,
            modifier = modifier,
            style = MaterialTheme.typography.bodyMedium
        )


//        Box(modifier = Modifier
//            .width(LocalConfiguration.current.screenWidthDp.dp)
//        ) {
//        }
    }

}



@Composable
fun NoticeComponent(
    list: String,
    modifier: Modifier = Modifier,
) {
    var isChecked by rememberSaveable { mutableStateOf(false) }
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = MaterialTheme.shapes.medium
        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(text = "오전", fontSize = 15.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "8:30", fontSize = 30.sp)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                Text(
                    text = "11월 7일(금)"
                )
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {isChecked = !isChecked}
                )
            }
        }
    }
}
