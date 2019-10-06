package com.mif50.extensions.ktx

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import com.mif50.extensions.R
import com.mif50.extensions.helpers.shotcut.ShortcutHelper
import com.mif50.extensions.ui.MainActivity

fun Activity.setShortCuts(isLogin: Boolean) {

    if (Build.VERSION.SDK_INT < 25) return

    val intent = if (isLogin) {
        Intent(Intent.ACTION_MAIN, Uri.EMPTY, this, MainActivity::class.java) // we can sent data also .putExtra
    }else{
        Intent(Intent.ACTION_MAIN, Uri.EMPTY, this, MainActivity::class.java)
    }

    ShortcutHelper.with(this)
        .createShortcut(
            "search_for_expert_topic",
            getString(R.string.text_search_for),
            R.drawable.ic_search,
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)).go()
}