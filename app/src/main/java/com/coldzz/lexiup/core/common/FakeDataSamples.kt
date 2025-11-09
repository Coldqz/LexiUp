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
                blockType = BlockTypes.ACTIVE,
                availableAt = LocalDateTime.now().plusMinutes(1)
            ),
            WordBlock(
                title = "ActiveBlock2",
                blockType = BlockTypes.ACTIVE,
                availableAt = LocalDateTime.now().plusMinutes(2)
            ),
            WordBlock(
                title = "PlannedBlock1",
                blockType = BlockTypes.PLANNED,
                availableAt = LocalDateTime.now().plusHours(4)
            ),
            WordBlock(
                title = "PlannedBlock2",
                blockType = BlockTypes.PLANNED,
                availableAt = LocalDateTime.now().plusHours(2)
            ),
            WordBlock(
                title = "LearnedBlock1",
                blockType = BlockTypes.LEARNED,
                completedAt = LocalDateTime.now().minusDays(4)
            ),
            WordBlock(
                title = "LearnedBlock2",
                blockType = BlockTypes.LEARNED,
                completedAt = LocalDateTime.now().minusDays(3)
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