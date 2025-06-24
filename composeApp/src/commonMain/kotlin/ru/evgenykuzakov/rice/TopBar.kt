package ru.evgenykuzakov.rice

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import rice.composeapp.generated.resources.Res
import rice.composeapp.generated.resources.calendar_icon_cd
import rice.composeapp.generated.resources.ic_calendar
import rice.composeapp.generated.resources.ic_check_circle
import rice.composeapp.generated.resources.tab_icon_cd
import ru.evgenykuzakov.shared.util.getFormattedDate
import ru.evgenykuzakov.shared.util.getFormattedToDayOfWeekDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onSetDatePickerVisibility: (Boolean) -> Unit,
    onSelectDate: (LocalDate) -> Unit,
    state: AppUIState,
    roundSize: Dp = 20.dp
) {
    Box(
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
                        onSetDatePickerVisibility(true)
                    }
                )
            }
        )
        val selectedPos = state.selectedDate.dayOfWeek.ordinal
        TabRow(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 72.dp),
            selectedTabIndex = selectedPos,
            indicator = { },
            divider = { }

        ) {
            state.weakDays.forEachIndexed { index, date ->
                RoundedTab(
                    onClick = {
                        onSelectDate(date)
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
                    },
                    icon = {
                        Icon(
                            painter = painterResource(Res.drawable.ic_check_circle),
                            contentDescription = stringResource(Res.string.tab_icon_cd),
                            tint = Color.Red
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun RoundedTab(
    text: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon()
        text()
    }
}