package com.coldzz.lexiup.features.words.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.R
import com.coldzz.lexiup.core.common.CerfLevel
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.features.words.domain.ReviewBlockIndicator

private const val TAG = "WordListElement"

/**
 * Can be set up with [componentMode]. Have 3 modes have 3 modes
 * [WordListElementMode.MultiSelectMode], [WordListElementMode.BookmarkMode],
 * [WordListElementMode.StandardMode]
 */
@Composable
fun WordListElement(
    title: String,
    level: CerfLevel,
    partOfSpeech: String,
    componentMode: WordListElementMode,
    actionOnElementClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(
                onClick = actionOnElementClick,
                role = Role.Button
            )
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = colorResource(R.color.light_gray),
                            shape = RoundedCornerShape(16)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_book),
                        contentDescription = stringResource(R.string.word_icon)
                    )
                }
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(title, style = MaterialTheme.typography.titleMedium)
                    Text(
                        "CERF Level: $level",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(partOfSpeech, style = MaterialTheme.typography.labelMedium)
                }
            }

            /*
            * check componentMode Mode and pass correct parameters to the IconButton,
            * if iconConfiguration return null StandardMode is picked and no icon need to be on screen
            * */
            componentMode.getIconConfiguration()?.let { iconConfig ->
                IconButton(
                    onClick = {
                        iconConfig.onClick()
                        Log.d(TAG, "onSelectedChange was invoked")
                    }
                ) {
                    Icon(
                        imageVector = iconConfig.imageVector,
                        contentDescription = iconConfig.contentDescription
                    )
                }
            }
        }
    }
}

/**
 * Core class for managing IconButton logic,
 * have 3 modes [MultiSelectMode], [BookmarkMode], [StandardMode], each of them unique parameters.
 * [StandardMode] means that element won't have any mode, just simple list element.
 * */
sealed class WordListElementMode {
    /**
     * Set up [WordElementIconConfig] object based on chosen mode.
     * */
    @Composable
    abstract fun getIconConfiguration(): WordElementIconConfig?

    class StandardMode : WordListElementMode() {
        @Composable
        override fun getIconConfiguration(): WordElementIconConfig? = null
    }

    class MultiSelectMode(
        val checked: Boolean,
        val onSelectedChange: () -> Unit
    ) : WordListElementMode() {
        @Composable
        override fun getIconConfiguration(): WordElementIconConfig =
            WordElementIconConfig(
                onClick = onSelectedChange,
                imageVector = if (checked)
                    ImageVector.vectorResource(R.drawable.ic_checked_circle)
                else
                    ImageVector.vectorResource(R.drawable.ic_circle),
                contentDescription = if (checked)
                    stringResource(R.string.select_icon_checked)
                else
                    stringResource(R.string.select_icon_unchecked)

            )
    }

    class BookmarkMode(
        val addedToReviewBlock: Boolean,
        val actionAddToReviewBlock: () -> Unit,
        val actionRemoveFromReviewBlock: () -> Unit
    ) : WordListElementMode() {
        @Composable
        override fun getIconConfiguration(): WordElementIconConfig = WordElementIconConfig(
            onClick = if (addedToReviewBlock)
                actionRemoveFromReviewBlock
            else
                actionAddToReviewBlock,
            imageVector = if (addedToReviewBlock)
                ImageVector.vectorResource(ReviewBlockIndicator.Remove.resourceId)
            else
                ImageVector.vectorResource(ReviewBlockIndicator.Add.resourceId),
            contentDescription = if (addedToReviewBlock)
                stringResource(R.string.added_as_bookmark_icon)
            else
                stringResource(R.string.add_as_bookmark)
        )
    }
}

/**
 * Class for storing Icon configuration.
 * All [WordListElementMode] children use it to define icon config and apply in compose component.
 * */
data class WordElementIconConfig(
    val onClick: () -> Unit,
    val imageVector: ImageVector,
    val contentDescription: String
)

@Preview
@Composable
private fun WordListElementPreview() {
    Column {
        with(FakeDataSamples.getUiModelMappedList()[0]) {
            WordListElement(
                title = word,
                level = level,
                partOfSpeech = partOfSpeech,
                actionOnElementClick = {},
                componentMode = WordListElementMode.BookmarkMode(true, {}, {})
            )
            WordListElement(
                title = word,
                level = level,
                partOfSpeech = partOfSpeech,
                actionOnElementClick = {},
                componentMode = WordListElementMode.BookmarkMode(false, {}, {})
            )
        }
    }
}

@Preview
@Composable
private fun WordListElementMultiselectPreview() {
    Column {
        with(FakeDataSamples.getUiModelMappedList()[0]) {
            WordListElement(
                title = word,
                level = level,
                partOfSpeech = partOfSpeech,
                actionOnElementClick = {},
                componentMode = WordListElementMode.MultiSelectMode(true) {}
            )
            WordListElement(
                title = word,
                level = level,
                partOfSpeech = partOfSpeech,
                actionOnElementClick = {},
                componentMode = WordListElementMode.MultiSelectMode(false) {}
            )
        }

    }
}

@Preview
@Composable
private fun WordListElementStandardPreview() {
    with(FakeDataSamples.getUiModelMappedList()[0]) {
        WordListElement(
            title = word,
            level = level,
            partOfSpeech = partOfSpeech,
            actionOnElementClick = {},
            componentMode = WordListElementMode.StandardMode()
        )
    }
}