package com.coldzz.lexiup.features.quiz.domain.use_case

import com.coldzz.lexiup.core.common.ResultDataState
import com.coldzz.lexiup.features.words.data.local.projection.PickQuizWordsData
import com.coldzz.lexiup.features.words.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPickQuizWordUseCase @Inject constructor(
    private val wordRepository: WordRepository
) {
    operator fun invoke(blockId: Int): Flow<ResultDataState<List<PickQuizWordsData>>> {
        return wordRepository.getPickQuizWordsFlow(blockId)
    }
}