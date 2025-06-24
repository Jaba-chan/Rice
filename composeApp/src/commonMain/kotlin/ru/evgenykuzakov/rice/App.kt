package ru.evgenykuzakov.rice

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import rice.composeapp.generated.resources.Res
import rice.composeapp.generated.resources.calendar_icon_cd
import rice.composeapp.generated.resources.ic_calendar
import ru.evgenykuzakov.designsystem.RiceTheme
import ru.evgenykuzakov.shared.ui.DatePickerModalInput
import ru.evgenykuzakov.shared.util.getFormattedDate
import ru.evgenykuzakov.shared.util.getFormattedToDayOfWeekDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(
    viewModel: MainActivityViewModel = koinViewModel()
) {
    RiceTheme {
        KoinContext {
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
                    val roundSize = 20.dp
                    Column(
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 0.dp,
                                    bottomStart = roundSize,
                                    bottomEnd = roundSize
                                )
                            )
                    ) {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = getFormattedDate(state.selectedDate),
                                    style = MaterialTheme.typography.displayMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                            }, actions = {
                                IconButton(content = {
                                    Icon(
                                        painter = painterResource(Res.drawable.ic_calendar),
                                        contentDescription = stringResource(Res.string.calendar_icon_cd),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                    onClick = {
                                        viewModel.setDatePickerVisibility(true)
                                    }
                                )
                            }
                        )
                        val selectedPos = state.selectedDate.dayOfWeek.ordinal
                        TabRow(
                            selectedTabIndex = selectedPos,
                            indicator = { },
                            divider = { }

                        ) {
                            state.weakDays.forEachIndexed { index, date ->
                                RoundedTab(
                                    onClick = {
                                        viewModel.selectDate(date)
                                        println(selectedPos)
                                        println(state.selectedDate.dayOfWeek)
                                    },
                                    text = {
                                        Text(
                                            text = getFormattedToDayOfWeekDate(date),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (selectedPos == index)
                                                MaterialTheme.colorScheme.primary
                                            else
                                                MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            ) {

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

