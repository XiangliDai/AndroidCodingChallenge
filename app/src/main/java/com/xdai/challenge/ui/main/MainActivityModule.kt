package com.xdai.challenge.ui.main

import android.app.Activity
import com.xdai.challenge.di.PerActivity
import com.xdai.challenge.network.ApiInterface
import com.xdai.challenge.repositories.MovieReviewRepository
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(private val activity: Activity) {

   @Provides
   @PerActivity
   fun activity():Activity{
       return activity
   }

    @Provides
    @PerActivity
    fun provideMainReviewRepository(apiInterface: ApiInterface): MovieReviewRepository = MovieReviewRepository(apiInterface)

    @Provides
    @PerActivity
    fun provideMainViewModelFactory(repository: MovieReviewRepository): MainViewModelFactory = MainViewModelFactory(repository)

}