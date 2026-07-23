package com.coldzz.lexiup.core.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DefinitionsItem(
    val definition: String?,
    val synonyms: List<String>,
    val antonyms: List<String>,
    val example: String?
)