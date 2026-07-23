package com.coldzz.lexiup.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@Composable
fun CircularWordsProgress(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String,
    percentageForIndicator: Float,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
    innerPadding: Dp = 34.dp
) {
    var textSize by remember { mutableStateOf(IntSize.Zero) }

    val density = LocalDensity.current
    val circleSize = remember(textSize, density) {
        with(density) {
            // pick bigger measure so that circle would be even
            max(
                textSize.width.toDp(),
                textSize.height.toDp()
            )
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .onSizeChanged {
                    textSize = it
                }
                .padding(innerPadding)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = subtitle,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelSmall
            )
        }
        CircularProgressIndicator(
            progress = {
                percentageForIndicator / 100
            },
            strokeWidth = strokeWidth,
            modifier = Modifier
                .size(circleSize)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun CircularWordsProgressPreview() {
    LexiUpTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(34.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularWordsProgress(
                title = "78.7%",
                subtitle = "GOAL REACHED",
                percentageForIndicator = 78.7f,
                strokeWidth = 16.dp
            )

            CircularWordsProgress(
                title = "10",
                subtitle = "Words",
                percentageForIndicator = 100f,
                strokeWidth = 4.dp,
                innerPadding = 12.dp
            )
        }
    }
}
