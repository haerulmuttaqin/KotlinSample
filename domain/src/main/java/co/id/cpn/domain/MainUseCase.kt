package co.id.cpn.domain

import co.id.cpn.entity.ResponseMovieDetail
import co.id.cpn.entity.ResultState
import co.id.cpn.entity.ResultsItem
import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    fun getNowPlaying(): Flow<ResultState<List<ResultsItem>>>
    fun getMovieDetailBy(id: Int): Flow<ResultState<ResponseMovieDetail>>
}