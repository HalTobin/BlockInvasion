package ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import data.repository.AppPreferences
import feature.game.GameScreen
import feature.game.GameViewModel
import feature.home.HomeScreen
import feature.home.HomeViewModel
import feature.settings.SettingsScreen
import feature.settings.SettingsViewModel
import koinViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.currentKoinScope
import ui.theme.BlockInvasionTheme

@Composable
fun MainScreen(
    preferences: AppPreferences
) {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) {
                val viewModel = koinViewModel<HomeViewModel>()
                val state by viewModel.state.collectAsState()
                HomeScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    goTo = { navController.navigate(it.route) })
            }
            composable(Screen.Game.route) {
                val viewModel = koinViewModel<GameViewModel>()
                val state by viewModel.state.collectAsState()
                GameScreen(
                    goBack = { navController.popBackStack() },
                    goToSettings = { navController.navigate(Screen.Settings.route) },
                    state = state,
                    onEvent = viewModel::onEvent)
            }
            composable(Screen.Settings.route) {
                val viewModel = koinViewModel<SettingsViewModel>()
                SettingsScreen(
                    goBack = { navController.popBackStack() },
                    preferences = preferences,
                    onEvent = viewModel::onEvent)
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() = BlockInvasionTheme { MainScreen(AppPreferences()) }