package com.coldzz.lexiup.features.words.presentation.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.R
import com.coldzz.lexiup.features.words.data.local.entities.LevelCerf
import com.coldzz.lexiup.features.words.domain.repository.ReviewBlockIndicator

private const val TAG = "WordListElement"

@Composable
fun WordListElement(
    title: String,
    level: LevelCerf,
    partOfSpeech: String,
    isAddedToReviewBlock: Boolean,
    actionAddToReviewBlock: () -> Unit,
    actionRemoveFromReviewBlock: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
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
                        "CERF Level: ${level.toString()}",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(partOfSpeech, style = MaterialTheme.typography.labelMedium)
                }
            }
            if (isAddedToReviewBlock) {
                IconButton(
                    onClick = actionRemoveFromReviewBlock
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(ReviewBlockIndicator.Remove.resourceId),
                        contentDescription = stringResource(R.string.added_as_bookmark_icon)
                    )
                }
            } else {
                IconButton(
                    onClick = actionAddToReviewBlock
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(ReviewBlockIndicator.Add.resourceId),
                        contentDescription = stringResource(R.string.add_as_bookmark)
                    )
                }
            }
        }
    }
}

@Preview()
@Composable
private fun WordListElementPreview() {
    WordListElement(
        "test",
        LevelCerf.A2,
        "asdas",
        true,
        {},
        {}
    )
}