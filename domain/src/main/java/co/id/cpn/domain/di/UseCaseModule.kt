package co.id.cpn.domain.di

import co.id.cpn.domain.MainRepository
import co.id.cpn.domain.MainUseCase
import co.id.cpn.domain.MainUseCaseImpl
import org.koin.dsl.module

val usecaseModule = module {
    single<MainUseCase> {
        MainUseCaseImpl(get())
    }
}