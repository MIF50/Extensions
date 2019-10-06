package com.mif50.extensions.ktx

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import com.mif50.extensions.R

fun getScreenHeight(): Int = Resources.getSystem().displayMetrics.heightPixels

fun getScreenWidth(): Int = Resources.getSystem().displayMetrics.widthPixels

fun View.isViewInsideScreen(): Boolean{
    if (!this.isShown)return false

    val actualPosition = Rect()
    this.getGlobalVisibleRect(actualPosition)
    val screen = Rect(0,0, getScreenWidth(), getScreenHeight())
    return actualPosition.intersect(screen)
}

fun Context.getDimensionPixel(resId: Int): Int = this.resources.getDimensionPixelSize(resId)

fun Context.cornerRadius(): Int{
    return this.getDimensionPixel(R.dimen.corner_radius)
}

fun Context.sixteenDp(): Int{
    return this.applicationContext.getDimensionPixel(R.dimen.size_sixteen)
}
fun Context.fourDp(): Int{
    return this.applicationContext.getDimensionPixel(R.dimen.size_four)
}

fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)

fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

/*
 * Integer Extensions
 */
fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.heightWithRatio(width: Int, height: Int): Int {
    val h = (this.toFloat() * height.toFloat()) / width.toFloat()
    return h.toInt()
}