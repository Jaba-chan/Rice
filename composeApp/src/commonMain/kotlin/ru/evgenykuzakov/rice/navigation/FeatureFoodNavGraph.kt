package ru.evgenykuzakov.rice.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.featureFoodNavGraph(
    showFoodScreenContent: @Composable () -> Unit,
    searchProductScreenContent: @Composable () -> Unit,
    addFoodScreenContent: @Composable () -> Unit,
){
    navigation(
        startDestination = Screen.ShowFoodsScreen.route,
        route = Screen.FoodScreen.route
    ){
        composable(Screen.ShowFoodsScreen.route) {
            showFoodScreenContent()
        }
        composable(Screen.SearchProductScreen.route) {
            searchProductScreenContent()
        }
        composable(Screen.AddFoodScreen.route) {
            addFoodScreenContent()
        }
    }
}