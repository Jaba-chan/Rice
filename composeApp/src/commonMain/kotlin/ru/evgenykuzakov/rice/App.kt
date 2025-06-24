package ru.evgenykuzakov.rice

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import ru.evgenykuzakov.designsystem.RiceTheme
import ru.evgenykuzakov.rice.navigation.AppNavGraph
import ru.evgenykuzakov.rice.navigation.NavigationItem
import ru.evgenykuzakov.rice.navigation.NavigationState
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

            val items = listOf(
                NavigationItem.Person,
                NavigationItem.Food,
                NavigationItem.Training,
                NavigationItem.Statistic
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
                    TopBar(
                        onSetDatePickerVisibility = viewModel::setDatePickerVisibility,
                        onSelectDate = viewModel::selectDate,
                        state = state
                    )
                },
                bottomBar = {

                }
            ) {

                AppNavGraph(
                    navHostController = navController,
                    signInScreenContent = {},
                    signUpScreenContent = {},
                    profileScreenContent = {},
                    foodScreenContent = {},
                    trainingScreenContent = {},
                    statisticScreenContent = {}
                )
            }
        }
    }
}

@Composable
fun RoundedTab(
    text: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        text()
    }
}

