package com.xdai.challenge.models

import com.squareup.moshi.Json

data class MovieReview(
        @Json(name = "display_title") val displayTitle: String,
        @Json(name = "mpaa_rating") val rating: String?,
        @Json(name = "critics_pick") val criticsPick: Int,
        @Json(name = "byline") val byline: String,
        @Json(name = "headline") val headline: String,
        @Json(name = "summary_short") val shortSummary: String,
        @Json(name = "publication_date") val publishedAt: String,
        @Json(name = "opening_date") val openedAt: String?,
        @Json(name = "date_updated") val updatedAt: String,
        val multimedia: Multimedia,
        val link: Link

)


