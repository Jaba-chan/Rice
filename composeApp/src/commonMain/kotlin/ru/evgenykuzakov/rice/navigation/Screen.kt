package ru.evgenykuzakov.rice.navigation

sealed class Screen(val route: String) {
    object SignInScreen: Screen(ROUTE_SIGN_IN)
    object SignUpScreen: Screen(ROUTE_SIGN_UP)
    object ProfileScreen: Screen(ROUTE_PROFILE)
    object FoodScreen: Screen(ROUTE_FOOD)
    object TrainingScreen: Screen(ROUTE_TRAINING)
    object HomeScreen: Screen(ROUTE_HOME)
    object SearchProductScreen: Screen(ROUTE_SEARCH_PRODUCT)


    companion object {
        const val ROUTE_SIGN_IN = "sign_in"
        const val ROUTE_SIGN_UP = "sign_up"
        const val ROUTE_PROFILE = "profile"
        const val ROUTE_FOOD = "food"
        const val ROUTE_TRAINING = "training"
        const val ROUTE_HOME = "home"
        const val ROUTE_SEARCH_PRODUCT = "search_product"
    }
}