package ru.evgenykuzakov.rice

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import ru.evgenykuzakov.designsystem.RiceTheme
import ru.evgenykuzakov.food.ShowMealsScreen
import ru.evgenykuzakov.rice.navigation.AppNavGraph
import ru.evgenykuzakov.rice.navigation.NavigationItem
import ru.evgenykuzakov.rice.navigation.NavigationState
import ru.evgenykuzakov.rice.navigation.Screen
import ru.evgenykuzakov.search_food.SearchProductsScreen
import ru.evgenykuzakov.shared.ui.DatePickerModalInput

@Composable
fun App(
    viewModel: MainActivityViewModel = koinViewModel()
) {
    RiceTheme {
        KoinContext {
            val navController = rememberNavController()
            val navState = remember { NavigationState(navController) }

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val navigationItems = listOf(
                NavigationItem.Person,
                NavigationItem.Food,
                NavigationItem.Training,
            )

            val state by viewModel.uiState.collectAsState()

            if (state.isDatePickerVisible) {
                DatePickerModalInput(
                    onDateSelected = {
                        if (it != null) {
                            viewModel.selectDate(it)
                        }
                    },
                    onDismiss = { viewModel.setDatePickerVisibility(false) }
                )
            }

            Scaffold(
                topBar = {
                    if (currentRoute in navigationItems.map { it.screen.route }) {
                    TopBar(
                        onSetDatePickerVisibility = viewModel::setDatePickerVisibility,
                        onSelectDate = viewModel::selectDate,
                        state = state
                    )
                    }
                },
                bottomBar = {
                    if (currentRoute in navigationItems.map { it.screen.route }) {
                        NavBar(
                            items = navigationItems,
                            currentRoute = currentRoute,
                            onNavigate = navState::navigateTo
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navState.navigateTo(Screen.SearchProductScreen.route) },
                        content = {}
                    )
                }
            ) {

                AppNavGraph(
                    navHostController = navController,
                    signInScreenContent = {},
                    signUpScreenContent = {},
                    profileScreenContent = {},
                    foodScreenContent = {
                        ShowMealsScreen()
                    },
                    trainingScreenContent = {},
                    searchProductsScreenContent = {
                        SearchProductsScreen()
                    }
                )
            }
        }
    }
}


