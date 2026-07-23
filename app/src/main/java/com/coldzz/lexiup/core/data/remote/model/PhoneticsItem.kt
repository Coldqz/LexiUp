package com.coldzz.lexiup.core.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhoneticsItem(
    val text: String?,
    val audio: String?,
    val sourceUrl: String?,
    val license: LicenceItem?
)