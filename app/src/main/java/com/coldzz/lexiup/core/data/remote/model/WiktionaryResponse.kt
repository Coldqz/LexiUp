package com.coldzz.lexiup.core.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WiktionaryResponse(
    val query: WiktionaryQuery?
)

@JsonClass(generateAdapter = true)
data class WiktionaryQuery(
    val pages: Map<String, WiktionaryPage>?
)

@JsonClass(generateAdapter = true)
data class WiktionaryPage(
    @param:Json(name = "imageinfo")
    val imageInfo: List<ImageInfoItem>?
)

@JsonClass(generateAdapter = true)
data class ImageInfoItem(
    val url: String?,
)