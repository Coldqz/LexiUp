package com.coldzz.lexiup.features.quiz.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.R
import com.coldzz.lexiup.features.words.presentation.components.PartOfSpeechIconComponent
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickQuizScreenComponent(
    answerOptions: List<AnswerOptionUiModel>,
    definition: String,
    partOfSpeech: String,
    currentProgressValue: Int,
    maxProgressValue: Int,
    showCongratulationDialog: Boolean = false,
    skippedWords: List<String> = emptyList(),
    onDialogButtonClick: () -> Unit,
    onOptionClick: (Int) -> Unit,
    onCloseButtonAction: () -> Unit
) {

    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var showSkippedWordsDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = onCloseButtonAction,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.back_icon),
                        )
                    }
                },
                actions = {
                    if (skippedWords.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                showSkippedWordsDialog = true
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_warning),
                                contentDescription = "Quiz warning icon",
                                tint = Color(0xFFFFD600)
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehaviour
            )
        }
    ) { paddingValues ->

        if (showCongratulationDialog) {
            CongratulationDialog(onDialogButtonClick)
        }

        if (showSkippedWordsDialog) {
            SkippedWordsDialog(
                skippedWords = skippedWords,
                onDismiss = {
                    showSkippedWordsDialog = false
                }
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            QuizProgressBarComponent(
                currentValue = currentProgressValue,
                maxValue = maxProgressValue,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.which_word_matches_definition),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Card(
                modifier = Modifier
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentSize()
                ) {
                    PartOfSpeechIconComponent(
                        partOfSpeech = partOfSpeech,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "\"$definition\"",
                            style = MaterialTheme.typography.bodyLarge,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(vertical = 22.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(1.dp))
            for (option in answerOptions) {
                WordChoiceComponent(
                    /*if answer option have its partOfSpeech then add it to the end of the word,
                    if not then show just the word*/
                    word = if (option.partOfSpeech.isNullOrBlank()) {
                        option.word
                    } else {
                        "${option.word} (${option.partOfSpeech})"
                    },
                    onClickButton = {
                        onOptionClick(option.wordId)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 56.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Preview(
    showSystemUi = true, showBackground = true,
    device = "spec:parent=pixel_5,orientation=landscape"
)
@Composable
private fun PickQuizScreenComponentPreview() {
    LexiUpTheme {
        PickQuizScreenComponent(
            answerOptions = listOf(
                AnswerOptionUiModel(wordId = 0, word = "Joy"),
                AnswerOptionUiModel(0, "Sadness"),
                AnswerOptionUiModel(0, "Anger"),
                AnswerOptionUiModel(0, "Fear")
            ),
            definition = "A feeling of great pleasure and happiness.",
            partOfSpeech = "noun",
            currentProgressValue = 2,
            maxProgressValue = 10,
            onOptionClick = {},
            onCloseButtonAction = {},
            showCongratulationDialog = false,
            onDialogButtonClick = {}
        )
    }
}