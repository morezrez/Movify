package morezrez.moviefy.viewModels

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import morezrez.moviefy.R
import morezrez.moviefy.data.repository.MovieRepository
import javax.inject.Inject

@HiltViewModel
class FragmentPlayerViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val videoURL ="https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    private var mPlayer: SimpleExoPlayer? = null


    fun buildMediaSource(): MediaSource {
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoURL))
    }


}