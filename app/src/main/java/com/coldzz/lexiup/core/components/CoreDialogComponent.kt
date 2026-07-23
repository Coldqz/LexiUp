package com.coldzz.lexiup.core.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

/**
 * Core dialog component for all dialogs in the app.
 *
 * @param isErrorDialog can be used to set different colors for error version of the dialog.
 * */
@Composable
fun CoreDialogComponent(
    title: String,
    subTitle: String,
    @DrawableRes iconRes: Int,
    iconContentDescription: String,
    modifier: Modifier = Modifier,
    isErrorDialog: Boolean = false,
    buttons: @Composable () -> Unit
) {
    val containerColor =
        if (isErrorDialog) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer
    val borderColor =
        if (isErrorDialog) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
    val iconTint =
        if (isErrorDialog) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer

    Dialog(
        onDismissRequest = {}
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp)
                    .padding(24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = containerColor)
                        .border(
                            width = Dp.Hairline,
                            color = borderColor,
                            shape = CircleShape,
                        )
                )
                {
                    Icon(
                        painter = painterResource(iconRes),
                        contentDescription = iconContentDescription,
                        tint = iconTint,
                        modifier = Modifier
                            .size(70.dp)
                            .padding(16.dp)
                    )
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = subTitle,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(4.dp))
                buttons.invoke()
            }
        }
    }
}