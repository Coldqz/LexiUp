package com.coldzz.lexiup.core.common

import com.coldzz.lexiup.features.blocks.data.local.entities.WordBlock
import com.coldzz.lexiup.features.blocks.domain.BlockTypes
import com.coldzz.lexiup.features.stats.presentation.LevelProgressDataModel
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import com.coldzz.lexiup.features.words.data.local.projection.WordsWithReviewBlockIndicator
import com.coldzz.lexiup.features.words.presentation.DefinitionAndExampleModel
import com.coldzz.lexiup.features.words.presentation.WordDetailsUiState
import com.coldzz.lexiup.features.words.presentation.WordItemUiModel
import java.time.LocalDate
import kotlin.random.Random

class FakeDataSamples {
    companion object {
        val fakeDataForCerfLevelStatistics = buildList {
            for (i in 0..5) {
                add(
                    LevelProgressDataModel(
                        level = CerfLevel.entries[i],
                        percentage = Random.nextInt(until = 100).toFloat()
                    )
                )
            }
        }

        val fakeWordsList1 = mutableListOf(
            OxfordWords(id = 1537, word = "discover", partOfSpeech = "noun", level = CerfLevel.A2),
            OxfordWords(id = 5221, word = "swim", partOfSpeech = "verb", level = CerfLevel.A1),
            OxfordWords(id = 4556, word = "run", partOfSpeech = "verb", level = CerfLevel.A2),
            OxfordWords(id = 4255, word = "raw", partOfSpeech = "adjective", level = CerfLevel.C1),
            OxfordWords(id = 2276, word = "funny", partOfSpeech = "adjective", level = CerfLevel.C1),
            OxfordWords(id = 3815, word = "pencil", partOfSpeech = "adjective", level = CerfLevel.C1),
            OxfordWords(id = 5883, word = "wooden", partOfSpeech = "adjective", level = CerfLevel.C1),
            OxfordWords(id = 5882, word = "wood", partOfSpeech = "adjective", level = CerfLevel.C1),
            OxfordWords(id = 2433, word = "hall", partOfSpeech = "adjective", level = CerfLevel.C1),
            OxfordWords(id = 5461, word = "town", partOfSpeech = "adjective", level = CerfLevel.C1),
        )

        val fakeWordDefinitionSample = WordDetailsUiState(
            word = "Serendipity",
            phonetic = "/ˌsɛ.ɹən.ˈdɪ.pɪ.ti/",
            partOfSpeech = "Noun",
            level = CerfLevel.C1,
            enableAmericanButton = true,
            enableBritishButton = false,
            definitionAndExamples = listOf(
                DefinitionAndExampleModel(
                    definition = "The occurrence and development of events by chance in a happy or beneficial way.",
                    example = "A fortunate stroke of serendipity led them to discover the hidden treasure."
                ),
                DefinitionAndExampleModel(
                    definition = "Serendipity can also refer to the faculty or phenomenon of making fortunate discoveries by accident.",
                    example = ""
                ),
                DefinitionAndExampleModel(
                    definition = "It can also describe an instance of finding something good without looking for it.",
                    example = "The serendipity of their encounter sparked a lifelong friendship."
                )
            ),
            isInReviewBlock = false
        )

        private fun List<OxfordWords>.mapToUiModel(): List<WordItemUiModel> {
            return this.map { element ->
                WordItemUiModel(
                    id = element.id,
                    word = element.word,
                    partOfSpeech = element.partOfSpeech,
                    level = element.level,
                    isLearned = element.isLearned,
                    isInReviewBlock = (element.id % 2) == 0
                )
            }
        }

        private fun List<OxfordWords>.mapToReviewIndicatorList(): List<WordsWithReviewBlockIndicator> {
            return this.map { element ->
                WordsWithReviewBlockIndicator(
                    id = element.id,
                    word = element.word,
                    partOfSpeech = element.partOfSpeech,
                    level = element.level,
                    isLearned = element.isLearned,
                    isInReviewBlock = (element.id % 2) == 0
                )
            }
        }

        fun getUiModelMappedList(): List<WordItemUiModel> = fakeWordsList1.mapToUiModel()

        val fakeBlocksList = listOf(
            WordBlock(
                blockType = BlockTypes.ACTIVE,
                availableAt = LocalDate.now(),
                blockNumber = 1
            ),
            WordBlock(
                blockType = BlockTypes.ACTIVE,
                availableAt = LocalDate.now().plusDays(1),
                blockNumber = 1
            ),
            WordBlock(
                blockNumber = 2,
                blockType = BlockTypes.ACTIVE,
                availableAt = LocalDate.now().plusDays(2)
            ),
            WordBlock(
                blockNumber = 3,
                blockType = BlockTypes.PLANNED,
                availableAt = LocalDate.now().plusDays(4)
            ),
            WordBlock(
                blockNumber = 4,
                blockType = BlockTypes.PLANNED,
                availableAt = LocalDate.now().plusDays(2)
            ),
            WordBlock(
                blockNumber = 6,
                blockType = BlockTypes.LEARNED,
                completedAt = LocalDate.now().minusDays(4)
            ),
            WordBlock(
                blockNumber = 7,
                blockType = BlockTypes.LEARNED,
                completedAt = LocalDate.now().minusDays(3)
            )
        )
    }
}