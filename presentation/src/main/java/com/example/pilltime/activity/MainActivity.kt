package com.example.pilltime.activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pilltime.ui.theme.PillTimeTheme
import com.example.pilltime.ui.HomeScreen
import com.google.android.gms.oss.licenses.OssLicensesActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PillTimeTheme() {
                HomeScreen()
            }
        }
    }
}

