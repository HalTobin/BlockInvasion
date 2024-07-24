package di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

import ui.MainViewModel
import feature.home.HomeViewModel
import feature.game.GameViewModel
import feature.settings.SettingsViewModel

actual val viewModelModule = module {
    singleOf(::MainViewModel)
    singleOf(::HomeViewModel)
    singleOf(::GameViewModel)
    singleOf(::SettingsViewModel)
}