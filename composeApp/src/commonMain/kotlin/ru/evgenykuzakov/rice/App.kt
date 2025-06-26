package ru.evgenykuzakov.rice

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import ru.evgenykuzakov.designsystem.theme.RiceTheme
import ru.evgenykuzakov.show_food.ShowMealsScreen
import ru.evgenykuzakov.rice.navigation.AppNavGraph
import ru.evgenykuzakov.rice.navigation.NavigationItem
import ru.evgenykuzakov.rice.navigation.NavigationState
import ru.evgenykuzakov.rice.navigation.Screen
import ru.evgenykuzakov.rice.ui.Fab
import ru.evgenykuzakov.rice.ui.NavBar
import ru.evgenykuzakov.rice.ui.TopBar
import ru.evgenykuzakov.search_food.SearchProductsScreen
import ru.evgenykuzakov.designsystem.DatePickerModalInput

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
                modifier = Modifier
                    .fillMaxSize(),
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
                    if (currentRoute in navigationItems.map { it.screen.route } && currentRoute != Screen.ProfileScreen.route) {
                        Fab(
                            onClick = { navState.navigateTo(Screen.SearchProductScreen.route) }
                        )
                    }
                }
            ) { paddingValues ->
                AppNavGraph(
                    navHostController = navController,
                    signInScreenContent = {},
                    signUpScreenContent = {},
                    profileScreenContent = {},

                    trainingScreenContent = {},
                    addFoodScreenContent = {},
                    showFoodScreenContent = {
                        ShowMealsScreen(
                            selectedDate = state.selectedDate,
                            paddingValues = paddingValues
                        )
                    },
                    searchProductScreenContent = {
                        SearchProductsScreen(
                            paddingValues = paddingValues
                        )
                    }
                )
            }
        }
    }
}


