package ru.evgenykuzakov.designsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import rice.core.designsystem.generated.resources.Res
import rice.core.designsystem.generated.resources.cancel
import rice.core.designsystem.generated.resources.clear_search_field_icon_cd
import rice.core.designsystem.generated.resources.ic_clear
import rice.core.designsystem.generated.resources.ic_search
import rice.core.designsystem.generated.resources.ok
import rice.core.designsystem.generated.resources.search_field_icon_cd
import rice.core.designsystem.generated.resources.search_hint
import ru.evgenykuzakov.common.util.formatMillisToDate

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


@Composable
fun SearchField(
    modifier: Modifier = Modifier.clip(CircleShape).fillMaxWidth().padding(16.dp),
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = stringResource(Res.string.search_hint),
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    onClearClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = hint,
                style = textStyle,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        shape = RoundedCornerShape(16.dp),
        textStyle = textStyle,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
            ),
        leadingIcon = {
            Icon(
                painter = painterResource(Res.drawable.ic_search),
                contentDescription = stringResource(Res.string.search_field_icon_cd),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    onClearClick()
                    focusManager.clearFocus()
                }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_clear),
                    contentDescription = stringResource(Res.string.clear_search_field_icon_cd),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        maxLines = 1,
        singleLine = true
    )
}