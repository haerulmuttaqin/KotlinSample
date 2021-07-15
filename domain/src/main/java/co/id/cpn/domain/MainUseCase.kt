package co.id.cpn.domain

import androidx.paging.PagingData
import co.id.cpn.entity.ResponseMovieDetail
import co.id.cpn.entity.ResultState
import co.id.cpn.entity.ResultsItem
import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    fun getNowPlaying(): Flow<PagingData<ResultsItem>>
    fun getMovieDetailBy(id: Int): Flow<ResultState<ResponseMovieDetail>>
}