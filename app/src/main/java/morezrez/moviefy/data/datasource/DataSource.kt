package morezrez.moviefy.data.datasource

import morezrez.moviefy.data.network.Resource
import morezrez.moviefy.models.MovieDetailResponse
import morezrez.moviefy.models.MovieResultModel

interface DataSource {

   suspend fun getTopRatedMovies():Resource<MovieResultModel>
   suspend fun getPopularMovies():Resource<MovieResultModel>
   suspend fun getUpcommingMovies():Resource<MovieResultModel>
   suspend fun getNowPlayingMovies():Resource<MovieResultModel>
   suspend fun getMovieDetail(id : Int): Resource<MovieDetailResponse>
   suspend fun getMovieDetailSimilar(id : Int): Resource<MovieResultModel>

}
