package com.xdai.challenge.ui.main

import com.xdai.challenge.di.PerActivity
import com.xdai.challenge.di.components.AppComponent
import dagger.Component

@PerActivity
@Component(dependencies = [AppComponent::class], modules = [MainActivityModule::class])
interface MainActivityComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
}