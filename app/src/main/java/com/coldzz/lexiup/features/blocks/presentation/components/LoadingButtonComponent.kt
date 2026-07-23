package com.coldzz.lexiup.features.blocks.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingButtonComponent(
    text: String,
    isLoadingEnabled: Boolean,
    actionOnCreateButton: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = {
            // if loading is enabled then button turn off any actions
            if (!isLoadingEnabled) {
                actionOnCreateButton()
            }
        },
    ) {
        if (isLoadingEnabled) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(24.dp)
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
private fun LoadingButtonComponentPreview() {
    println("Start of preview")
    var isLoading by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Button(
            onClick = {
                isLoading = !isLoading
            }
        ) {
            Text("Enable")
        }

        LoadingButtonComponent(
            text = "Create block",
            isLoadingEnabled = isLoading,
            actionOnCreateButton = {
                println("Button was clicked")
            }
        )

        LoadingButtonComponent(
            text = "Create block",
            isLoadingEnabled = isLoading,
            actionOnCreateButton = {
                println("Button was clicked")
            }
        )
    }
}