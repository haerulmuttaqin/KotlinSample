package co.id.cpn.kotlinsample

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import co.id.cpn.data.MainRepositoryImpl
import co.id.cpn.kotlinsample.utils.Injection
import kotlinx.coroutines.flow.collect

class MainWorker constructor(appContext: Context, workerParameters: WorkerParameters): 
    CoroutineWorker(appContext, workerParameters) {
    
    override suspend fun doWork(): Result {
        
        val service = Injection.provideApiService()
        val repository = MainRepositoryImpl(service)
        return try {
            
            val response = repository.getNowPlaying(1)
            response.collect {
                Log.i("MainWorker", "doWork: $it")
            }
            Log.i("MainWorker", "doWork Success: running")
            return Result.success()
        } catch (e: Exception) {
            Log.i("MainWorker", "doWork Failure: $e")
            Result.failure()
        }    
    }
}