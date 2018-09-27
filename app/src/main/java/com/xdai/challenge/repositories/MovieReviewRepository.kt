package com.xdai.challenge.repositories

import com.xdai.challenge.models.Result
import com.xdai.challenge.network.ApiInterface
import io.reactivex.Observable
import javax.inject.Inject

class MovieReviewRepository @Inject constructor(val apiInterface: ApiInterface) {

    fun getMovieReviews(apikey: String, offset:Int): Observable<Result> {
        return apiInterface.getMovieReviews(apikey, offset)
    }
}