package com.coldzz.lexiup.features.blocks.domain

import com.coldzz.lexiup.features.blocks.data.local.WordBlock

class FormattedWordBlocksList (
    val activeBlocks: MutableList<WordBlock> = mutableListOf(),
    val customBlocks: MutableList<WordBlock> = mutableListOf(),
    val learnedBlocks: MutableList<WordBlock> = mutableListOf()
) {
    companion object {
        fun formattedList(data: List<WordBlock>): FormattedWordBlocksList {
            val formattedWordBlock = FormattedWordBlocksList()
            data.forEach { wordBlock ->
                when {
                    wordBlock.blockType == BlockTypes.REGULAR && !wordBlock.isLearned -> formattedWordBlock.activeBlocks.add(wordBlock)
                    wordBlock.blockType == BlockTypes.CUSTOM && !wordBlock.isLearned -> formattedWordBlock.customBlocks.add(wordBlock)
                    wordBlock.isLearned -> formattedWordBlock.learnedBlocks.add(wordBlock)
                }
            }
            return formattedWordBlock
        }
    }
}
