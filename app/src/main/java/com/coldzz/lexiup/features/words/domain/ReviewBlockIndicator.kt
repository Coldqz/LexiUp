package com.coldzz.lexiup.features.words.domain

import androidx.annotation.DrawableRes
import com.coldzz.lexiup.R

enum class ReviewBlockIndicator(@param:DrawableRes val resourceId: Int) {
    Add(R.drawable.ic_bookmark_add),
    Remove(R.drawable.ic_bookmark_remove),
}