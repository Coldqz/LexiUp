package com.coldzz.lexiup.features.blocks.domain.use_case.block_use_cases

import javax.inject.Inject

class WordBlockUseCases @Inject constructor(
    val activateBlock: ActivateBlockUseCase,
    val deactivateBlock: DeactivateBlockUseCase,
    val deleteBlock: DeleteBlockUseCase,
    val getBlocksFlow: GetBlocksFlowUseCase,
    val createBlockWithWords: CreateBlockWithWordsUseCase,
    val loadRandomWords: LoadRandomWordsUseCase,
    val getReviewBlockWords: GetReviewBlockWordsUseCase,
    val addWordToReviewBlock: AddWordToReviewBlockUseCase,
    val removeWordFromReviewBlock: RemoveWordFromReviewBlockUseCase,
    val getWordsFromBlock: GetWordsFromBlockUseCase,
    val getReviewBlockId: GetReviewBlockIdUseCase
)