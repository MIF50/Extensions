package com.mif50.extensions.helpers.shotcut

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import androidx.fragment.app.Fragment
import java.util.ArrayList

class ShortcutHelper {

    @SuppressLint("NewApi")
    fun createShortcut(
        shortLabel: CharSequence,
        longLabel: CharSequence,
        iconResource: Int,
        intent: Intent
    ): ShortcutHelper {

        val shortcutId = shortLabel.toString().replace("\\s+".toRegex(), "").toLowerCase() + "_shortcut"
        val shortcut = ShortcutInfo.Builder(mActivity, shortcutId)
            .setShortLabel(shortLabel)
            .setLongLabel(longLabel)
            .setIcon(Icon.createWithResource(mActivity, iconResource))
            .setIntent(intent)
            .build()
        mShortcutInfos.add(shortcut)
        return this
    }

    @SuppressLint("NewApi")
    fun createShortcut(
        shortLabel: CharSequence,
        longLabel: CharSequence,
        iconResource: Int,
        intents: Array<Intent>
    ): ShortcutHelper {

        val shortcutId = shortLabel.toString().replace("\\s+".toRegex(), "").toLowerCase() + "_shortcut"
        val shortcut = ShortcutInfo.Builder(mActivity, shortcutId)
            .setShortLabel(shortLabel)
            .setLongLabel(longLabel)
            .setIcon(Icon.createWithResource(mActivity, iconResource))
            .setIntents(intents)
            .build()
        mShortcutInfos.add(shortcut)
        return this
    }

    @SuppressLint("NewApi")
    fun createShortcutList(shortcuts: List<Shortcut>): ShortcutHelper {

        mShortcutManager = mActivity!!.getSystemService(ShortcutManager::class.java)
        for (i in shortcuts.indices) {
            if (i < mShortcutManager!!.maxShortcutCountPerActivity) {
                val shortcutId = shortcuts[i].shortLabel!!.replace("\\s+".toRegex(), "").toLowerCase() + "_shortcut"
                val shortcut = ShortcutInfo.Builder(mActivity, shortcutId)
                    .setShortLabel(shortcuts[i].shortLabel!!)
                    .setLongLabel(shortcuts[i].longLabel!!)
                    .setIcon(Icon.createWithResource(mActivity, shortcuts[i].iconResource))
                    .setIntent(shortcuts[i].intent!!)
                    .build()
                mShortcutInfos.add(shortcut)
            }
        }
        return this
    }

    fun go() {
        if (Build.VERSION.SDK_INT < 25) return

        if (mShortcutManager == null && mActivity != null)
            mShortcutManager = mActivity!!.getSystemService<ShortcutManager>(ShortcutManager::class.java)
        try {
            if (mShortcutInfos.size > 0)
                mShortcutManager!!.dynamicShortcuts =
                    mShortcutInfos
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var mActivity: Activity? = null
        private var mShortcutManager: ShortcutManager? = null
        private val mShortcutInfos = ArrayList<ShortcutInfo>()

        fun with(activity: Activity): ShortcutHelper {
            mActivity = activity
            return ShortcutHelper()
        }

        fun with(fragment: Fragment): ShortcutHelper {
            mActivity = fragment.activity
            return ShortcutHelper()
        }
    }
}