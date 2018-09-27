package com.xdai.challenge.models

import com.squareup.moshi.Json

data class Link(
        val type: String,
        val url: String,
        @Json(name = "suggested_link_text")val suggestedLinkText: String
)
