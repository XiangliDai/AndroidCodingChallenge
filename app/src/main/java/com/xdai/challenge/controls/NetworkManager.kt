package com.xdai.challenge.controls

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkManager(private val context: Context) {
    fun isOnline():Boolean{
        val connManager= context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo:NetworkInfo? = connManager.activeNetworkInfo
        return networkInfo?.isConnected == true
    }
}