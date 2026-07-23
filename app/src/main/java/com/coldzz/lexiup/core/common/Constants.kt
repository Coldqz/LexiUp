package com.coldzz.lexiup.core.common

import java.time.format.DateTimeFormatter

object Constants {
    const val MIN_WORDS_COUNT_ON_BLOCK_CREATING = 10
    const val SUGGEST_WORDS_COUNT = 3
    const val REVIEW_BLOCK_NUMBER = 0
    const val DATABASE_NAME = "words-db"
    const val WORDS_JSON = "words5k.json"
    val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.u")
    const val MIN_WORDS_IN_BLOCK = 10
    const val MAX_ACTIVE_BLOCKS_COUNT = 3
    const val MAX_LEARNED_BLOCKS_BY_DAY = 2
    const val MAX_NEW_BLOCKS_LEARNED_BY_DAY = 1
    const val QUIZ_ANSWER_OPTIONS_COUNT = 4
    // user id constant, since we have only one user
    const val USER_ID = 0
    const val MIN_NUMBER_OF_WORDS_TO_START_REVIEW_BLOCK_QUIZ = 5
}