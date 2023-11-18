@file:OptIn(ExperimentalFoundationApi::class)

package com.example.pilltime.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pilltime.viewModel.MyViewModel
import com.example.domain.model.local.NoticeData
import com.example.pilltime.ui.theme.Background_Dark
import com.example.pilltime.ui.theme.Button_Click_Green
import com.example.pilltime.ui.theme.Button_NonClick_Gray
import com.example.pilltime.ui.theme.Button_NonClick_White
import com.example.pilltime.ui.theme.ClickOrange
import com.example.pilltime.ui.theme.Component_Click_Pink
import com.example.pilltime.ui.theme.Component_NonClick_Gray
import com.example.pilltime.ui.theme.TextColor_White

/**
 * 2023-11-07
 * pureum
 */
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    list: List<String>,
    viewModel: MyViewModel = hiltViewModel(),
) {
    Column(
        modifier = modifier
            .background(Background_Dark)
            .fillMaxSize()
    ) {
//        TopAdView(list = list)

        NoticeText()

        AddButton(viewModel = viewModel)

        NoticeView(viewModel = viewModel)
    }
}
@Composable
fun TopAdView(
    modifier: Modifier = Modifier,
    list: List<String>
){
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
    ) {
        items(list) { it ->
            AdComponent(it)
        }
    }
}
@Composable
fun AdComponent(
    list: String,
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
            .height(75.dp)
            .border(1.5.dp, Brush.horizontalGradient(gradientColors), RectangleShape)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = list,
            modifier = modifier,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
@Composable
fun NoticeText(
    modifier: Modifier = Modifier
){
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
}
@Composable
fun AddButton(
    viewModel: MyViewModel
){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(end = 5.dp, bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        IconButton(
            onClick = { viewModel.addNotice() }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                tint = Button_NonClick_Gray,
                contentDescription = "",
                modifier = Modifier.size(50.dp, 50.dp)
            )
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoticeView(
    viewModel: MyViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
){
    val notices by viewModel.uiState.collectAsState()
    if (notices.isNotEmpty()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = modifier
                .fillMaxHeight()
                .padding(horizontal = 5.dp)
        ) {
            items(notices, key = { it.submitTime }) { notice ->
                NoticeComponent(
                    modifier = Modifier.animateItemPlacement(),
                    data = notice,
                    deleteNotice = { viewModel.deleteNotice(notice.submitTime) },
                )
            }
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "알람을 추가해주세요~", color = Color.White, fontSize = 25.sp)
        }
    }
}

@Preview
@Composable
fun TestNotice(){
    NoticeComponent(deleteNotice = {})
}
@Composable
fun NoticeComponent(
    data: NoticeData = NoticeData(),
    modifier: Modifier = Modifier,
    deleteNotice: () -> Unit,
) {
    var isChecked by rememberSaveable { mutableStateOf(data.isChecked) }
    var isClick by rememberSaveable { mutableStateOf(false) }
    val surfaceColor by animateColorAsState(
        targetValue = if (isClick) Component_Click_Pink else Component_NonClick_Gray, label = ""
    )
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .fillMaxWidth()
            .clickable(
                onClick = { isClick = !isClick }
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .background(surfaceColor)
                .padding(vertical = 20.dp, horizontal = 15.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(text = "시간", fontSize = 15.sp, color = TextColor_White, modifier = Modifier.padding(start =8.dp)  )
                    Spacer(modifier = modifier.width(6.dp))
                    Text(text = "${data.time}", fontSize = 30.sp, color = TextColor_White)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                ) {
                    Text(
                        text = "${data.month}월 ${data.day}일",
                        color = TextColor_White
                    )
                    // TODO 클릭 시 몇시간 후에 울리는지 알림
                    Checkbox(
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = Button_NonClick_White,
                            checkedColor = Button_Click_Green
                        ),
                        checked = isChecked,
                        onCheckedChange = {
                            isChecked = !isChecked
                        }
                    )
                }
            }
            if (isClick) {
                deleteNoticeButton(deleteNotice = {
                    deleteNotice()
                    isClick = false
                })
            }
        }
    }
}

@Composable
fun deleteNoticeButton(
    modifier: Modifier = Modifier,
    deleteNotice: () -> Unit
){
    Spacer(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 25.dp, vertical = 5.dp)
        .height(1.5.dp)
        .background(Button_NonClick_White)
    )
    Row(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val context = LocalContext.current
        TextButton(
            onClick = {makeToast(context, "수정하기")},
        ){
            Text(text = "수정하기", color = TextColor_White, fontSize = 15.sp)
        }
        TextButton(
            onClick = { deleteNotice() },
        ){
            Text(text = "삭제하기", color = TextColor_White, fontSize = 15.sp)
        }
//        IconButton(
//            onClick = { deleteNotice() }
//        ) {
//            Icon(
//                imageVector = Icons.Default.Clear,
//                modifier = modifier.size(40.dp, 40.dp),
//                contentDescription = ""
//            )
//        }
    }
}

fun makeToast(context: Context, text: String) {
    Toast.makeText(context, "$text", Toast.LENGTH_SHORT).show()
}
