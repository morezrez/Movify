package morezrez.moviefy.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import morezrez.moviefy.data.network.Resource
import morezrez.moviefy.data.repository.MovieRepository
import morezrez.moviefy.models.MovieModel
import morezrez.moviefy.models.MovieResultModel
import javax.inject.Inject

@HiltViewModel
class FragmentVitrinViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    var allMovies : MutableLiveData<ArrayList<ArrayList<MovieModel>>> = MutableLiveData()


    fun getAllMovies() {
        viewModelScope.launch {
            allMovies.value = movieRepository.getAllMovies()
        }
    }


}