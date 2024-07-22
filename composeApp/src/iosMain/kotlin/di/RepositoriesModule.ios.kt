package di

import data.createDatastore
import data.repository.PreferenceRepository
import data.repository.PreferenceRepositoryImpl
import org.koin.dsl.module

actual val repositoriesModule = module {
    single<PreferenceRepository> { PreferenceRepositoryImpl(createDatastore()) }
}