package be.chaidev.chronote.util

import android.widget.ImageView

interface GlideManager {

    fun setImage(imageUrl: String, imageView: ImageView)
}