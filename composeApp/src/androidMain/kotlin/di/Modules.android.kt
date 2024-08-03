package di

import data.createDatastore
import data.repository.PreferenceRepository
import data.repository.PreferenceRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ui.audio.SoundController

actual object Modules {
    actual val repositories = module {
        single<PreferenceRepository> { PreferenceRepositoryImpl(createDatastore(androidApplication())) }
    }
    actual val controllers = module {
        single<SoundController> { SoundController(get()) }
    }
}