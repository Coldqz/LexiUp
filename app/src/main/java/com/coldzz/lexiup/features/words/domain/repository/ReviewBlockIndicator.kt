package com.coldzz.lexiup.features.words.domain.repository

import androidx.annotation.DrawableRes
import com.coldzz.lexiup.R

enum class ReviewBlockIndicator(@param:DrawableRes val resourceId: Int) {
    Add(R.drawable.ic_bookmark_add),
    Added(R.drawable.ic_bookmark_added),
    Remove(R.drawable.ic_bookmark_remove),
}