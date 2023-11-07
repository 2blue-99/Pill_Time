package com.example.pilltime.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 2023-11-07
 * pureum
 */

@Composable
fun LoadingScreen(
    onNextEvent: () -> Unit,
    modifier: Modifier = Modifier
){
    LaunchedEffect(key1 = true){
        delay(3000L)
        onNextEvent()
    }

    Column(
        Modifier.fillMaxSize()
    ) {
        Text(text = "여긴 로딩")
        Button(
            onClick = {  }
        ) {
            Text(text = "눌러봐용")
        }
    }
}