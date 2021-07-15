package co.id.cpn.data.di

import co.id.cpn.data.MainRepositoryImpl
import co.id.cpn.domain.MainRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MainRepository> {
        MainRepositoryImpl(get())
    }
}