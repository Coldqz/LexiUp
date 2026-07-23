package com.coldzz.lexiup.features.blocks.presentation.components

import android.os.Bundle
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.coldzz.lexiup.R
import com.coldzz.lexiup.features.blocks.domain.LearningLevelIndicator

/**
 * Main block component that could be changed to look like learned or custom block component.
 * Set parameters to null if you need to disable them.
 * */
@Composable
fun CoreWordBlockComponent(
    title: String,
    label: String?,
    learningLevelIndicator: LearningLevelIndicator?,
    actionButton: @Composable () -> Unit,
    actionOnElementClick: () -> Unit,
    modifier: Modifier = Modifier,
    dropDownItems: List<DropDownItemModel> = emptyList()
) {

    // creating saver object so that ve can save offset in rememberSaveable
    val offsetSaver = Saver<Offset, Bundle>(
        save = {
            Bundle().apply {
                putFloat("x", it.x)
                putFloat("y", it.y)
            }
        },
        restore = {
            Offset(
                x = it.getFloat("x"),
                y = it.getFloat("y")
            )
        }
    )
    var isDropDownExpanded by rememberSaveable { mutableStateOf(false) }

    var pressOffset by rememberSaveable(stateSaver = offsetSaver) { mutableStateOf(Offset.Zero) }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val density = LocalDensity.current

    var boxHeight by rememberSaveable { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16))
            .indication(interactionSource, LocalIndication.current)
            .pointerInput(true) {
                detectTapGestures(
                    onLongPress = {
                        pressOffset = it
                        isDropDownExpanded = true
                    },
                    onTap = {
                        actionOnElementClick()
                    },
                    onPress = {
                        // Start the ripple animation
                        val press = PressInteraction.Press(it)
                        interactionSource.emit(press)

                        // Wait for the finger to be lifted or the gesture to be canceled
                        val released = tryAwaitRelease()

                        // Finish the ripple if released, or cancel it if the gesture was interrupted
                        if (released) {
                            interactionSource.emit(PressInteraction.Release(press))
                        } else {
                            interactionSource.emit(PressInteraction.Cancel(press))
                        }
                    }
                )
            }
            .onSizeChanged {
                boxHeight = it.height
            }
    ) {
        ElevatedCard {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    // internal padding for card
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy (16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = colorResource(R.color.light_gray),
                                shape = RoundedCornerShape(16)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_book),
                            contentDescription = stringResource(R.string.word_icon)
                        )
                    }
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium
                        )
                        label?.let {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    learningLevelIndicator?.let {
                        Icon(
                            imageVector = when (learningLevelIndicator) {
                                LearningLevelIndicator.Zero ->
                                    ImageVector.vectorResource(LearningLevelIndicator.Zero.resourceId)

                                LearningLevelIndicator.One ->
                                    ImageVector.vectorResource(LearningLevelIndicator.One.resourceId)

                                LearningLevelIndicator.Two ->
                                    ImageVector.vectorResource(LearningLevelIndicator.Two.resourceId)

                                LearningLevelIndicator.Three ->
                                    ImageVector.vectorResource(LearningLevelIndicator.Three.resourceId)

                                LearningLevelIndicator.Four ->
                                    ImageVector.vectorResource(LearningLevelIndicator.Four.resourceId)
                            },
                            contentDescription = stringResource(R.string.learning_level_icon)
                        )
                    }
                }
                actionButton()
            }
        }
        if (dropDownItems.isNotEmpty()) {
            DropdownMenu(
                expanded = isDropDownExpanded,
                onDismissRequest = { isDropDownExpanded = false },
                offset = with(density) {
                    DpOffset(
                        x = pressOffset.x.toDp(),
                        y = pressOffset.y.toDp() - boxHeight.toDp()
                    )
                }
            ) {
                dropDownItems.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item.text) },
                        onClick = item.action
                    )
                }
            }
        }
    }
}