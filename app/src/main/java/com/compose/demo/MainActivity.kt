package com.compose.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.compose.demo.page.HomePage
import com.compose.demo.page.LoginPage
import com.compose.demo.page.RoomPage
import com.compose.demo.route.LocalNavController
import com.compose.demo.route.RoutePath
import com.compose.demo.ui.theme.ComposeDemoTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize().navigationBarsPadding(), color = MaterialTheme.colors.background) {
                    val navController: NavHostController = rememberAnimatedNavController()
                    CompositionLocalProvider(LocalNavController provides navController) {
                        AppNavigator()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigator(){
    val navController: NavHostController = LocalNavController.current
    AnimatedNavHost(
        navController = navController,
        startDestination = RoutePath.Login,
        enterTransition = {slideInHorizontally(initialOffsetX = {it}, animationSpec = tween(500))},
        exitTransition = {slideOutHorizontally(targetOffsetX = {-it}, animationSpec = tween(500))},
        popEnterTransition = {slideInHorizontally(initialOffsetX = {-it}, animationSpec = tween(500))},
        popExitTransition = {slideOutHorizontally(targetOffsetX = {it}, animationSpec = tween(500))}
    ){
        composable(RoutePath.Login){ LoginPage() }
        composable(RoutePath.Home){ HomePage() }
        composable(RoutePath.Room){ RoomPage() }
    }
}