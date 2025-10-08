package com.coldzz.lexiup.features.blocks.data.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.coldzz.lexiup.features.words.domain.model.OxfordWords

data class WordBlockWithOxfordWords(
    @Embedded val wordBlock: WordBlock,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = WordBlockOxfordWords::class,
            parentColumn = "word_block_id",
            entityColumn = "word_id"
        )
    )
    val words:List<OxfordWords>
)