package morezrez.moviefy.data.datasource

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import morezrez.moviefy.data.network.ApiService
import morezrez.moviefy.data.network.Resource
import morezrez.moviefy.data.network.safeApiCall
import morezrez.moviefy.models.MovieResultModel
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val apiService: ApiService) : DataSource {

    override suspend fun getTopRatedMovies() = safeApiCall { apiService.getTopRatedMovies() }
    override suspend fun getPopularMovies() = safeApiCall { apiService.getPopularMovies() }
    override suspend fun getUpcommingMovies() = safeApiCall { apiService.getUpcommingMovies() }
    override suspend fun getNowPlayingMovies() = safeApiCall { apiService.getNowPlayingMovies() }
    override suspend fun getMovieDetail(id: Int) = safeApiCall { apiService.getMovieDetail(id) }
    override suspend fun getMovieDetailSimilar(id: Int)= safeApiCall{ apiService.getMovieDetailSimilar(id) }
}