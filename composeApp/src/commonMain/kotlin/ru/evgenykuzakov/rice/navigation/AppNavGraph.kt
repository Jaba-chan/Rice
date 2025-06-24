package ru.evgenykuzakov.rice.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import ru.evgenykuzakov.rice.MainActivityViewModel


@Composable
fun AppNavGraph(
    viewModel: MainActivityViewModel = koinViewModel(),
    navHostController: NavHostController,
    signInScreenContent: @Composable () -> Unit,
    signUpScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
    foodScreenContent: @Composable () -> Unit,
    trainingScreenContent: @Composable () -> Unit,
    statisticScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.FoodScreen.route

    ) {
        composable(Screen.SignInScreen.route) {
            signInScreenContent()
        }
        composable(Screen.SignUpScreen.route) {
            signUpScreenContent()
        }
        composable(Screen.ProfileScreen.route) {
            profileScreenContent()
        }
        composable(Screen.FoodScreen.route) {
            foodScreenContent()
        }
        composable(Screen.TrainingScreen.route) {
            trainingScreenContent()
        }
        composable(Screen.StatisticScreen.route) {
            statisticScreenContent()
        }
        bottomNavGraph(
            profileScreenContent = profileScreenContent,
            foodScreenContent = foodScreenContent,
            trainingScreenContent = trainingScreenContent,
            statisticScreenContent = statisticScreenContent
        )
    }
}