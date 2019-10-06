package com.mif50.extensions.ktx

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.mif50.extensions.helpers.ui.LayoutRes

fun Fragment.getLayoutRes(): LayoutRes {
    val annotation = this::class.java.annotations.find { it is LayoutRes } as? LayoutRes
    if (annotation != null) {
        return annotation
    } else {
        throw KotlinNullPointerException("Please add the LayoutRes annotation at the to of the class")
    }
}

fun AppCompatActivity.replaceFragment(containerId: Int,fragment: Fragment, tag: String = "") {
    this.supportFragmentManager
        .beginTransaction()
        .replace(containerId, fragment, tag)
        .commit()
}

/**
 * Pops all the queued fragments
 */
fun AppCompatActivity.popEveryFragment() {
    val fm = supportFragmentManager
    // Clear all back stack.
    val backStackCount = fm.backStackEntryCount
    for (i in 0 until backStackCount) {

        // Get the back stack fragment id.
        val backStackId = fm.getBackStackEntryAt(i).getId()

        fm.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE)

    }
}

fun AppCompatActivity.openAsRoot(containerId: Int, fragment: Fragment, tag: String = "") {
    popEveryFragment()
    replaceFragment(containerId, fragment, tag)
}


