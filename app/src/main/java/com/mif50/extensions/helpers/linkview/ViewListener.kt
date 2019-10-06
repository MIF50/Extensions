package com.mif50.extensions.helpers.linkview

/**
 * Created by BeinMedia @2018.
 */

interface ViewListener {

    fun onSuccess(status: Boolean)

    fun onError(e: Exception)
}
