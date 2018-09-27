package com.xdai.challenge

import android.app.Application
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
    }

}