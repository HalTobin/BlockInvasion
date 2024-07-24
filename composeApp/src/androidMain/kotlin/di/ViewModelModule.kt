package di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

import ui.MainViewModel
import feature.home.HomeViewModel
import feature.game.GameViewModel
import feature.settings.SettingsViewModel

actual val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::GameViewModel)
    viewModelOf(::SettingsViewModel)
}