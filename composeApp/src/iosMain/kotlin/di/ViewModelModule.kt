package di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

import ui.MainViewModel
import feature.game.GameViewModel
import feature.settings.SettingsViewModel

actual val viewModelModule = module {
    singleOf(::MainViewModel)
    singleOf(::GameViewModel)
    singleOf(::SettingsViewModel)
}