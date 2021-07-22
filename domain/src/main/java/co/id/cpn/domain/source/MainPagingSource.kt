package co.id.cpn.domain.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.id.cpn.domain.MainRepository
import co.id.cpn.entity.ResponseMovieDetail
import co.id.cpn.entity.ResponseMovies
import co.id.cpn.entity.ResultState
import co.id.cpn.entity.ResultsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import java.lang.Exception

class MainPagingSource constructor(private val repository: MainRepository) :
    PagingSource<Int, ResultsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        return try {
            val mutableListMovies = mutableListOf<ResultsItem>()
            val nextPageNumber = params.key ?: 1
            val nextPrevNumber = if (nextPageNumber == 1) null else nextPageNumber - 1
            val response = repository.getNowPlaying(page = nextPageNumber)
            var throwable = Throwable()
            response.collect {
                when(it) {
                    is ResultState.Success -> mutableListMovies.addAll(it.data)
                    is ResultState.Error -> {
                        throwable = it.throwable
                    }
                    is ResultState.Empty -> {
                        Log.e("DataSource", "Empty")
                    }
                }
            }

            if (throwable.message != null) {
                return LoadResult.Error(throwable)
            }
            return LoadResult.Page(
                data = mutableListMovies,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
            
        } catch (t: Throwable) {
            t.printStackTrace()
            LoadResult.Error(t)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return null
    }
}