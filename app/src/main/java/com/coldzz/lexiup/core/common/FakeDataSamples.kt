package com.coldzz.lexiup.core.common

import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlockWithOxfordWords
import com.coldzz.lexiup.features.blocks.domain.BlockTypes
import com.coldzz.lexiup.features.words.data.local.entities.LevelCerf
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import java.time.LocalDateTime

class FakeDataSamples {
    companion object {
        val fakeWordsList1 = mutableListOf(
            OxfordWords(id = 1537, word = "discover", partOfSpeech = "noun", level = LevelCerf.A2),
            OxfordWords(id = 5221, word = "swim", partOfSpeech = "verb", level = LevelCerf.A1),
            OxfordWords(id = 4556,word = "run", partOfSpeech = "verb", level = LevelCerf.A2),
            OxfordWords(id = 4255,word = "raw", partOfSpeech = "adjective", level = LevelCerf.C1),
            OxfordWords(id = 2276,word = "funny", partOfSpeech = "adjective", level = LevelCerf.C1),
            OxfordWords(id = 3815,word = "pencil", partOfSpeech = "adjective", level = LevelCerf.C1),
            OxfordWords(id = 5883,word = "wooden", partOfSpeech = "adjective", level = LevelCerf.C1),
            OxfordWords(id = 5882,word = "wood", partOfSpeech = "adjective", level = LevelCerf.C1),
            OxfordWords(id = 2433,word = "hall", partOfSpeech = "adjective", level = LevelCerf.C1),
            OxfordWords(id = 5461,word = "town", partOfSpeech = "adjective", level = LevelCerf.C1),
        )

        val fakeBlocksList = listOf<WordBlock>(
            WordBlock(
                title = "ActiveBlock1",
                blockType = BlockTypes.REGULAR,
                availableAt = LocalDateTime.now().plusHours(4),
                completedAt = null
            ),
            WordBlock(
                title = "ActiveBlock2",
                blockType = BlockTypes.REGULAR,
                availableAt = LocalDateTime.now().plusHours(10),
                completedAt = null
            ),
            WordBlock(
                title = "CustomBlock1",
                blockType = BlockTypes.CUSTOM,
                availableAt = LocalDateTime.now().plusHours(4),
                completedAt = null
            ),
            WordBlock(
                title = "CustomBlock2",
                blockType = BlockTypes.CUSTOM,
                availableAt = LocalDateTime.now().plusHours(2),
                completedAt = null
            ),
            WordBlock(
                title = "LearnedActiveBlock1",
                blockType = BlockTypes.REGULAR,
                isLearned = true,
                completedAt = LocalDateTime.now().minusDays(4)
            ),
            WordBlock(
                title = "LearnedActiveBlock2",
                blockType = BlockTypes.REGULAR,
                isLearned = true,
                completedAt = LocalDateTime.now().minusDays(3)
            ),
            WordBlock(
                title = "LearnedCustomBlock1",
                blockType = BlockTypes.CUSTOM,
                isLearned = true,
                availableAt = null,
                completedAt = LocalDateTime.now().minusDays(1)
            ),
            WordBlock(
                title = "LearnedCustomBlock2",
                blockType = BlockTypes.CUSTOM,
                isLearned = true,
                availableAt = null,
                completedAt = LocalDateTime.now().minusDays(2)
            )
        )


        val fakeBlocksWithOxfordWordsList = listOf<WordBlockWithOxfordWords>(
            WordBlockWithOxfordWords(
                wordBlock = fakeBlocksList[0],
                words = fakeWordsList1
            ),
            WordBlockWithOxfordWords(
                wordBlock = fakeBlocksList[1],
                words = fakeWordsList1
            ),
            WordBlockWithOxfordWords(
                wordBlock = fakeBlocksList[2],
                words = fakeWordsList1
            ),
            WordBlockWithOxfordWords(
                wordBlock = fakeBlocksList[3],
                words = fakeWordsList1
            ),
            WordBlockWithOxfordWords(
                wordBlock = fakeBlocksList[4],
                words = fakeWordsList1
            ),
            WordBlockWithOxfordWords(
                wordBlock = fakeBlocksList[5],
                words = fakeWordsList1
            )
        )
    }
}