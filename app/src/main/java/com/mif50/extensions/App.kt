package com.mif50.extensions

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {

//        private var instance: App? = null
//        fun getInstance(): App {
//            if (instance == null){
//                return App()
//            }
//            return instance!!
//        }
//
//        fun getApplicationContext(): Context {
//            return  getInstance().applicationContext
//        }
    }

    override fun onCreate() {
        super.onCreate()
//        instance = this
    }

}