package com.coldzz.lexiup.features.blocks.domain

import androidx.annotation.DrawableRes
import com.coldzz.lexiup.R

enum class LearningLevelIndicator(@param:DrawableRes val resourceId: Int) {
    Zero(R.drawable.ic_learning_level_0),
    One(R.drawable.ic_learning_level_1),
    Two(R.drawable.ic_learning_level_2),
    Three(R.drawable.ic_learning_level_3),
    Four(R.drawable.ic_learning_level_4);

    // increase level by one, if it is already max level then just return it to avoid errors
    fun next(): LearningLevelIndicator {
        return if (this == entries.last()) this else entries[this.ordinal + 1]
    }

    // function to check if level is the last in enum
    fun isLastLevel(): Boolean = this == LearningLevelIndicator.entries.last()

    // check if level is zero, i.e. first time learning
    fun isFirstLearnStatus(): Boolean = this == Zero

    // learning intervals
    fun nextIntervalDays(): Long {
        return when (this) {
            Zero -> 0
            One -> 1
            Two -> 3
            Three -> 7
            Four -> 0
        }
    }
}