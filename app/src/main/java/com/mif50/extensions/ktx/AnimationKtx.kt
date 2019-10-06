package com.mif50.extensions.ktx

import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.mif50.extensions.R

/*
* made first time in recycler view translation like parallax
*/
fun RecyclerView.parallaxFirstItem(){
    addOnScrollListener(object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val view = recyclerView.getChildAt(0)
            if (view != null && recyclerView.getChildAdapterPosition(view) == 0) {
                view.translationY = -view.top / 2f
            }
        }
    })
}

/*
* infinite animation with changing alpha value
 */
fun View.reverseAlphaAnimation() {
    val reverseFade = AnimationUtils.loadAnimation(this.context, R.anim.reverse_alpha)
    startAnimation(reverseFade)
}

fun View.reverseShowHide() {
    val reverseFade = AnimationUtils.loadAnimation(this.context, R.anim.reverse_fade_animation)
    startAnimation(reverseFade)
}

fun View.shake() {
    val shakeAnim = AnimationUtils.loadAnimation(this.context, R.anim.shake)
    startAnimation(shakeAnim)
}

fun View.popup() {
    val popupAnim = AnimationUtils.loadAnimation(this.context, R.anim.pop_up)
    startAnimation(popupAnim)
}
