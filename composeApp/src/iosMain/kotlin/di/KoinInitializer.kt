package di

import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(Modules.repositories, Modules.controllers, ModuleVM.viewModels)
        }
    }
}