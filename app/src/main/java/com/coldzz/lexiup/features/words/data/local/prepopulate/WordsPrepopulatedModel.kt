package com.coldzz.lexiup.features.words.data.local.prepopulate

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class WordsPrepopulatedModel(
    val word: String,
    @param:Json(name = "class") val partOfSpeech: String,
    val level: String
)