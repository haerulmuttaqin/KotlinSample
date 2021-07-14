package co.id.cpn.kotlinsample

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import co.id.cpn.domain.MainUseCase
import co.id.cpn.entity.ResponseMovieDetail
import co.id.cpn.entity.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainUseCase: MainUseCase) : ViewModel() {
    val nowPlayingMovies = mainUseCase.getNowPlaying().asLiveData()
    fun movieDetailBy(id: Int): LiveData<ResultState<ResponseMovieDetail>> = mainUseCase.getMovieDetailBy(id).asLiveData() 
}