package morezrez.moviefy.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import morezrez.moviefy.data.datasource.MovieRemoteDataSource
import morezrez.moviefy.data.network.Resource
import morezrez.moviefy.models.MovieDetailResponse
import morezrez.moviefy.models.MovieModel
import morezrez.moviefy.models.MovieResultModel
import java.lang.reflect.Type
import javax.inject.Inject


class MovieRepository @Inject constructor(private val movieRemoteDataSource: MovieRemoteDataSource) {

    private suspend fun getTopRatedMovies(): ArrayList<MovieModel> {
        return getMovies(movieRemoteDataSource.getTopRatedMovies())!!
    }

    private suspend fun getPopularMovies(): ArrayList<MovieModel> {
        return getMovies(movieRemoteDataSource.getPopularMovies())!!
    }

    private suspend fun getUpcommingMovies(): ArrayList<MovieModel> {
        return getMovies(movieRemoteDataSource.getUpcommingMovies())!!
    }

    private suspend fun getNowPlayingMovies(): ArrayList<MovieModel> {
        return getMovies(movieRemoteDataSource.getNowPlayingMovies())!!
    }

    suspend fun getMovieDetail(id: Int): MovieDetailResponse? {
        val data = movieRemoteDataSource.getMovieDetail(id)
        val result = getMovieDetails(data)
        Log.d("BZRTAG-1", result.toString())
        Log.d("BZRTAG-2", data.toString())
        return result

    }

    suspend fun getMovieDetailSimilar(id :Int) : ArrayList<MovieModel>?{
        return getMovies(movieRemoteDataSource.getMovieDetailSimilar(id))
    }

    private fun getMovies(response: Resource<MovieResultModel>): ArrayList<MovieModel>? {
        val movies: ArrayList<MovieModel>? = when (response) {
            is Resource.Success -> {
                val results: MovieResultModel = response.data
                results.results
            }

            is Resource.Error -> {
                response.error
                null
            }
        }
        return movies
    }

    suspend fun getAllMovies(): ArrayList<ArrayList<MovieModel>> {
        val allMovies: ArrayList<ArrayList<MovieModel>> = ArrayList()
        val topRated: ArrayList<MovieModel> = getTopRatedMovies()
        val popular: ArrayList<MovieModel> = getPopularMovies()
        val nowPlaying: ArrayList<MovieModel> = getNowPlayingMovies()
        val upcomming: ArrayList<MovieModel> = getUpcommingMovies()
        allMovies.apply {
            addAll(listOf(topRated, popular, upcomming, nowPlaying))
        }
        return allMovies
    }

    private fun getMovieDetails(response: Resource<MovieDetailResponse>): MovieDetailResponse? {
        val detail: MovieDetailResponse? = when (response) {
            is Resource.Success -> {
                response.data
            }

            is Resource.Error -> {
                response.error
                null
            }
        }
        return detail
    }
}