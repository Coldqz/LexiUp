package com.coldzz.lexiup.core.data.local

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class AppTypeConverters {
    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime): Long {
        return date.atZone(ZoneId.systemDefault()).toEpochSecond()
    }

    @TypeConverter
    fun toLocalDateTime(millisSinceEpoch: Long): LocalDateTime {
        return Instant.ofEpochSecond(millisSinceEpoch).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }
}