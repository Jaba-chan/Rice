package ru.evgenykuzakov.shared.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import rice.shared.generated.resources.Res
import rice.shared.generated.resources.cancel
import rice.shared.generated.resources.ok
import ru.evgenykuzakov.shared.util.formatMillisToDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalInput(
    onDateSelected: (LocalDate?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                DialogButton(
                    onClick = {
                        onDateSelected(datePickerState.selectedDateMillis?.formatMillisToDate())
                        onDismiss()
                    },
                    text = stringResource(Res.string.ok)
                )
            }
        },
        dismissButton = {
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                DialogButton(
                    onClick = onDismiss,
                    text = stringResource(Res.string.cancel)
                )
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
        }

    }
}

@Composable
private fun DialogButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        content = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    )
}
