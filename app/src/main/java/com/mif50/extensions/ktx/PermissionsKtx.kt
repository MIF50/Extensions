package com.mif50.extensions.ktx

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

fun Activity.hasPermission(vararg permissions: String): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true
    return permissions.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }
}

fun IntArray.hasAllPermissionsGranted(): Boolean {
    for (grantResult in this) {
        if (grantResult == PackageManager.PERMISSION_DENIED) {
            return false
        }
    }
    return true
}

fun isEqualOrMoreThanMarshmallow(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M