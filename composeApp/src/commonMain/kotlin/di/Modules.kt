package di

import org.koin.core.module.Module

expect object Modules {
    val viewModels: Module
    val repositories: Module
    val controllers: Module
}