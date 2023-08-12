package morezrez.moviefy.data.network

import morezrez.moviefy.models.MovieDetailResponse
import morezrez.moviefy.models.MovieResultModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("3/movie/top_rated?api_key=680538304f7a08f749bc5b51db8b1869")
    suspend fun getTopRatedMovies(): MovieResultModel

    @GET("3/movie/popular?api_key=680538304f7a08f749bc5b51db8b1869")
    suspend fun getPopularMovies(): MovieResultModel

    @GET("3/movie/upcoming?api_key=680538304f7a08f749bc5b51db8b1869")
    suspend fun getUpcommingMovies(): MovieResultModel

    @GET("3/movie/now_playing?api_key=680538304f7a08f749bc5b51db8b1869")
    suspend fun getNowPlayingMovies(): MovieResultModel

    @GET("3/movie/{movie_id}?api_key=680538304f7a08f749bc5b51db8b1869")
    suspend fun getMovieDetail(@Path("movie_id") id: Int): MovieDetailResponse

    @GET("3/movie/{movie_id}/similar?api_key=680538304f7a08f749bc5b51db8b1869")
    suspend fun getMovieDetailSimilar(@Path("movie_id") id : Int) : MovieResultModel
}