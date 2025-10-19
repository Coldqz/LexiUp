package com.coldzz.lexiup.features.words.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.coldzz.lexiup.R
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.features.words.data.local.entities.OxfordWords

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    modifier: Modifier = Modifier,
    dataForSearch: List<OxfordWords>
) {
    // Controls expansion state of the search bar
    var expanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    val filteredList: List<OxfordWords> = remember(query) {
        if (query.isBlank()) emptyList()
        else dataForSearch.asSequence()
            .filter { it.word.contains(query, ignoreCase = true) }
            .take(10)
            .toList()
    }

    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "rotation"
    )

    SearchBar(
        modifier = modifier,
        inputField = {
            InputField(
                query = query,
                onQueryChange = { query = it },
                onSearch = { expanded = false },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                leadingIcon = {
                    if (expanded) {
                        IconButton(
                            onClick = {
                                expanded = false
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
                        text = "Search words"
                    )
                },
            )
        },
        expanded = expanded,
        onExpandedChange = { expanded = it })
    {
        if (filteredList.isNotEmpty()) {
            LazyColumn {
                items(filteredList) { element ->
                    WordListElement(
                        title = element.word,
                        level = element.level,
                        partOfSpeech = element.partOfSpeech,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun MySearchBarPreview() {
    MySearchBar(
        modifier = Modifier,
        dataForSearch = FakeDataSamples.fakeWordsList1
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TestPreview() {
    var expanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    SearchBar(inputField = {
        InputField(
            query = query,
            onQueryChange = { query = it },
            onSearch = {},
            expanded = expanded,
            onExpandedChange = { expanded = it })
    }, expanded = expanded, onExpandedChange = { expanded = it }) { }
}