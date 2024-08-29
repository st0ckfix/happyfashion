package com.kotlin_jetpack_compose.happyfashion.services

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.kotlin_jetpack_compose.happyfashion.components.SUBTITLE_MODE

enum class EXOPLAYER_STATE {
    PLAYING, PAUSED, STOPPED, RELEASED
}

class AudioService : ViewModel() {

    init {
        println("ViewModel initialized")
    }

    private val _exoPlayer: MutableState<ExoPlayer?> = mutableStateOf(null)
    private val _exoPlayerState: MutableState<EXOPLAYER_STATE> = mutableStateOf(EXOPLAYER_STATE.RELEASED)
    val exoPlayerState: MutableState<EXOPLAYER_STATE> = _exoPlayerState

    private var subtitlesFlow = emptyList<SubtitleService.Subtitle>()
    private var subtitlesVertical = emptyList<SubtitleService.Subtitle>()

    private val currentSubtitle: MutableIntState = mutableIntStateOf(0)

    private var _subtitleMode = mutableStateOf(SUBTITLE_MODE.NO)
    val subtitleMode: MutableState<SUBTITLE_MODE> = _subtitleMode


    fun setSubtitlesFlow(subtitlesFlow: List<SubtitleService.Subtitle>){
        this.subtitlesFlow = subtitlesFlow
    }

    fun setSubtitlesVertical(subtitlesVertical: List<SubtitleService.Subtitle>){
        this.subtitlesVertical = subtitlesVertical
    }

    fun setExoPlayer(exoPlayer: ExoPlayer){
        _exoPlayer.value = exoPlayer
    }

    fun setSubtitleState(state: SUBTITLE_MODE){
        when(state){
            SUBTITLE_MODE.FLOW -> {
                for (i in subtitlesFlow.indices) {
                    if (_exoPlayer.value!!.currentPosition in (subtitlesFlow[i].start.time - 50)..(subtitlesFlow[i].end.time + 50)) {
                        currentSubtitle.intValue = i
                        break
                    }

                }
            }
            SUBTITLE_MODE.VERTICAL -> {
                for (i in subtitlesVertical.indices) {
                    if (_exoPlayer.value!!.currentPosition in (subtitlesVertical[i].start.time - 150)..(subtitlesVertical[i].end.time + 150)) {
                        currentSubtitle.intValue = i
                        break
                    }

                }
            }
            else -> {}
        }
        _subtitleMode.value = state
    }

    fun setMediaItem(
        audioUrl: String
    ){
        val mediaItem = MediaItem.fromUri(audioUrl)
        _exoPlayer.value!!.setMediaItem(mediaItem)
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.repeatMode = Player.REPEAT_MODE_ALL
        _exoPlayer.value!!.playWhenReady = true
        _exoPlayer.value!!.addListener(object : Player.Listener {
            override fun onPositionDiscontinuity(
                oldPosition: Player.PositionInfo,
                newPosition: Player.PositionInfo,
                reason: Int
            ) {
                if (reason == Player.DISCONTINUITY_REASON_AUTO_TRANSITION) {
                    println("Audio ended and repeating")
                    currentSubtitle.intValue = 0
                }
            }
            override fun onPlaybackStateChanged(playbackState: Int) {
                if(playbackState == Player.STATE_READY){
                    _exoPlayerState.value = EXOPLAYER_STATE.PLAYING
                }
            }
        })
    }

    fun getSubtitle() : SubtitleService.Subtitle ? {
        return when(_subtitleMode.value){
            SUBTITLE_MODE.FLOW -> {
                if (currentSubtitle.intValue == subtitlesFlow.size) {
                    return null
                }
                if (_exoPlayer.value!!.currentPosition in (subtitlesFlow[currentSubtitle.intValue].start.time - 50)..(subtitlesFlow[currentSubtitle.intValue].end.time + 50)) {
                    currentSubtitle.intValue++
                    return subtitlesFlow[currentSubtitle.intValue - 1]
                }

                else {
                    return null
                }
            }
            SUBTITLE_MODE.VERTICAL -> {
                if (currentSubtitle.intValue == subtitlesVertical.size) {
                    return null
                }
                if (_exoPlayer.value!!.currentPosition in (subtitlesVertical[currentSubtitle.intValue].start.time - 150)..(subtitlesVertical[currentSubtitle.intValue].end.time + 150)) {
                    currentSubtitle.intValue++
                    return subtitlesVertical[currentSubtitle.intValue - 1]
                }
                else {
                    return null
                }
            }
            else -> SubtitleService.Subtitle(0, java.sql.Timestamp(0), java.sql.Timestamp(0), "")
        }
    }

    fun release(){
        _exoPlayer.value!!.release()
        _exoPlayerState.value = EXOPLAYER_STATE.RELEASED
    }

    fun play(){
        _exoPlayer.value!!.play()
        _exoPlayerState.value = EXOPLAYER_STATE.PLAYING
    }

    fun pause(){
        _exoPlayer.value!!.pause()
        _exoPlayerState.value = EXOPLAYER_STATE.PAUSED
    }

    fun stop(){
        _exoPlayer.value!!.stop()
        _exoPlayerState.value = EXOPLAYER_STATE.STOPPED
    }

    fun getCurrentPosition() : Long {
        return _exoPlayer.value!!.currentPosition
    }

    fun getDuration() : Long {
        return _exoPlayer.value!!.duration
    }

    fun seekTo(position: Long) {
        _exoPlayer.value!!.seekTo(position)
    }

}