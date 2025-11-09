package com.coldzz.lexiup.features.blocks.domain

import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock

class FormattedWordBlocksList (
    val activeBlocks: MutableList<WordBlock> = mutableListOf(),
    val plannedBlocks: MutableList<WordBlock> = mutableListOf(),
    val learnedBlocks: MutableList<WordBlock> = mutableListOf()
) {
    companion object {
        fun formattedList(data: List<WordBlock>): FormattedWordBlocksList {
            val formattedWordBlock = FormattedWordBlocksList()
            data.forEach { wordBlock ->
                when {
                    wordBlock.blockType == BlockTypes.ACTIVE && !wordBlock.isPermanent -> formattedWordBlock.activeBlocks.add(wordBlock)
                    wordBlock.blockType == BlockTypes.PLANNED && !wordBlock.isPermanent -> formattedWordBlock.plannedBlocks.add(wordBlock)
                    wordBlock.blockType == BlockTypes.LEARNED && !wordBlock.isPermanent -> formattedWordBlock.learnedBlocks.add(wordBlock)
                }
            }
            return formattedWordBlock
        }
    }
}
