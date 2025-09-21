package com.coldzz.lexiup.features.words.data.local.prepopulate

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class WordsPrepopulatedModel (
    @param:Json(name = "A1") val a1: List<String>,
    @param:Json(name = "A2") val a2: List<String>,
    @param:Json(name = "B1") val b1: List<String>,
    @param:Json(name = "B2") val b2: List<String>,
)