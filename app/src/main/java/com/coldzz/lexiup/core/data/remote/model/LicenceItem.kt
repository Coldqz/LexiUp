package com.coldzz.lexiup.core.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LicenceItem(
    val name: String?,
    val url: String?
)