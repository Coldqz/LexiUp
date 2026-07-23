package com.coldzz.lexiup.features.blocks.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.R
import com.coldzz.lexiup.features.blocks.presentation.dashedBorder
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@Composable
fun ReviewScreenNoWordsCaseComponent(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .dashedBorder(
                brush = SolidColor(
                    value = MaterialTheme.colorScheme.outline
                ),
                shape = RoundedCornerShape(16.dp),
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .sizeIn(
                    minHeight = 400.dp
                )
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24))
                    .background(color = MaterialTheme.colorScheme.primary)
            )
            {
                Icon(
                    painter = painterResource(R.drawable.ic_bookmark),
                    contentDescription = stringResource(R.string.add_to_review_block_icon),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(85.dp)
                        .padding(16.dp)
                )
            }
            Text(
                text = stringResource(R.string.no_saved_words_yet),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = stringResource(R.string.no_saved_words_description),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ReviewScreenNoWordsCaseComponentPreview() {
    LexiUpTheme {
        ReviewScreenNoWordsCaseComponent()
    }
}