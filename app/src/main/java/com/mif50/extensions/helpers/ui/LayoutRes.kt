package com.mif50.extensions.helpers.ui


/**
 *
 * Copyrights (c) 9/28/18 Created By MIF50
 * email mohamedibrahim1223@gmail.com
 **/
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class LayoutRes(val layout: Int = 0, val menu: Int = 0, val withBack: Boolean = false)