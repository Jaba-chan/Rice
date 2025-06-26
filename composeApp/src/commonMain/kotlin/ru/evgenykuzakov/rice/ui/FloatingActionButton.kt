package ru.evgenykuzakov.rice.ui

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import rice.composeapp.generated.resources.Res
import rice.composeapp.generated.resources.fab_icon_cd
import rice.composeapp.generated.resources.ic_add
import ru.evgenykuzakov.rice.navigation.Screen

@Composable
fun Fab(
    onClick: () -> Unit
){
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        content = {
            Icon(
                painter = painterResource(Res.drawable.ic_add),
                contentDescription = stringResource(Res.string.fab_icon_cd),
            )
        },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )
}
