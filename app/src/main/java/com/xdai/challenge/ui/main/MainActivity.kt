package com.xdai.challenge.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.xdai.challenge.AndroidCodingChallengeApplication
import com.xdai.challenge.R


class MainActivity : AppCompatActivity() {
     lateinit var component:MainActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = DaggerMainActivityComponent.builder()
        .appComponent((application as AndroidCodingChallengeApplication).component)
                .mainActivityModule(MainActivityModule(this))
                .build()


        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}
