package morezrez.moviefy.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import morezrez.moviefy.R
import morezrez.moviefy.models.MovieModel

class MovieListsHorizontalRecyclerAdapter(private val adapterCommiunictor: AdapterCommiunicatorInterface) :
    ListAdapter<MovieModel, MovieListsHorizontalRecyclerAdapter.ViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_horizontal_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var movie = currentList[position]
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
            .into(holder.imgMovie)

        holder.apply {
            txtMovie.text = movie.title
            txtRate.text = movie.voteAverage.toString()
        }

        holder.imgMovie.setOnClickListener {

            adapterCommiunictor.transferToDetail(
                movie.backdropPath,
                movie.title,
                movie.overview,
                movie.id!!,
                movie.posterPath,
                movie.originalLanguage,
                movie.adult,
                movie.voteAverage,
                movie.voteCount,
                movie.releaseDate

            )

        }
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imgMovie: ImageView = ItemView.findViewById(R.id.img_movie_recycler)
        val txtMovie: TextView = ItemView.findViewById(R.id.txt_movie_recycler)
        val txtRate: TextView = ItemView.findViewById(R.id.txtRate)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.posterPath == newItem.posterPath
        }
    }

}

interface AdapterCommiunicatorInterface {
    fun transferToDetail(
        backgroundPath: String?,
        movieTitle: String?,
        movieDescription: String?,
        movieId: Int?,
        moviePoster: String?,
        movieLanguage: String?,
        movieRage: Boolean?,
        movieImdb: Double?,
        movieVoteCount: Int?,
        movieReleaseDate: String?
    )
}