@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pilltime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pilltime.ui.MyViewModel
import com.example.pilltime.ui.theme.PillTimeTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pilltime.ui.AddScreen
import com.example.pilltime.ui.HomeScreen
import com.example.pilltime.ui.LoadingScreen

enum class PillTimeScreen(@StringRes val title: Int) {
    Loading(title = R.string.Loading),
    Home(title = R.string.Home),
    Add(title = R.string.Add),
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PillTimeTheme {
              PillTimeApp()
            }
        }
    }
}
@Composable
fun PillTimeApp(
    viewModel: MyViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = PillTimeScreen.valueOf(
        backStackEntry?.destination?.route ?: PillTimeScreen.Loading.name
    )
    PillTimeTheme {
        Scaffold(

        ) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = PillTimeScreen.Loading.name,
                modifier = Modifier.padding(innerPadding)
            ){
                composable(PillTimeScreen.Loading.name) {
                    LoadingScreen(
                        onNextEvent = {
                            navController.navigate(PillTimeScreen.Home.name)
                        }
                    )
                }

                composable(PillTimeScreen.Home.name) {
                    AddScreen(

                    )
                }

                composable(PillTimeScreen.Add.name) {
                    HomeScreen()
                }


            }
        }
    }
}