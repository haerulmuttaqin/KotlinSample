package co.id.cpn.kotlinsample

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import co.id.cpn.domain.MainUseCase
import co.id.cpn.entity.ResponseMovieDetail
import co.id.cpn.entity.ResultState
import co.id.cpn.entity.ResultsItem
import kotlinx.coroutines.flow.Flow

class MainViewModel constructor(private val mainUseCase: MainUseCase) : ViewModel() {
    val nowPlayingMovies: Flow<PagingData<ResultsItem>> = mainUseCase.getNowPlaying().cachedIn(viewModelScope)
    
    fun movieDetailBy(id: Int): LiveData<ResultState<ResponseMovieDetail>> =
        mainUseCase.getMovieDetailBy(id).asLiveData()
}