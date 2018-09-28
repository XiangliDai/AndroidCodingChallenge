package com.xdai.challenge

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.support.v4.content.LocalBroadcastManager
import com.xdai.challenge.di.components.AppComponent
import com.xdai.challenge.di.components.DaggerAppComponent
import com.xdai.challenge.di.modules.AppModule
import com.xdai.challenge.di.modules.NetworkModule

class AndroidCodingChallengeApplication: Application() {

    lateinit var component: AppComponent
    override fun onCreate() {
        super.onCreate()

       component= DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule(getString(R.string.base_url)))
                .build()

        component.inject(this)
        registerNetworkStatusReceiver()
    }

    private fun registerNetworkStatusReceiver() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(
                builder.build(),
                object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        sendLocalBroadCast(true)
                    }

                    override fun onLost(network: Network) {
                        sendLocalBroadCast(false)
                    }
                })

    }

    private fun sendLocalBroadCast(b: Boolean) {
        val networkStateIntent = Intent(NETWORK_AVAILABLE_ACTION)
        networkStateIntent.putExtra(IS_NETWORK_AVAILABLE, b)
        LocalBroadcastManager.getInstance(this@AndroidCodingChallengeApplication).sendBroadcast(networkStateIntent)

    }

    companion object {
        const val NETWORK_AVAILABLE_ACTION = "com.GoFundMe.GoFundMe.NetworkAvailable"
        const val IS_NETWORK_AVAILABLE = "isNetworkAvailable"
    }
}