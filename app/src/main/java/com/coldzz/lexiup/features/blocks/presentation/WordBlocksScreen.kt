package com.coldzz.lexiup.features.blocks.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.coldzz.lexiup.R
import com.coldzz.lexiup.features.blocks.data.local.WordBlockWithOxfordWords
import com.coldzz.lexiup.features.blocks.presentation.components.ActiveBlocksCounter
import com.coldzz.lexiup.features.blocks.presentation.components.BlockCategoryDivider
import com.coldzz.lexiup.features.blocks.presentation.viewmodel.WordBlockViewModel
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@Composable
fun WordBlockScreen(wordBlockViewModel: WordBlockViewModel = hiltViewModel()) {
    val blocklist by wordBlockViewModel.blocksList.collectAsState()
    WordBlocksScreenContent(blocklist)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WordBlocksScreenContent(
    blockList: List<WordBlockWithOxfordWords>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.learning),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Information icon"
                        )
                    }
                }
            )
        }
    ) {
        innerPadding ->
        Column(
            Modifier.padding(innerPadding)
        ) {
            BlockCategoryDivider(
                title = "Active blocks",
                enableAddButton = true,
                counter = ActiveBlocksCounter(3, 4)
            )
        }
    }
}

@Preview
@Composable
private fun WordBlocksScreenContentPreview() {
    LexiUpTheme {
//        WordBlocksScreenContent()
    }
}