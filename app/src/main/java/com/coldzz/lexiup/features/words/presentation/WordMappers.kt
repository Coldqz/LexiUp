package com.coldzz.lexiup.features.words.presentation

import com.coldzz.lexiup.core.data.remote.model.FreeDictionaryResponse
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

/*
* This creates empty placeholder in case there is no data in api. Though we can insert audio if it is available.
* */
fun createPlaceholderDetails(wordId: Int, audio: String? = null): WordDetailWithMeanings {
    return WordDetailWithMeanings(
        details = WordDetails(
            wordId = wordId,
            phonetic = "",
            audioUrl = audio
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

fun WordWithDetails.toUiState(): WordDetailsUiState {
    return WordDetailsUiState(
        id = this.id,
        word = this.word,
        phonetic = this.wordDetails?.phonetic.orEmpty(),
        audioUrl = this.wordDetails?.audioUrl.orEmpty(),
        enablePlayButton = !this.wordDetails?.audioUrl.isNullOrBlank(),
        partOfSpeech = this.partOfSpeech,
        level = this.level,
        definitionAndExamples = this.wordMeaning.map {
            DefinitionAndExampleModel(
                definition = it.definition,
                example = it.example.orEmpty()
            )
        },
        isInReviewBlock = isInReviewBlock
    )
}

/**
 * Returns null if no audio was found.
 * */
fun WiktionaryResponse.extractAudio(): String? {
    val allPages = this.query?.pages?.values?.flatMap { page ->
        page.imageInfo.orEmpty()
    }.orEmpty()

    val audioUs = allPages.find { it.url?.contains("-us") == true }?.url

    return audioUs
}

/**
 * This function map whole api response to Database format.
 * It chooses what data should we use as definitions and examples.
 * @param [audioUrl] keep it null if there is no audio file available
 * */
fun FreeDictionaryResponse.toDatabaseEntity(
    wordId: Int,
    word: String,
    partOfSpeech: String,
    audioUrl: String? = null
): WordDetailWithMeanings {

    // if api response is empty or null then add placeholder and quit function
    if (this.entries.isNullOrEmpty()) {
        return createPlaceholderDetails(wordId, audioUrl)
    }

    val correctPartOfSpeech = partOfSpeechConverter(word, partOfSpeech)

    // pick part of speech we need, if there is no part of speech we need then return placeholder
    val correctEntry = this.entries.firstOrNull() { item ->
        correctPartOfSpeech == item.partOfSpeech
    } ?: return createPlaceholderDetails(wordId, audioUrl)

    if (correctEntry.senses.isNullOrEmpty()) {
        return createPlaceholderDetails(wordId, audioUrl)
    }

    val definitionAndExampleModel = correctEntry.senses.flatMap { senseEntry ->
        when {
            // If the sense has examples then use the sense itself
            !senseEntry.examples.isNullOrEmpty() -> {
                listOf(
                    DefinitionAndExampleModel(
                        definition = senseEntry.definition.orEmpty(),
                        example = senseEntry.examples.firstOrNull().orEmpty()
                    )
                )
            }
            // If the sense has no examples but has subsenses, use subsenses instead
            !senseEntry.subsenses.isNullOrEmpty() -> {
                senseEntry.subsenses.map { subSenseEntry ->
                    DefinitionAndExampleModel(
                        definition = subSenseEntry.definition.orEmpty(),
                        example = subSenseEntry.examples?.firstOrNull().orEmpty()
                    )
                }
            }
            // Otherwise, use sense without examples
            else -> {
                listOf(
                    DefinitionAndExampleModel(
                        definition = senseEntry.definition.orEmpty(),
                        example = ""
                    )
                )
            }
        }
    }

    return WordDetailWithMeanings(
        details = WordDetails(
            wordId = wordId,
            phonetic = correctEntry.pronunciations?.firstOrNull()?.transcription.orEmpty(),
            audioUrl = audioUrl
        ),
        meanings = definitionAndExampleModel.map {
            WordMeaning(
                wordId = wordId,
                definition = it.definition,
                example = it.example
            )
        }
    )
}

/**
 * Oxford 5000 list (which is our database core) have different part of speech naming that is in FreeDictionary API, most are the same but not all of them.
 * In this function we manually convert them so that we can find them in api response.
 * */
private fun partOfSpeechConverter(word: String, partOfSpeech: String): String {
    // converting part of speech to different naming
    val result = when (partOfSpeech) {
        "adjective", "adverb", "conjunction", "determiner", "noun", "preposition", "pronoun", "verb" -> partOfSpeech
        "auxiliary verb", "linking verb", "modal verb" -> "verb"
        "number" -> "numeral"
        "ordinal number" -> "adjective"
        "exclamation" -> "interjection"
        "infinitive marker" -> "particle"
        else -> partOfSpeech
    }
    // here we manually handle unique exceptions
    return when {
        // somehow word "no" have no interjection part of speech but does have particle,
        // so we manually need to change it for our api mapping function
        word == "no" && partOfSpeech == "exclamation" -> "particle"
        else -> result
    }
}