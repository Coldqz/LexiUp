package com.coldzz.lexiup.features.blocks.domain

import androidx.annotation.DrawableRes
import com.coldzz.lexiup.R

enum class LearningLevelIndicator(@param:DrawableRes val resourceId: Int) {
    One(R.drawable.ic_learning_level_1),
    Two(R.drawable.ic_learning_level_2),
    Three(R.drawable.ic_learning_level_3),
    Four(R.drawable.ic_learning_level_4)
}
