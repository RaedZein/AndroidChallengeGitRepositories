package com.raedzein.assignment.ui.list

import com.airbnb.lottie.LottieAnimationView

class FavouriteLoaderView (private val view : LottieAnimationView){
    var favourited = false

    private var firstTime = true
    fun favourite(favourite: Boolean){
        this.favourited = favourite
        if(firstTime) {
            view.frame = if (favourite) FRAME_FAVOURITED_STATE else FRAME_UNFAVOURITED_STATE
            firstTime = false
        }
        else{
            if(favourite)
                switchFrames(FRAME_FAVOURITE_START, FRAME_FAVOURITE_END)
            else switchFrames(FRAME_UNFAVOURITE_START, FRAME_UNFAVOURITE_END)
        }

    }

    private fun switchFrames(startFrame: Int,endFrame: Int){
        view.cancelAnimation()
        view.frame = startFrame
        view.setMinAndMaxFrame(startFrame,endFrame)
        view.playAnimation()
    }

    companion object {
        private const val FRAME_UNFAVOURITED_STATE=0
        private const val FRAME_FAVOURITED_STATE=60
        private const val FRAME_FAVOURITE_START=40
        private const val FRAME_FAVOURITE_END=60
        private const val FRAME_UNFAVOURITE_START=105
        private const val FRAME_UNFAVOURITE_END=125
    }
}
