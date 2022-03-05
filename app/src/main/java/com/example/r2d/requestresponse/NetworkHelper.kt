package com.example.r2d.requestresponse

import android.content.Context
import android.net.ConnectivityManager

class NetworkHelper {

    companion object {

        fun isNetworkAvaialble(context: Context): Boolean {

            try {

                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                val activeNetworkPresent = connectivityManager.activeNetworkInfo

                return activeNetworkPresent != null && activeNetworkPresent.isConnected()

            } catch (ex: Exception) {
                return false
            }
        }
    }
}