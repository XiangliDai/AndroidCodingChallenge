package com.xdai.challenge.network

import com.xdai.challenge.models.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("reviews/dvd-picks.json?order=by-date")
    fun getMovieReviews(@Query("api-key")apiKey:String, @Query("offset") offset:Int): Observable<Result>
}