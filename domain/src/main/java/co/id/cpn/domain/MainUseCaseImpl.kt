package co.id.cpn.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import co.id.cpn.domain.source.MainPagingSource
import co.id.cpn.entity.ResponseMovieDetail
import co.id.cpn.entity.ResponseMovies
import co.id.cpn.entity.ResultState
import co.id.cpn.entity.ResultsItem
import kotlinx.coroutines.flow.Flow

class MainUseCaseImpl constructor(private val repository: MainRepository): MainUseCase {
    
    override fun getNowPlaying(): Flow<PagingData<ResultsItem>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { 
            MainPagingSource(repository)
        }
    ).flow

    override fun getMovieDetailBy(id: Int): Flow<ResultState<ResponseMovieDetail>> =
        repository.getMovieDetailBy(id)
}