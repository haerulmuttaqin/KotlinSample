package co.id.cpn.kotlinsample

import android.app.Application
import co.id.cpn.data.di.networkModule
import co.id.cpn.data.di.repositoryModule
import co.id.cpn.domain.di.usecaseModule
import co.id.cpn.kotlinsample.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(
                listOf(
                    repositoryModule,
                    networkModule,
                    usecaseModule,
                    viewModelModule
                )
            )
        }
    }
}