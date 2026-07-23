package com.coldzz.lexiup.features.words.domain.use_case

import javax.inject.Inject

class WordUseCases @Inject constructor(
    val getWordsWithReviewIndicator: GetWordsWithReviewIndicatorUseCase,
    val getWordDetailsUseCase: GetWordDetailsUseCase
)