package com.mif50.extensions.ktx

import android.content.Context
import android.telephony.TelephonyManager
import android.util.Log

private const val TAG = "PhoneKtx"
/*
* get the country code or sent the defaultCountry
* */
fun Context.getDetectedCountry(defaultCountryIsoCode: String = ""): String {
    val context = this.applicationContext
    detectSIMCountry(context)?.let {
        return it
    }

    detectNetworkCountry(context)?.let {
        return it
    }

    detectLocaleCountry(context)?.let {
        return it
    }

    return defaultCountryIsoCode
}

private fun detectSIMCountry(context: Context): String? {
    try {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        Log.d(TAG, "detectSIMCountry: ${telephonyManager.simCountryIso}")
        return telephonyManager.simCountryIso
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

private fun detectNetworkCountry(context: Context): String? {
    try {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        Log.d(TAG, "detectNetworkCountry: ${telephonyManager.simCountryIso}")
        return telephonyManager.networkCountryIso
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

private fun detectLocaleCountry(context: Context): String? {
    try {
        val localeCountryISO = context.resources.configuration.locale.country
        Log.d(TAG, "detectNetworkCountry: $localeCountryISO")
        return localeCountryISO
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}