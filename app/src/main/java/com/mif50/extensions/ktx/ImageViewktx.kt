package com.mif50.extensions.ktx

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.mif50.extensions.R
import java.io.File



// load image form url with glide and used placeholder with default value
fun ImageView.loadImage(url: String, placeholder: Int? = R.drawable.shape_black) {
    val options = RequestOptions()
    placeholder?.let { options.placeholder(it) }
    Glide.with(context).load(url)
        .apply(options)
        .into(this)

}

// load image from url with glide and set radius to image and used placeholder
fun ImageView.loadImageWithRadius(url: String, radius: Int) {
    Glide.with(this).load(url)
        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(radius)).placeholder(R.drawable.shape_black))
        .into(this)
}

// this to set circle image with glide use url
fun ImageView.setCircleImage(url: String, placeholder: Int = R.drawable.shape_black) {
    Glide.with(context).load(url)
        .apply(RequestOptions.circleCropTransform().placeholder(placeholder))
        .into(this)
}

// set circle image with glide from bitmap
fun ImageView.loadCircleImageFromBitmap(bitmap: Bitmap, placeholder: Int = R.mipmap.ic_launcher_round) {
        Glide.with(context).load(bitmap)
            .apply(RequestOptions.circleCropTransform().placeholder(placeholder))
            .into(this)
}

// set circle image with glide from file
fun ImageView.loadCircleImageFromFile(file: File, placeholder: Int = R.mipmap.ic_launcher_round) {
        Glide.with(context).load(file)
            .apply(RequestOptions.circleCropTransform().placeholder(placeholder))
            .into(this)
}

// load image with glide with fade in animation
fun ImageView.loadImageWithFade(url: String, placeholder: Int = R.drawable.shape_black) {
        Glide.with(context).load(url)
            .apply(RequestOptions().placeholder(placeholder))
            .transition(DrawableTransitionOptions.withCrossFade(300))
            .into(this)
}

// set thumbnail form videoUrl and set it to image view using glide
fun ImageView.loadThumbnailFromVideoUrl(videoUrl: String) {
        Glide.with(context).load(videoUrl)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                    setImageDrawable(resource)
                    return true
                }
            }).submit()
}

fun ImageView.changeTintColor(res: Int) {
    this.setColorFilter(ContextCompat.getColor(this.context,res),android.graphics.PorterDuff.Mode.MULTIPLY)
}