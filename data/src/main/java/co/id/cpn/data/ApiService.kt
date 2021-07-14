package co.id.cpn.data

import co.id.cpn.entity.ResponseMovieDetail
import co.id.cpn.entity.ResponseMovies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
 
    //https://api.themoviedb.org/3/
    
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") api_key: String = "710c2f2a12afed796678c7144104b344"
    ): ResponseMovies
    
    @GET("movie/{id}")
    suspend fun getMovieDetailBy(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = "710c2f2a12afed796678c7144104b344",
    ): ResponseMovieDetail
}