package com.coldzz.lexiup.features.user.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "user_stats"
)
data class UserStats(
    @PrimaryKey val id: Int,
    val streak: Int,
    @ColumnInfo(name = "blocks_learned_today") val blocksLearnedToday: Int?,
    @ColumnInfo(name = "new_blocks_learned_today") val newBlocksLearnedToday: Int?,
    @ColumnInfo(name = "last_update_date") val lastUpdateDate: LocalDate?
)