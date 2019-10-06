package com.mif50.extensions.ktx

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


fun Context.longSnakbar(rootView: View, message: String) {
    Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
}

fun Context.longSnackbarWithAction(rootView: View, message: String, actionTitle: String, click: View.OnClickListener) {
    Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).setAction(actionTitle, click).show()
}
