package di

import data.createDatastore
import data.repository.PreferenceRepository
import data.repository.PreferenceRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module
import ui.audio.SoundController

actual object Modules {
    actual val repositories = module {
        single { PreferenceRepositoryImpl(createDatastore()) }.bind<PreferenceRepository>()
    }
    actual val controllers = module {
        single<SoundController> { SoundController(get()) }
    }
}