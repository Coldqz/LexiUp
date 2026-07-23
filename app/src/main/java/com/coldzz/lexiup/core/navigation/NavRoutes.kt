package com.coldzz.lexiup.core.navigation

import androidx.annotation.DrawableRes
import com.coldzz.lexiup.R
import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoutes(val name: String, @param:DrawableRes val iconRes: Int) {
    @Serializable
    data object StatsScreen : NavRoutes(name = "Progress", iconRes = R.drawable.ic_progress)

    @Serializable
    data object LearningScreen : NavRoutes(name = "Learning", iconRes = R.drawable.ic_learning)

    @Serializable
    data object WordsScreen : NavRoutes(name = "Words", iconRes = R.drawable.ic_book)

    @Serializable
    data object ReviewBlock: NavRoutes(name = "Review block", iconRes = 0)

    @Serializable
    data object BlockCreatingScreen: NavRoutes(name = "BlockCreatingScreen", iconRes = 0)

    @Serializable
    data class BlockWordsList(val blockId: Int): NavRoutes(name = "ShowWordsScreen", iconRes = 0)

    @Serializable
    data class WordDetailsScreen(val wordId: Int): NavRoutes(name = "WordDetails", iconRes = 0)

    @Serializable
    data class PickQuizScreen(val blockId: Int, val saveProgressChanges: Boolean = true): NavRoutes(name = "PickQuizScreen", iconRes = 0)

    @Serializable
    data object OnBoardingScreen: NavRoutes(name = "OnBoardingScreen", iconRes = 0)
}