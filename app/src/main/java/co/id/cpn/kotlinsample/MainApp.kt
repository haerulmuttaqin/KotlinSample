package co.id.cpn.kotlinsample

import android.app.Application
import androidx.work.*
import co.id.cpn.data.di.networkModule
import co.id.cpn.data.di.repositoryModule
import co.id.cpn.domain.di.usecaseModule
import co.id.cpn.kotlinsample.di.viewModelModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class MainApp: Application() {
    
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    
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

        applicationScope.launch {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = PeriodicWorkRequestBuilder<MainWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(this@MainApp)
                .enqueueUniquePeriodicWork(
                    BuildConfig.APPLICATION_ID,
                    ExistingPeriodicWorkPolicy.REPLACE,
                    workRequest
                )
        }
        
    }
}