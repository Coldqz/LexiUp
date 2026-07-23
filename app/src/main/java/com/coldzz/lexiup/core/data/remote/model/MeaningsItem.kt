package com.coldzz.lexiup.core.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MeaningsItem(
    val partOfSpeech: String?,
    val definitions: List<DefinitionsItem>,
    val synonyms: List<String>,
    val antonyms: List<String>
)