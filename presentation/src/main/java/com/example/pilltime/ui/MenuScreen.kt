package com.example.pilltime.ui

import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.Start
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pilltime.ui.theme.TextColor_White
import com.google.android.gms.oss.licenses.OssLicensesActivity

/**
 * 2023-11-07
 * pureum
 */
@Preview
@Composable
fun MenuScreen(){
    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = 30.dp, horizontal = 30.dp)
    ) {
        val context = LocalContext.current
        Text(text = "더보기", color = TextColor_White, fontSize = 35.sp)
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp, vertical = 30.dp)
        ) {
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    makeToast(context, "라이센스")
                }) {
                Text(text = "라이센스", color = TextColor_White, fontSize = 25.sp, modifier = Modifier.padding(vertical = 20.dp),
                        textAlign = TextAlign.Left)

            }
            TextButton(
                modifier = Modifier.fillMaxWidth().align(alignment = Alignment.Start),
                onClick = { makeToast(context, "현재 버전은 1.0.0 입니다.") }) {
                Text(text = "버전정보", color = TextColor_White, fontSize = 25.sp, modifier = Modifier.padding(vertical = 20.dp))

            }
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { makeToast(context, "lpm083100@gmail.com 으로 연락 주세요!") }) {
                Text(text = "문의하기", color = TextColor_White, fontSize = 25.sp, modifier = Modifier.padding(vertical = 20.dp))

            }
        }
    }
}