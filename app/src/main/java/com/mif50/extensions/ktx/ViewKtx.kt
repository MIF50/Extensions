package com.mif50.extensions.ktx

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.text.Html
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mif50.extensions.R
import com.mif50.extensions.helpers.linkview.TextViewLinkHandler
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun SwipeRefreshLayout.startRefreshing() {
    isRefreshing = true
}

fun SwipeRefreshLayout.stopRefreshing() {
    isRefreshing = false
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)



fun View.setVisibility(visible: Boolean) {
    if (visible) this.setVisible() else this.setGone()
}

fun View.setGone() { this.visibility = View.GONE }
fun setGone(vararg views: View ) {
    views.map { it.setGone() }
}

fun View.toggleVisibility() {
    if (this.isShown) this.setGone() else this.setVisible()
}

fun View.setInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.setVisible() {
    this.visibility = View.VISIBLE
}

fun setVisible(vararg views: View) {
    views.map { it.setVisible() }
}

fun setInvisible(vararg views: View) {
    views.map { it.setInvisible() }
}

fun View.margin(left: Float? = null, top: Float? = null, right: Float? = null, bottom: Float? = null) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.run { leftMargin = dpToPx(this) }
        top?.run { topMargin = dpToPx(this) }
        right?.run { rightMargin = dpToPx(this) }
        bottom?.run { bottomMargin = dpToPx(this) }
    }
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}



fun clearAllEditText(vararg views: EditText){
    for (view in views)
        view.text = null

}
fun AppCompatTextView.setTextFromHtml(str : String){
    text = if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
        (Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY))
    } else {
        Html.fromHtml(str)
    }
}


fun View.toggleClickable(){
    this.isClickable = !this.isClickable
}

fun View.setVisibleOrGone(flag: Boolean){
    if (flag) this.setVisible() else this.setGone()
}

fun EditText.getString() = this.text.toString().trim()


//fun Context.hideKeyboard(view: View) {
//    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
//}

fun View.changeBackgroundTint(color: String){
    val states = arrayOf(intArrayOf(android.R.attr.state_enabled))
    val colors = intArrayOf(Color.parseColor(color))
    val tintList = ColorStateList(states, colors)
    this.background.setTintList(tintList)
}

fun AppCompatEditText.clear(){
    if(text.toString().isNotEmpty()) setText("")
}

fun View.fastChangeColor(color1: Int = R.color.colorWhite, color2: Int = R.color.colorDarkOrange){
    setBackgroundResource(color1)
    Handler().postDelayed({
        setBackgroundResource(color2)
    }, 100)
}

fun TextView.openLinkFromLinkedText() {
    movementMethod = object : TextViewLinkHandler() {
        override fun onLinkClick(url: String) {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            builder.setToolbarColor(Objects.requireNonNull(ContextCompat.getColor(context, R.color.colorPrimary)))
            builder.setSecondaryToolbarColor(Objects.requireNonNull(ContextCompat.getColor(context, R.color.colorAccent)))
            customTabsIntent.launchUrl(Objects.requireNonNull(context), Uri.parse(url))
        }
    }
}

fun EditText.placeCursorToEnd(){
    this.setSelection(this.text.length)
}

fun EditText.placeCursorToStart(){
    this.setSelection(0)
}

fun View.setRaduisWithColor(color: Int, radius: Int){
    val shape = GradientDrawable()
    shape.cornerRadius = radius.toFloat()
    shape.setColor(color)
    this.background = shape
}



fun getResId(resName: String, c: Class<*>): Int {
    return try {
        val idField = c.getDeclaredField(resName)
        idField.getInt(idField)
    } catch (e: Exception) {
        e.printStackTrace()
        -1
    }
}

fun TextView.changeTextColor(res: Int){
    this.setTextColor(ContextCompat.getColor(context, res))
}
