package co.id.cpn.data

import co.id.cpn.domain.MainRepository
import co.id.cpn.entity.ResponseMovieDetail
import co.id.cpn.entity.ResultState
import co.id.cpn.entity.ResultsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainRepositoryImpl constructor(private val apiService: ApiService) :
    MainRepository {
    /*override fun getNowPlaying(page: Int): Flow<ResultState<List<ResultsItem>>> = flow {
        try {
            val response = apiService.getNowPlaying(page = page)
            if (response.results.isNotEmpty()) {
                emit(ResultState.Success(response.results))
            } else {
                emit(ResultState.Empty)
            }
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }*/

    override fun getNowPlaying(page: Int): Flow<ResultState<List<ResultsItem>>> = flow {
        try {
            val response = apiService.getNowPlaying(page = page)
            if (response.results.isNotEmpty()) {
                emit(ResultState.Success(response.results))
            } else {
                emit(ResultState.Empty)
            }
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

    override fun getMovieDetailBy(id: Int): Flow<ResultState<ResponseMovieDetail>> = flow {
        try {
            val response = apiService.getMovieDetailBy(id)
            emit(ResultState.Success(response))
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)
}