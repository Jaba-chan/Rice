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
    trainingScreenContent: @Composable () -> Unit,
    showFoodScreenContent: @Composable () -> Unit,
    searchProductScreenContent: @Composable () -> Unit,
    addFoodScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen.route

    ) {
        composable(Screen.SignInScreen.route) {
            signInScreenContent()
        }
        composable(Screen.SignUpScreen.route) {
            signUpScreenContent()
        }
        bottomNavGraph(
            profileScreenContent = profileScreenContent,
            trainingScreenContent = trainingScreenContent,
            showFoodScreenContent = showFoodScreenContent,
            searchProductScreenContent = searchProductScreenContent,
            addFoodScreenContent = addFoodScreenContent
        )
    }
}