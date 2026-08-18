package com.coldzz.lexiup.features.words.presentation

import com.coldzz.lexiup.core.data.remote.model.DictionaryResponse
import com.coldzz.lexiup.core.data.remote.model.WiktionaryResponse
import com.coldzz.lexiup.features.blocks.data.local.projection.WordDetailWithMeanings
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords
import com.coldzz.lexiup.features.words.data.local.entities.WordDetails
import com.coldzz.lexiup.features.words.data.local.entities.WordMeaning
import com.coldzz.lexiup.features.words.data.local.projection.WordWithDetails
import com.coldzz.lexiup.features.words.data.local.projection.WordsWithReviewBlockIndicator

fun OxfordWords.toUiModel(): WordItemUiModel {
    return WordItemUiModel(
        id = id,
        word = word,
        partOfSpeech = partOfSpeech,
        level = level,
        isLearned = isLearned,
        isInReviewBlock = false
    )
}

fun WordsWithReviewBlockIndicator.toUiModel(): WordItemUiModel {
    return WordItemUiModel(
        id = id,
        word = word,
        partOfSpeech = partOfSpeech,
        level = level,
        isLearned = isLearned,
        isInReviewBlock = isInReviewBlock
    )
}

fun WordWithDetails.toUiState(): WordDetailsUiState {
    return WordDetailsUiState(
        id = this.id,
        word = this.word,
        phonetic = this.wordDetails?.phonetic.orEmpty(),
        partOfSpeech = this.partOfSpeech,
        level = this.level,
        audioUs = this.wordDetails?.audioUs.orEmpty(),
        audioUk = this.wordDetails?.audioUk.orEmpty(),
        enableAmericanButton = !this.wordDetails?.audioUs.isNullOrBlank(),
        enableBritishButton = !this.wordDetails?.audioUk.isNullOrBlank(),
        definitionAndExamples = this.wordMeaning.map {
            DefinitionAndExampleModel(
                definition = it.definition,
                example = it.example.orEmpty()
            )
        },
        isInReviewBlock = isInReviewBlock
    )
}

fun createPlaceholderDetails(wordId: Int): WordDetailWithMeanings {
    return WordDetailWithMeanings(
        details = WordDetails(
            wordId = wordId,
            phonetic = "",
            audioUs = null,
            audioUk = null
        ),
        meanings = listOf(
            WordMeaning(
                wordId = wordId,
                definition = "No definition found for this word in dictionary.",
                example = ""
            )
        )
    )
}

fun WiktionaryResponse.extractAudio(): AudioFilesData {
    val allPages = this.query?.pages?.values?.flatMap { page ->
        page.imageInfo.orEmpty()
    }.orEmpty()

    val audioUs = allPages.find { it.url?.contains("-us") == true }?.url.orEmpty()
    val audioUk = allPages.find { it.url?.contains("-uk") == true }?.url.orEmpty()

    return AudioFilesData(
        audioUs = audioUs,
        audioUk = audioUk
    )
}
fun List<DictionaryResponse>.toDatabaseEntity(wordId: Int, partOfSpeech: String, audioData: AudioFilesData): WordDetailWithMeanings {

    // Api response can be list containing few DictionaryResponse objects,
    // so we need to join them together. Read comments below for .flatMap explanation.
    val allMeanings = this.flatMap { dictionaryResponse ->
        dictionaryResponse.meanings
    }

    val definitionsForPartOfSpeech = allMeanings
        // here we filter database response by part of speech
        .filter { it.partOfSpeech == partOfSpeech }
        /*
        * We cut out only definitions from the object, and we get single List<DefinitionsItem>.
        *
        * Btw if we use normal .map function instead of .flatMap here we will get List<List<DefinitionsItem>>.
        * This is because every DictionaryResponse object have List<MeaningsItem> with List<DefinitionsItem> inside,
        * and if we extract our definitions from List<MeaningsItem> with .map we will get list of definitions,
        * and since every definition is list then we will get list of lists i.e. List<List<DefinitionsItem>>.
        * */
        .flatMap { it.definitions }
        // then we map our List<DefinitionsItem> to uiModel
        .map {
            DefinitionAndExampleModel(
                definition = it.definition.orEmpty(),
                example = it.example.orEmpty()
            )
        }
        // if list it empty it means we have no definitions, so we just add placeholder
        .ifEmpty {
            listOf(
                DefinitionAndExampleModel(
                    definition = "No definitions for this part of speech were found",
                    example = ""
                )
            )
        }
    // and finally we create our WordDetailWithMeanings object with necessary data and insert it into the DB
    return WordDetailWithMeanings(
        details = WordDetails(
            wordId = wordId,
            phonetic = this.firstOrNull { !it.phonetic.isNullOrBlank() }?.phonetic.orEmpty(),
            audioUs = audioData.audioUs,
            audioUk = audioData.audioUk
        ),
        meanings = definitionsForPartOfSpeech.map {
            WordMeaning(
                wordId = wordId,
                definition = it.definition,
                example = it.example
            )
        }
    )
}
