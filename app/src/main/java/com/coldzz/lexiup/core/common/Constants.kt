package com.coldzz.lexiup.core.common

import java.time.format.DateTimeFormatter

object Constants {
    const val DATABASE_NAME = "words-db"
    const val WORDS_JSON = "words5k.json"
    const val TIME_FORMAT = "%02d:%02d"
    val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.u")
}