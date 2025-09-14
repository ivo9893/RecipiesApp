package com.example.myrecipies

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myrecipies.ui.theme.MyRecipiesTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myrecipies.presentation.ui.screens.LoginViewModel

import androidx.hilt.navigation.compose.hiltViewModel
import com.example.login.presentation.LoginLayout
import com.example.myrecipies.presentation.ui.screens.BottomNavBar
import com.example.myrecipies.presentation.ui.screens.BottomNavScreen
import com.example.myrecipies.presentation.ui.screens.CreateRecipeLayout
import com.example.myrecipies.presentation.ui.screens.CreateRecipeViewModel
import com.example.myrecipies.presentation.ui.screens.RecipeGrid
import com.example.myrecipies.presentation.ui.screens.RecipiesLayout
import com.example.myrecipies.presentation.ui.screens.RecipiesViewModel
import com.example.myrecipies.presentation.ui.screens.RegisterScreen
import com.example.myrecipies.presentation.ui.screens.RegisterViewModel
import com.example.myrecipies.presentation.ui.screens.SplashScreen
import com.example.myrecipies.presentation.ui.screens.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyRecipiesTheme {
                MainScreen()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splashscreen") {

        // Splash Screen
        composable("splashscreen") {
            val splashViewModel: SplashViewModel = hiltViewModel()
            SplashScreen(viewModel = splashViewModel, navigateToLogin = {
                navController.navigate("loginscreen") {
                    popUpTo("splashscreen") { inclusive = true }
                }
            })
        }

        // Login Screen
        composable("loginscreen") {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginLayout(
                viewModel = viewModel,
                navigateToRegister = {
                    navController.navigate("registerscreen") {
                        popUpTo(0)
                    }
                },
                navigateToRecipies = {
                    navController.navigate("recipiescreen")
                }
            )
        }

        // Register Screen
        composable("registerscreen") {
            val viewModel: RegisterViewModel = hiltViewModel()
            RegisterScreen(
                viewModel = viewModel,
                navigateToLogin = {
                    navController.navigate("loginscreen") {
                        popUpTo(0)
                    }
                }
            )
        }

        // Recipes Screen with Bottom Navigation
        composable("recipiescreen") {
            val navControllerBottom = rememberNavController()
            Scaffold(
                bottomBar = { BottomNavBar(navControllerBottom) }
            ) { innerPadding ->
                NavHost(
                    navController = navControllerBottom,
                    startDestination = BottomNavScreen.Recipes.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(BottomNavScreen.Recipes.route) {
                        val viewModel: RecipiesViewModel = hiltViewModel()
                        RecipiesLayout(viewModel)
                    }
                    composable(BottomNavScreen.CreateRecipe.route) {
                        val viewModel: CreateRecipeViewModel = hiltViewModel()
                        CreateRecipeLayout(viewModel)
                    }
                }
            }
        }
    }
}