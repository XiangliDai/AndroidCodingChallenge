
package com.xdai.challenge.ui.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.xdai.challenge.repositories.MovieReviewRepository
import javax.inject.Inject


class MainViewModelFactory@Inject constructor(private val movieReviewRepository: MovieReviewRepository)
   : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(movieReviewRepository) as T
    }
}