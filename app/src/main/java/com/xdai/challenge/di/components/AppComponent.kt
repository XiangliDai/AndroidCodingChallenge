package com.xdai.challenge.di.components

import android.app.Application
import com.xdai.challenge.AndroidCodingChallengeApplication
import com.xdai.challenge.di.modules.AppModule
import com.xdai.challenge.di.modules.NetworkModule
import com.xdai.challenge.network.ApiInterface
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [AndroidInjectionModule::class, AppModule::class, NetworkModule::class]
)
interface AppComponent {
    fun inject(app: AndroidCodingChallengeApplication)

    fun application():Application
    fun apiInterface(): ApiInterface
}