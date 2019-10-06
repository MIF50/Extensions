package com.mif50.extensions.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import androidx.fragment.app.FragmentActivity
import com.mif50.extensions.helpers.ui.LayoutRes


/**
 *  @name Time viewer
 * Copyrights (c) 9/15/18 Created By MIF50
 **/
fun Activity.getLayoutRes(): LayoutRes {
    val annotation = this::class.java.annotations.find { it is LayoutRes } as? LayoutRes
    if (annotation != null) {
        return annotation
    } else {
        throw KotlinNullPointerException("Please add the LayoutRes annotation at the top of the class")
    }
}

inline fun <reified T: Any> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T: Any> Context.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T: Any> Context.startActivityWithClearTask() {
    startActivity(Intent(this, T::class.java).addClearFlags())
}

inline fun <reified T: Any> FragmentActivity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T: Any> Activity.startActivity(data: Intent) {
    startActivity(Intent(this, T::class.java).putExtras(data))
}

inline fun <reified T: Any> Context.startActivity(data: Intent) {
    startActivity(Intent(this, T::class.java).putExtras(data))
}

inline fun <reified T: Any> Context.startActivityWithClearTask(data: Intent) {
    startActivity(Intent(this, T::class.java).putExtras(data).addClearFlags())
}

inline fun <reified T: Any> Activity.startActivityForResult(requestCode: Int) {
    startActivityForResult(Intent(this, T::class.java), requestCode)
}

inline fun <reified T: Any> Activity.startActivityForResult(data: Intent, requestCode: Int) {
    startActivityForResult(Intent(this, T::class.java).putExtras(data), requestCode)
}

fun Intent.addClearFlags() = addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
