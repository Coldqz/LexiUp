package com.coldzz.lexiup.features.words.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults.InputField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.coldzz.lexiup.R
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.features.words.presentation.WordItemUiModel

/**
[isExpanded] manage expansion state of the search bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    dataForSearch: List<WordItemUiModel>,
    suggestionsListMode: WordListComponentMode,
    actionOnElementClick: (Int) -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }

    val filteredList: List<WordItemUiModel> = remember(query, dataForSearch) {
        val trimmedQuery = query.trim()
        if (trimmedQuery.isEmpty()) {
            emptyList()
        } else {
            dataForSearch.asSequence()
                .filter { it.word.contains(trimmedQuery, ignoreCase = true) }
                .sortedWith(
                    compareBy<WordItemUiModel> {
                        // 1. Exact matches first
                        !it.word.equals(trimmedQuery, ignoreCase = true)
                    }.thenBy {
                        // 2. Starts with matches second
                        !it.word.startsWith(trimmedQuery, ignoreCase = true)
                    }.thenBy {
                        // 3. Shorter words first
                        it.word.length
                    }.thenBy {
                        // 4. Alphabetical order
                        it.word
                    }
                )
                .take(10)
                .toList()
        }
    }

    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "rotation"
    )

    SearchBar(
        modifier = modifier.fillMaxWidth(),
        inputField = {
            InputField(
                query = query,
                onQueryChange = { query = it },
                onSearch = { onExpandedChange(false) },
                expanded = isExpanded,
                onExpandedChange = onExpandedChange,
                leadingIcon = {
                    if (isExpanded) {
                        IconButton(
                            onClick = {
                                onExpandedChange(false)
                            },
                            modifier = Modifier
                                .rotate(rotation)
                        ) {
                            // We need to use arrowForward because of rotating animation
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = stringResource(R.string.back_icon),
                            )
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search_icon),
                            modifier = Modifier
                                .rotate(rotation)
                        )
                    }
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_words)
                    )
                },
            )
        },
        expanded = isExpanded,
        onExpandedChange = onExpandedChange
    )
    {
        if (filteredList.isNotEmpty()) {
            WordsListComponent(
                wordsList = filteredList,
                componentMode = suggestionsListMode,
                actionOnElementClick = actionOnElementClick,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun MySearchBarPreview() {
    MySearchBar(
        modifier = Modifier,
        dataForSearch = FakeDataSamples.getUiModelMappedList(),
        suggestionsListMode = WordListComponentMode.StandardMode(),
        isExpanded = false,
        onExpandedChange = {},
        actionOnElementClick = { _ -> }
    )
}

@Preview(showBackground = true, showSystemUi = true,
    device = "spec:parent=pixel_5,orientation=landscape"
)
@Composable
private fun MySearchBarPreviewHorizontal() {
    MySearchBar(
        modifier = Modifier,
        dataForSearch = FakeDataSamples.getUiModelMappedList(),
        suggestionsListMode = WordListComponentMode.StandardMode(),
        isExpanded = false,
        onExpandedChange = {},
        actionOnElementClick = { _ -> }
    )
}