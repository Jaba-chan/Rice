package ru.evgenykuzakov.rice.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.bottomNavGraph(
    profileScreenContent: @Composable () -> Unit,
    foodScreenContent: @Composable () -> Unit,
    trainingScreenContent: @Composable () -> Unit,
){
    navigation(
        startDestination = Screen.FoodScreen.route,
        route = Screen.HomeScreen.route
    ){
        composable(Screen.ProfileScreen.route) {
            profileScreenContent()
        }
        composable(Screen.FoodScreen.route) {
            foodScreenContent()
        }
        composable(Screen.TrainingScreen.route) {
            trainingScreenContent()
        }
    }
}