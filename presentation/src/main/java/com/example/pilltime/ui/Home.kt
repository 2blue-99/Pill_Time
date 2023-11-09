@file:OptIn(ExperimentalPermissionsApi::class)

package com.example.pilltime.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
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
import androidx.compose.ui.graphics.vector.ImageVector
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
enum class ScreenType(@StringRes val title: Int) {
    Camera(title = R.string.Camera),
    Menu(title = R.string.Menu),
    Add(title = R.string.Add),
    Home(title = R.string.Home)
}

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
) {
    val cameraPermissionState: PermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =
        ScreenType.valueOf(backStackEntry?.destination?.route ?: ScreenType.Menu.name)
    Scaffold(
        bottomBar = {
            if(currentScreen != ScreenType.Camera)
                SootheBottomNavigation(
                    onChangeNav = {
                        navController.navigate(it.toString())
                    }
                )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenType.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ScreenType.Home.name) {
                MainScreen(list = listOf("광고 1", "광고 2", "광고 3", "광고 4", "광고 4", "광고 4"))
            }
            composable(ScreenType.Camera.name) {
                if (cameraPermissionState.hasPermission)
                    CameraScreen()
                else
                    cameraPermissionState.launchPermissionRequest()
            }
            composable(ScreenType.Add.name) {
                AddScreen()
            }
            composable(ScreenType.Menu.name) {
                MenuScreen()
            }
        }
    }
}

@Composable
private fun SootheBottomNavigation(
    modifier: Modifier = Modifier,
    onChangeNav: (ScreenType) -> Unit,
) {
    var selectedType by rememberSaveable { mutableStateOf(ScreenType.Home) }
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {

        MyNavigationBarItem(
            icon = Icons.Default.AccountBox,
            text = "Camera",
            inputType = ScreenType.Camera,
            selectedType = selectedType,
            onChange = {
                selectedType = ScreenType.Camera
                onChangeNav(ScreenType.Camera)
            }
        )

        MyNavigationBarItem(
            icon = Icons.Default.Notifications,
            text = "Home",
            inputType = ScreenType.Home,
            selectedType = selectedType,
            onChange = {
                selectedType = ScreenType.Home
                onChangeNav(ScreenType.Home)
            }
        )

        MyNavigationBarItem(
            icon = Icons.Default.Menu,
            text = "Menu",
            inputType = ScreenType.Menu,
            selectedType = selectedType,
            onChange = {
                selectedType = ScreenType.Menu
                onChangeNav(ScreenType.Menu)
            }
        )
//        NavigationBarItem(
//            icon = {
//                Icon(
//                    imageVector = Icons.Default.Notifications,
//                    contentDescription = null
//                )
//            },
//            label = {
//                Text("Camera")
//            },
//            selected = selectedType == ScreenType.Camera,
//            onClick = {
//                selectedType = ScreenType.Camera
//                onChangeScreen(selectedType)
//            }
//        )
//        NavigationBarItem(
//            icon = {
//                Icon(
//                    imageVector = Icons.Default.Home,
//                    contentDescription = null
//                )
//            },
//            label = {
//                Text("Home")
//            },
//            selected = selectedType == ScreenType.Home,
//            onClick = {
//                selectedType = ScreenType.Home
//                onChangeScreen(selectedType)
//            }
//        )
//        NavigationBarItem(
//            icon = {
//                Icon(
//                    imageVector = Icons.Default.Menu,
//                    contentDescription = null
//                )
//            },
//            label = {
//                Text("Menu")
//            },
//            selected = selectedType == ScreenType.Menu,
//            onClick = {
//                selectedType = ScreenType.Menu
//                onChangeScreen(selectedType)
//            }
//        )
    }
}

@Composable
fun RowScope.MyNavigationBarItem(
    icon: ImageVector,
    text: String,
    inputType: ScreenType,
    selectedType: ScreenType,
    onChange: (ScreenType) -> Unit,
) {
    NavigationBarItem(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        label = {
            Text(text)
        },
        selected = selectedType == inputType,
        onClick = {
            onChange(inputType)
        }
    )
}