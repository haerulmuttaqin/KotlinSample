package co.id.cpn.domain

import co.id.cpn.entity.ResponseMovieDetail
import co.id.cpn.entity.ResultState
import co.id.cpn.entity.ResultsItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainUseCaseImpl @Inject constructor(private val repository: MainRepository) : MainUseCase {
    override fun getNowPlaying(): Flow<ResultState<List<ResultsItem>>> = repository.getNowPlaying()
    override fun getMovieDetailBy(id: Int): Flow<ResultState<ResponseMovieDetail>> = repository.getMovieDetailBy(id)

}