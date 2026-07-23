package com.coldzz.lexiup.features.words.presentation.components

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.features.words.presentation.WordItemUiModel
import com.coldzz.lexiup.ui.theme.LexiUpTheme

/**
 * Component with search bar and list of elements.
 *
 * [searchBarListMode] allows us to set up search bar. Set null if you want to hide it.
 *
 * Default dataset for [MySearchBar] searching is the one-page displays i.e [wordsList],
 * but if we need to search in other lists we can change [searchBarList].
 *
 * We can specify [searchBarListMode] to change list mode in searching suggestions.
 *
 * Also, if you want to add some last list element use [lastListElement] if not keep it null.
 *
 * [emptyListComposable] will be displayed when wordsList is empty, set it to null to disable this functionality.
 *
 * [bottomBar] can be set to null to disable it.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordListScreenComponent(
    wordsList: List<WordItemUiModel>,
    searchBarList: List<WordItemUiModel> = wordsList,
    searchBarListMode: WordListComponentMode?,
    descriptionUnderSearchBar: @Composable (() -> Unit)? = null,
    lastListElement: @Composable (() -> Unit)? = null,
    emptyListComposable: @Composable ((PaddingValues) -> Unit)? = null,
    actionOnElementClick: (Int) -> Unit,
    listMode: WordListComponentMode,
    bottomBar: @Composable (() -> Unit)? = null,
) {
    var isSearchBarExpanded by rememberSaveable { mutableStateOf(false) }
    val searchBarAnimatedPadding by animateDpAsState(
        targetValue = if (isSearchBarExpanded) 0.dp else 16.dp
    )

    Scaffold(
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = searchBarAnimatedPadding)
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                searchBarListMode?.let {
                    MySearchBar(
                        dataForSearch = searchBarList,
                        suggestionsListMode = searchBarListMode,
                        actionOnElementClick = actionOnElementClick,
                        isExpanded = isSearchBarExpanded,
                        onExpandedChange = { isSearchBarExpanded = it },
                    )
                }
            }
        },
        bottomBar = {
            bottomBar?.let {
                if (!isSearchBarExpanded) {
                    bottomBar()
                }
            }
        }
    ) { paddingValues ->
        if (emptyListComposable == null) {
            WordsListComponent(
                wordsList = wordsList,
                componentMode = listMode,
                lastListElement = lastListElement,
                descriptionUnderSearchBar = descriptionUnderSearchBar,
                actionOnElementClick = actionOnElementClick,
                modifier = Modifier.padding(paddingValues),
            )
        } else {
            if (wordsList.isEmpty()) {
                emptyListComposable(paddingValues)
            } else {
                WordsListComponent(
                    wordsList = wordsList,
                    componentMode = listMode,
                    lastListElement = lastListElement,
                    descriptionUnderSearchBar = descriptionUnderSearchBar,
                    actionOnElementClick = actionOnElementClick,
                    modifier = Modifier.padding(paddingValues),
                )
            }

        }
    }
}

@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true,
    device = "spec:parent=pixel_5,orientation=landscape"
)
@Composable
private fun WordListScreenPreview() {
    LexiUpTheme {
        WordListScreenComponent(
            FakeDataSamples.getUiModelMappedList(),
            searchBarListMode = WordListComponentMode.BookmarkMode({}, {}),
            actionOnElementClick = { _ -> },
            listMode = WordListComponentMode.BookmarkMode({}, {})
        )
    }
}