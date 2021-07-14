package co.id.cpn.data.di

import co.id.cpn.data.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    
    @Singleton
    @Binds
    internal abstract fun bindRepository(repositoryImpl: MainRepositoryImpl): co.id.cpn.domain.MainRepository 
    
    
}