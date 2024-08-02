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
import org.koin.dsl.bind

actual object Modules {
    actual val viewModels = module {
        viewModelOf(::MainViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::GameViewModel)
        viewModelOf(::SettingsViewModel)
    }
    actual val repositories = module {
        single { PreferenceRepositoryImpl(createDatastore()) }.bind<PreferenceRepository>()
    }
    actual val controllers = module {
        single<SoundController> { SoundController(get()) }
    }
}