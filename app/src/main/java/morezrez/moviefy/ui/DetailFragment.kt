package morezrez.moviefy.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import morezrez.moviefy.R
import morezrez.moviefy.databinding.FragmentDetailBinding
import morezrez.moviefy.models.MovieDetailResponse
import morezrez.moviefy.models.ProductionCompanies
import morezrez.moviefy.viewModels.FragmentDetailViewModel

@AndroidEntryPoint
class DetailFragment : Fragment(),AdapterCommiunicatorInterface {

    private val fragmentDetailViewModel: FragmentDetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailBinding

    private var args: DetailFragmentArgs? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        // Toast.makeText(context,args?.moviePoster,Toast.LENGTH_LONG).show()
        return binding.root

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = getArgs()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMovieDetailData()
        setUpSimilarList()

        binding.btnWatch.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToPlayerFragment()
            findNavController().navigate(action)
        }
    }

    private fun getArgs(): DetailFragmentArgs? {
        val bundle = arguments
        return bundle?.let { DetailFragmentArgs.fromBundle(it) }
    }

    private fun getMovieDetailData() {

        val idd = args?.movieId!!.toInt()
        fragmentDetailViewModel.getMovieDetail(idd)
        fragmentDetailViewModel.movieDetail.observe(viewLifecycleOwner){
            setUpMovieDetail(it)
        }
    }

    private fun setCollapsToolbarTitle(title: String) {
        var isShow = true
        var scrollRange = -1
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                binding.collapsToolbar.title = title
                isShow = true
            } else if (isShow) {
                binding.collapsToolbar.title =
                    " " //careful there should a space between double quote otherwise it wont work
                isShow = false
            }
        })
    }

    private fun setUpMovieDetail(detail : MovieDetailResponse){
        var rage = "every one"
        if (detail.adult == true) {
            rage = "+18"
        }
        Picasso.get()
            .load(getString(R.string.images_base_url) + detail.backdropPath)
            .into(binding.imgMovieBackground)
        Picasso.get()
            .load(getString(R.string.images_base_url) + detail.posterPath)
            .into(binding.imgMoviePoster)
        binding.txtMovieTitle.text = detail.originalTitle
        binding.movieDesc.text = detail.overview
        binding.txtImdb.text = detail.voteAverage.toString()
        binding.txtMovieDetail.text =
            "language : ${detail.originalLanguage} - " + "date : ${detail.releaseDate} - " + "rage : $rage"
        binding.txtLikePeople.text = "${detail.voteCount} likes this movie."
        binding.collapsToolbar.title = detail.originalTitle
        setCollapsToolbarTitle(detail.originalTitle.toString())
        val producerInfo : ArrayList<ProductionCompanies> = detail.productionCompanies
        for (info in producerInfo){
            binding.txtProducer.text = "Producer Company : "+info.name
            Picasso.get()
                .load(getString(R.string.images_base_url) + info.logoPath)
                .into(binding.imgProducerLogo)
        }
    }

    private fun setUpSimilarList(){

        val recyclerAdapterSimilar = MovieListsHorizontalRecyclerAdapter(this)
        binding.similarRecycler.apply {
            adapter = recyclerAdapterSimilar
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        }

        fragmentDetailViewModel.getMovieDetailSimilar(args?.movieId!!.toInt())
        fragmentDetailViewModel.movieDetailSimilars.observe(viewLifecycleOwner){similars ->
            recyclerAdapterSimilar.submitList(similars)

        }

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
        val action = DetailFragmentDirections.actionDetailFragmentSelf(
            backgroundPath, movieTitle, movieDescription, movieId!!, moviePoster!!, movieLanguage!!,
            movieRage!!,
            movieImdb!!.toFloat(),
            movieVoteCount!!,
            movieReleaseDate!!
        )
        findNavController().navigate(action)
    }
}