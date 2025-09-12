package com.coldzz.lexiup.features.profile.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_stats"
)
data class UserStats(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    @ColumnInfo(name = "photo_url") val photoUrl: String,
    val streak: Int,
    @ColumnInfo(name = "blocks_learned") val blocksLearned: Int,
    @ColumnInfo(name = "words_learned") val wordsLearned: Int
)