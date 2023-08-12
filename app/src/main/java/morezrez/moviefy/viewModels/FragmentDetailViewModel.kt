package morezrez.moviefy.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import morezrez.moviefy.data.repository.MovieRepository
import morezrez.moviefy.models.MovieDetailResponse
import morezrez.moviefy.models.MovieModel
import javax.inject.Inject

@HiltViewModel
class FragmentDetailViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    var movieDetail : MutableLiveData<MovieDetailResponse> = MutableLiveData()
    var movieDetailSimilars : MutableLiveData<ArrayList<MovieModel>> = MutableLiveData()


    fun getMovieDetail(id :Int)  {
        viewModelScope.launch {
             movieDetail.value=movieRepository.getMovieDetail(id)
        }

    }

    fun getMovieDetailSimilar(id :Int){
        viewModelScope.launch {
            movieDetailSimilars.value = movieRepository.getMovieDetailSimilar(id)
        }
    }


}