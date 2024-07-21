package ui

sealed class Screen(val route: String) {
    data object Home: Screen("home")
    data object Game: Screen("game")
    data object Settings: Screen("settings")
}