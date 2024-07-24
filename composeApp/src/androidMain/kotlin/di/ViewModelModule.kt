package di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

import feature.game.GameViewModel
import ui.MainViewModel
import feature.settings.SettingsViewModel

actual val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::GameViewModel)
    viewModelOf(::SettingsViewModel)
}