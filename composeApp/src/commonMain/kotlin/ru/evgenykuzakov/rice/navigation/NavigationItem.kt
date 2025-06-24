package ru.evgenykuzakov.rice.navigation

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import rice.composeapp.generated.resources.Res
import rice.composeapp.generated.resources.food
import rice.composeapp.generated.resources.ic_food
import rice.composeapp.generated.resources.ic_person
import rice.composeapp.generated.resources.ic_statistics
import rice.composeapp.generated.resources.ic_training
import rice.composeapp.generated.resources.profile
import rice.composeapp.generated.resources.statistics
import rice.composeapp.generated.resources.training

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: StringResource,
    val iconResId: DrawableResource
) {
    object Food: NavigationItem(
        screen = Screen.FoodScreen,
        titleResId = Res.string.food,
        iconResId = Res.drawable.ic_food
        
    )
    object Person: NavigationItem(
        screen = Screen.ProfileScreen,
        titleResId = Res.string.profile,
        iconResId = Res.drawable.ic_person

    )
    object Statistic: NavigationItem(
        screen = Screen.StatisticScreen,
        titleResId = Res.string.statistics,
        iconResId = Res.drawable.ic_statistics

    )

    object Training: NavigationItem(
        screen = Screen.TrainingScreen,
        titleResId = Res.string.training,
        iconResId = Res.drawable.ic_training

    )
}