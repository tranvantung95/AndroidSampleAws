package com.amazonaws.matches.highlightPlaying

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.amazonaws.common.fullAllScreen
import com.amazonaws.matches.R
import com.amazonaws.matches.databinding.FragmentHighlightVideoBinding
import com.amazonaws.navigation.NavigationScreenKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.amazonaws.navigation.R as navR
class HighlightPlayingFragment : Fragment() {

    private var viewBinding: FragmentHighlightVideoBinding? = null
    private lateinit var videoScope: CoroutineScope
    private var playJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHighlightVideoBinding.bind(
            inflater.inflate(
                R.layout.fragment_highlight_video,
                container,
                false
            )
        )
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(NavigationScreenKey.MATCH_HIGHLIGHT_VIDEO_URL)?.let { videoUrl ->
            viewBinding?.root?.fullAllScreen(true)
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            videoScope = CoroutineScope(Job() + Dispatchers.Main)
            viewBinding?.run {
                playJob?.cancel()
                playJob = videoScope.launch {
                    videoView.player = (ExoPlayer.Builder(context ?: return@launch).build()).apply {
                        val mediaItem = MediaItem.fromUri(videoUrl)
                        setMediaItem(mediaItem)
                        prepare()
                        play()
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        viewBinding?.root?.fullAllScreen(false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        super.onDestroyView()
    }
    override fun onStop() {
        viewBinding?.videoView?.player?.release()
        super.onStop()
    }
}