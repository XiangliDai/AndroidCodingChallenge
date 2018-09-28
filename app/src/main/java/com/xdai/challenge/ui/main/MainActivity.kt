package com.xdai.challenge.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.xdai.challenge.AndroidCodingChallengeApplication
import com.xdai.challenge.R
import android.support.v4.content.LocalBroadcastManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter


class MainActivity : AppCompatActivity() {
    lateinit var component: MainActivityComponent
    private var networkReceiver: BroadcastReceiver? = null
    private var localBroadcastManager: LocalBroadcastManager? = null
    private lateinit var mainFragment: MainFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = DaggerMainActivityComponent.builder()
                .appComponent((application as AndroidCodingChallengeApplication).component)
                .mainActivityModule(MainActivityModule(this))
                .build()

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            mainFragment = MainFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, mainFragment, TAG)
                    .commitNow()
        } else {
            mainFragment = supportFragmentManager.findFragmentByTag(TAG) as MainFragment
        }

        registerReceiver()
    }

    private fun registerReceiver() {
        val intentFilter = IntentFilter(AndroidCodingChallengeApplication.NETWORK_AVAILABLE_ACTION)

        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        networkReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val isNetworkAvailable = intent.getBooleanExtra(AndroidCodingChallengeApplication.IS_NETWORK_AVAILABLE, true)
                mainFragment?.showNetworkStatus(isNetworkAvailable)

            }
        }
        localBroadcastManager!!.registerReceiver(networkReceiver as BroadcastReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (localBroadcastManager != null) {
            this!!.networkReceiver?.let { localBroadcastManager!!.unregisterReceiver(it) }
        }
    }

    companion object {
        const val TAG: String = "MainFragment"
    }

}
