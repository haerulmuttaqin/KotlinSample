package co.id.cpn.domain.di

import co.id.cpn.domain.MainUseCase
import co.id.cpn.domain.MainUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @Singleton
    @Binds
    internal abstract fun bindUseCase(mainUseCaseImpl: MainUseCaseImpl): MainUseCase
    
}