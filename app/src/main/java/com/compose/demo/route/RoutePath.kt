package com.compose.demo.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 *
 * @author zrh
 * @date 2023/11/13
 *
 */
object RoutePath {
    const val Login = "login"
    const val Home = "home"
    const val Room = "room"
}

val LocalNavController = staticCompositionLocalOf<NavHostController> { error("No NavController provided!") }

@Composable
fun getNavController(): NavHostController {
    if (LocalInspectionMode.current) return rememberNavController()
    return LocalNavController.current
}