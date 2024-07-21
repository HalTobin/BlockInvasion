package di

import data.repository.PreferenceRepository
import data.repository.PreferenceRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {
    single<PreferenceRepository> { PreferenceRepositoryImpl() }
}