package com.xdai.challenge.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.xdai.challenge.models.Result
import com.xdai.challenge.repositories.MovieReviewRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel  @Inject constructor(private val movieReviewRepository: MovieReviewRepository) :ViewModel() {

    private val movieReviewsResult: MutableLiveData<Result> = MutableLiveData()
    private val movieReviewsError: MutableLiveData<String> = MutableLiveData()

    fun movieReviewsResult(): LiveData<Result> {
        return movieReviewsResult
    }

    fun movieReviewsError(): LiveData<String> {
        return movieReviewsError
    }

    fun loadMovieReviews(apiKey:String, offset:Int){
        movieReviewRepository.getMovieReviews(apiKey, offset)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {  t-> movieReviewsResult.postValue(t) }, Consumer { e ->   movieReviewsError.postValue(e.message) })
    }

    companion object {
        val LOG: String = MainViewModel::class.java.canonicalName
    }

}