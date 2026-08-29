package com.coldzz.lexiup.core.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FreeDictionaryResponse(
    val word: String?,
    val entries: List<ResponseEntry>?,
    val source: Source?
)

@JsonClass(generateAdapter = true)
data class ResponseEntry(
    val language: LanguageParameters?,
    val partOfSpeech: String?,
    val pronunciations: List<PronunciationEntry>?,
    val senses: List<SenseEntry>?,
    val synonyms: List<String>?
)

@JsonClass(generateAdapter = true)
data class PronunciationEntry(
    val type: String?,
    @param:Json(name = "text") val transcription : String?,
    val tags: List<String>?
)

@JsonClass(generateAdapter = true)
data class SenseEntry(
    val definition: String?,
    val tags: List<String>?,
    val examples: List<String>?,
    val synonyms: List<String>?,
    val subsenses: List<SenseEntry>?
)

@JsonClass(generateAdapter = true)
data class LanguageParameters(
    val code: String?,
    val name: String?
)

@JsonClass(generateAdapter = true)
data class Source(
    @param:Json(name = "url") val wordLink: String?,
    val license: License
)

@JsonClass(generateAdapter = true)
data class License(
    @param:Json(name = "name") val licenseName : String?,
    @param:Json(name = "url") val licenseUrl: String?
)
