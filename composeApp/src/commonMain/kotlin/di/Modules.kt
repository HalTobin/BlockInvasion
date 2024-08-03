package di

import feature.game.GameViewModel
import feature.home.HomeViewModel
import feature.settings.SettingsViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import ui.MainViewModel

expect object Modules {
    val repositories: Module
    val controllers: Module
}

object ModuleVM {
    val viewModels = module {
        viewModelOf(::MainViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::GameViewModel)
        viewModelOf(::SettingsViewModel)
    }
}