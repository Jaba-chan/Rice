package ru.evgenykuzakov.shared.ui

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
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
            DialogButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis?.formatMillisToDate())
                    onDismiss()
                },
                text = stringResource(Res.string.ok)
            )
        },
        dismissButton = {
            DialogButton(
                onClick = onDismiss,
                text = stringResource(Res.string.cancel)
            )
        },
        colors = DatePickerDefaults.colors(
            containerColor =  MaterialTheme.colorScheme.surface
        )
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor =  MaterialTheme.colorScheme.surface,
            )
        )
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
