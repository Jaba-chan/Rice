package ru.evgenykuzakov.search_food

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.evgenykuzakov.common.Resource
import ru.evgenykuzakov.domain.model.Product

@Composable
fun ProductItem(
    uiState: SearchFoodUIState,
    position: Int,
    onClick: (Product) -> Unit
) {
    when(val products = uiState.products){
        is Resource.Error -> {}
        is Resource.Loading -> {}
        is Resource.Success -> {
            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .height(48.dp)
                    .padding(horizontal = 20.dp)
                    .clickable { onClick(products.data[position]) },
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = products.data[position].name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }


}

@Composable
fun StyledHorizontalDivider(
    horizontalPadding: Dp = 16.dp
){
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = horizontalPadding),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

sealed class SearchProductsItems {
    data object ProductItem : SearchProductsItems()
    data object SearchItem : SearchProductsItems()
}