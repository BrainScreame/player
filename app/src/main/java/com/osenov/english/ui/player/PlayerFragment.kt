package com.osenov.english.ui.player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import com.osenov.english.data.entities.Video
import com.osenov.english.databinding.FragmentPlayerBinding
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.exoplayer2.mediacodec.DefaultMediaCodecAdapterFactory

class PlayerFragment : Fragment() {

    companion object {
        const val videoModel = "VIDEO"
    }

    private lateinit var viewBinding: FragmentPlayerBinding

    private var player: ExoPlayer? = null
    private var video: Video? = null

    private var currentPlayWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentPlayerBinding.inflate(inflater, container, false)

//        activity?.let {
//             WindowCompat.setDecorFitsSystemWindows(it.window, false)
//             WindowInsetsControllerCompat(it.window, viewBinding.root).let { controller ->
//                 controller.hide(WindowInsetsCompat.Type.systemBars())
//                 controller.systemBarsBehavior =
//                     WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//             }
//         }
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        video = arguments?.getParcelable(videoModel)

    }

    override fun onStart() {
        super.onStart()

        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer()
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

    override fun onDestroy() {
        super.onDestroy()

        player?.clearVideoSurface()

//        activity?.let {
//            WindowCompat.setDecorFitsSystemWindows(it.window, true)
//            WindowInsetsControllerCompat(
//                it.window,
//                viewBinding.root
//            ).show(WindowInsetsCompat.Type.systemBars())
//        }
//        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
    }

    private fun initializePlayer() {
        val trackSelector = DefaultTrackSelector(requireContext()).apply {
            setParameters(buildUponParameters().setPreferredAudioLanguage("ru"))
        }

        player = ExoPlayer.Builder(
            requireContext(),
            DefaultRenderersFactory(
               requireContext()
            ).setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
        )
            .setTrackSelector(trackSelector)
            .build()
            .also { exoPlayer ->
                viewBinding.itemVideoExoplayer.player = exoPlayer
                val mediaItem = MediaItem.Builder()
                    .setUri(video?.uri)
                    .setMimeType(MimeTypes.VIDEO_MATROSKA)
                    .build()
                //exoPlayer.addListener(PlayerListener())
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.playWhenReady = currentPlayWhenReady
                exoPlayer.seekTo(currentWindow, playbackPosition)
                exoPlayer.prepare()
            }

    }

    private fun hideSystemUi() {
        viewBinding.itemVideoExoplayer.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private fun releasePlayer() {
        player?.run {
            playbackPosition = this.currentPosition
            currentWindow = this.currentWindowIndex
            currentPlayWhenReady = this.playWhenReady
            release()
        }
        player = null
    }
}
