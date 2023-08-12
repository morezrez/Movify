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
import morezrez.moviefy.models.RecyclerParentDataModel

class VitrinParentRecyclerAdapter( val adapterCommunicatorInterface: AdapterCommiunictor) :
    ListAdapter<RecyclerParentDataModel, VitrinParentRecyclerAdapter.ViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = when (viewType) {
            TYPE_RECYCLER_VIEW_TITLE -> R.layout.title_recycler_movie_horizontal
            TYPE_RECYCLER_VIEW_MOVIE_LIST -> R.layout.recycler_movie_horizontal
            TYPE_BANNER -> R.layout.vitrin_banner_layout
            else -> throw IllegalArgumentException("Invalid view type")
        }

        val view =
            LayoutInflater.from(parent.context)
                .inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is RecyclerParentDataModel.RecyclerTitle -> TYPE_RECYCLER_VIEW_TITLE
            is RecyclerParentDataModel.RecyclerMovieList -> TYPE_RECYCLER_VIEW_MOVIE_LIST
            is RecyclerParentDataModel.MovieBanner -> TYPE_BANNER
        }
    }

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        private fun bindRecyclerTitle(item : RecyclerParentDataModel.RecyclerTitle){
            val txtRecyclerTitle: TextView = itemView.findViewById(R.id.txtRecyclerTitle)
            txtRecyclerTitle.text=item.txtRecyclerTitle.toString()
        }

        fun bindRcyclerMovieList(item : RecyclerParentDataModel.RecyclerMovieList){
            adapterCommunicatorInterface.setUpHorizontalLists(item,itemView)
        }

        fun bindVitrinBanner(item : RecyclerParentDataModel.MovieBanner){
            val imgVitrinBanner : ImageView = itemView.findViewById(R.id.img_vitrin_banner)
            Picasso.get()
                .load(item.bannerUrl)
                .into(imgVitrinBanner)
        }

        fun bind(dataModel: RecyclerParentDataModel) {
            when (dataModel) {
                is RecyclerParentDataModel.RecyclerTitle -> bindRecyclerTitle(dataModel)
                is RecyclerParentDataModel.RecyclerMovieList -> bindRcyclerMovieList(dataModel)
                is RecyclerParentDataModel.MovieBanner -> bindVitrinBanner(dataModel)
            }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<RecyclerParentDataModel>() {
        override fun areItemsTheSame(oldItem: RecyclerParentDataModel, newItem: RecyclerParentDataModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RecyclerParentDataModel, newItem: RecyclerParentDataModel): Boolean {
            return oldItem == newItem
        }

        private const val TYPE_RECYCLER_VIEW_TITLE = 0
        private const val TYPE_RECYCLER_VIEW_MOVIE_LIST = 1
        private const val TYPE_BANNER= 2
    }

}

interface AdapterCommiunictor{
    fun setUpHorizontalLists(item: RecyclerParentDataModel.RecyclerMovieList, itemView : View)
}