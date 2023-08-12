package morezrez.moviefy.models

import androidx.annotation.ColorInt

sealed class RecyclerParentDataModel {
    data class RecyclerTitle(
        var txtRecyclerTitle : String,
    ) : RecyclerParentDataModel()


    data class RecyclerMovieList(
        var movieLists : ArrayList<MovieModel>,
    ) : RecyclerParentDataModel()

    data class MovieBanner(
        var bannerUrl : String,
    ) : RecyclerParentDataModel()

}
