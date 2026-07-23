package com.coldzz.lexiup.features.quiz.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@Composable
fun WordChoiceComponent(
    word: String,
    onClickButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledTonalButton(
        onClick = onClickButton,
        shape = RoundedCornerShape(16),
        modifier = modifier
    ) {
        Text(
            text = word,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun WordChoiceComponentPreview() {
    LexiUpTheme {
        WordChoiceComponent(
            "Anger",
            {},
            modifier = Modifier.background(Color.Red)
        )
    }
}