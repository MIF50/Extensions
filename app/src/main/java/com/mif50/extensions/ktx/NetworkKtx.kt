package com.mif50.extensions.ktx

import android.app.ActivityManager
import android.content.Context
import android.net.ConnectivityManager


fun Context.isNetworkConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    if (activeNetwork != null) {
        // connected to the internet
        if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
            // connected to wifi
            return true
        } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
            // connected to mobile data
            return true
        }
    }
    return true
}


// check if add in in background or not
fun Context.appIsInBackground(): Boolean {
    var isInBackground = true
    val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val runningProcesses = am.runningAppProcesses
    for (processInfo in runningProcesses) {
        if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
            for (activeProcess in processInfo.pkgList) {
                if (activeProcess == packageName) {
                    isInBackground = false
                }
            }
        }
    }
    return isInBackground
}