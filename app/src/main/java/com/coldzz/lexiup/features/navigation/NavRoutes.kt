package com.coldzz.lexiup.features.navigation

import androidx.annotation.DrawableRes
import com.coldzz.lexiup.R
import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoutes(val name: String, @param:DrawableRes val iconRes: Int) {
    @Serializable
    data object HomeScreen : NavRoutes("Home", R.drawable.ic_home)

    @Serializable
    data object LearningScreen : NavRoutes("Learning", R.drawable.ic_learning)

    @Serializable
    data object WordsScreen : NavRoutes("Words", R.drawable.ic_book)

    @Serializable
    data object ReviewBlock: NavRoutes("Review block", iconRes = 0)
}