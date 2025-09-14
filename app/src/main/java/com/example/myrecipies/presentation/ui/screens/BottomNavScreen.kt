package com.example.myrecipies.presentation.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myrecipies.R

sealed class BottomNavScreen(val route: String, val label: String, @DrawableRes val iconRes: Int) {
    object Recipes : BottomNavScreen("recipes", "My Recipes", R.drawable.recipies)
    object CreateRecipe : BottomNavScreen("profile", "Create Recipie", R.drawable.create_recipe)
}