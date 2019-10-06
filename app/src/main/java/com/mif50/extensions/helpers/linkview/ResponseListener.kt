package com.mif50.extensions.helpers.linkview

/**
 * Created by MIF50 .
 */

interface ResponseListener {

    fun onData(withMetaData: MetaData)

    fun onError(e: Exception)
}
