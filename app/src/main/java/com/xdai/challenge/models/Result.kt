package com.xdai.challenge.models

import com.squareup.moshi.Json

data class Result (
   val status: String,
    val copyright: String,
    @Json(name = "has_more")val hasMore: Boolean,
    @Json(name = "num_results")val numResults: Int,
    val results: List<MovieReview>  = listOf<MovieReview>()

)