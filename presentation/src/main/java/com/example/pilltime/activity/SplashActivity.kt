package com.example.pilltime.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pilltime.R
import com.example.pilltime.ui.theme.BlueSky
import com.example.pilltime.ui.theme.PillTimeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

/**
 * 2023-11-07
 * pureum
 */

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PillTimeTheme {
                SplashScreen()
            }
        }
    }
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    val activity = LocalContext.current as Activity
    val intent = Intent(activity.applicationContext, MainActivity::class.java)
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = activity) {
        isVisible = true
        delay(1200L)
        activity.startActivity(intent)
        activity.finish()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueSky),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(500, easing = LinearEasing))
        ) {
            Image(
                painter = painterResource(id = R.drawable.pill_time_logo),
                modifier = modifier.size(width = 100.dp, height = 100.dp),
                contentDescription = ""
            )
        }
    }

}