package ru.evgenykuzakov.rice.navigation

import androidx.navigation.NavController
import ru.evgenykuzakov.rice.navigation.Screen

class NavigationState(
    private val navController: NavController
) {
    fun navigateTo(route: String){
        navController.navigate(route){
            popUpTo(Screen.SignInScreen.route){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    fun navigateToMainScreenForSignedUser(){
        navController.navigate(Screen.HomeScreen.route){
            popUpTo(Screen.SignInScreen.route){
                inclusive = true
            }
        }
    }

}