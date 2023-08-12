package morezrez.moviefy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.viewModels
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.AndroidEntryPoint
import morezrez.moviefy.R
import morezrez.moviefy.databinding.FragmentDetailBinding
import morezrez.moviefy.databinding.FragmentPlayerBinding
import morezrez.moviefy.viewModels.FragmentDetailViewModel
import morezrez.moviefy.viewModels.FragmentPlayerViewModel


@AndroidEntryPoint
class PlayerFragment : Fragment() , Player.Listener{

    private var mPlayer: SimpleExoPlayer? = null
    private lateinit var playerView: PlayerView

    private lateinit var binding: FragmentPlayerBinding
    private val fragmentPlayerViewModel: FragmentPlayerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     binding = FragmentPlayerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerView = binding.PlayerView
    }


    private fun initPlayer() {
        mPlayer = SimpleExoPlayer.Builder(requireContext()).build()
        playerView.player = mPlayer
        mPlayer!!.apply {
            playWhenReady = true
            setMediaSource(fragmentPlayerViewModel.buildMediaSource())
            prepare()
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || mPlayer == null) {
            initPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }
    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        if (mPlayer == null) {
            return
        }
        //release player when done
        mPlayer!!.release()
        mPlayer = null
    }

    //creating mediaSource
    companion object {

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}