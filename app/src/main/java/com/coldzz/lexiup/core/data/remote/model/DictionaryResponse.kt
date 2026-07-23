package com.coldzz.lexiup.core.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DictionaryResponse(
    val word: String?,
    val phonetic: String?,
    val phonetics: List<PhoneticsItem>,
    val meanings: List<MeaningsItem>,
    val license: LicenceItem?,
    val sourceUrls: List<String>
)