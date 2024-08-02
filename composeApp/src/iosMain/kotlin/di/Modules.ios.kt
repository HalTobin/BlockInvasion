package di

import data.createDatastore
import data.repository.PreferenceRepository
import data.repository.PreferenceRepositoryImpl
import org.koin.dsl.module
import ui.audio.SoundController

import ui.MainViewModel
import feature.home.HomeViewModel
import feature.game.GameViewModel
import feature.settings.SettingsViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf

actual object Modules {
    actual val viewModels = module {
        viewModelOf(::MainViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::GameViewModel)
        viewModelOf(::SettingsViewModel)
    }
    actual val repositories = module {
        single<PreferenceRepository> { PreferenceRepositoryImpl(createDatastore()) }
    }
    actual val controllers = module {
        single<SoundController> { SoundController(get()) }
    }
}