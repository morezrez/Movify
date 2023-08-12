package morezrez.moviefy.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import morezrez.moviefy.R
import morezrez.moviefy.databinding.FragmentVitrinBinding
import morezrez.moviefy.models.RecyclerParentDataModel
import morezrez.moviefy.viewModels.FragmentVitrinViewModel

@AndroidEntryPoint
class VitrinFragment : Fragment(), AdapterCommiunictor, AdapterCommiunicatorInterface {

    private val fragmentVitrinViewModel: FragmentVitrinViewModel by viewModels()
    private lateinit var binding: FragmentVitrinBinding
    private lateinit var parentRecycler: VitrinParentRecyclerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVitrinBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentRecycler = VitrinParentRecyclerAdapter(this)
        binding.parentRecycler.apply {
            adapter = parentRecycler
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }


        initParentRecycler()

    }

    private fun initParentRecycler() {

        val titleList: MutableList<String> =
            listOf("Top Rated", "Popular", "Upcoming", "Now Playing") as MutableList<String>

        val bannerList: MutableList<String> =
            listOf(
                "https://tomandlorenzo.com/wp-content/uploads/2023/04/Barbie-The-Movie-Character-Posters-Warner-Movie-Preview-Tom-Lorenzo-Site-0.jpg",
                "https://movies.universalpictures.com/media/06-opp-dm-mobile-banner-1080x745-now-pl-f01-071223-64bab982784c7-1.jpg",
                "https://bccolonels.com/wp-content/uploads/2023/05/1683740816298961-0.jpg"
            ) as MutableList<String>

        fragmentVitrinViewModel.getAllMovies()
        fragmentVitrinViewModel.allMovies.observe(viewLifecycleOwner, Observer { movies ->


            val recyclerList: MutableList<RecyclerParentDataModel> = mutableListOf()
            var i = 0
            for (item in movies) {
                if (bannerList.getOrNull(i) != null) {
                    recyclerList.add(RecyclerParentDataModel.MovieBanner(bannerList[i]))
                }
                recyclerList.add(RecyclerParentDataModel.RecyclerTitle(titleList[i]))
                recyclerList.add(RecyclerParentDataModel.RecyclerMovieList(item))
                i += 1
            }

            parentRecycler.submitList(recyclerList)

            Log.d("parent", movies.toString())
        })
    }

    companion object {

    }

    override fun setUpHorizontalLists(
        item: RecyclerParentDataModel.RecyclerMovieList,
        itemView: View
    ) {
        val recycler: RecyclerView = itemView.findViewById(R.id.recyclerMovieHorizontal)
        val recyclerAdapterPopular = MovieListsHorizontalRecyclerAdapter(this)
        recycler.apply {
            adapter = recyclerAdapterPopular
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        }
        recyclerAdapterPopular.submitList(item.movieLists)
    }

    override fun transferToDetail(
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
    ) {
        val action = VitrinFragmentDirections.actionVitrinFragmentToDetailFragment(
            backgroundPath, movieTitle, movieDescription, movieId!!, moviePoster!!, movieLanguage!!,
            movieRage!!,
            movieImdb!!.toFloat(),
            movieVoteCount!!,
            movieReleaseDate!!
        )
        findNavController().navigate(action)

    }
}