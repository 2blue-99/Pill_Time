@file:OptIn(ExperimentalPermissionsApi::class)

package com.example.pilltime.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pilltime.R
import com.example.pilltime.viewModel.MyViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

/**
 * 2023-11-07
 * pureum
 */
enum class PillTimeScreen(@StringRes val title: Int) {
    Camera(title = R.string.Camera),
    Menu(title = R.string.Menu),
    Add(title = R.string.Add),
    Home(title = R.string.Add),
}

@Composable
fun HomeScreen(
    viewModel: MyViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val cameraPermissionState : PermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen =
        PillTimeScreen.valueOf(backStackEntry?.destination?.route ?: PillTimeScreen.Menu.name)
    Scaffold(
        bottomBar = { SootheBottomNavigation(
            navController = navController,
            onClick = {
                navController.navigate(it.toString())
                Log.e("TAG,", "HomeScreen: $it", )
            }
        ) },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = PillTimeScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(PillTimeScreen.Home.name) {
                MainScreen(list = listOf("광고 1","광고 2","광고 3","광고 4","광고 4","광고 4"))
            }
            composable(PillTimeScreen.Camera.name) {
                if(cameraPermissionState.hasPermission)
                    CameraScreen()
                else{
                    cameraPermissionState.launchPermissionRequest()
                    Log.e("TAG", "HomeScreen: 카메라 권한 없음~", )
                }
            }
            composable(PillTimeScreen.Add.name) {
                AddScreen()
            }
            composable(PillTimeScreen.Menu.name) {
                MenuScreen()
            }
        }
    }
}
@Composable
private fun SootheBottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onClick : (PillTimeScreen) -> Unit
) {
    var isSelected by rememberSaveable { mutableStateOf(PillTimeScreen.Home) }
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null
                )
            },
            label = {
                Text("Camera")
            },
            selected = isSelected == PillTimeScreen.Camera,
            onClick = {
                isSelected = PillTimeScreen.Camera
                onClick(isSelected)
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text("Home")
            },
            selected = isSelected == PillTimeScreen.Home,
            onClick = {
                isSelected = PillTimeScreen.Home
                onClick(isSelected)
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null
                )
            },
            label = {
                Text("Menu")
            },
            selected = isSelected == PillTimeScreen.Menu,
            onClick = {
                isSelected = PillTimeScreen.Menu
                onClick(isSelected)
            }
        )
    }
}