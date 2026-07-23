package com.coldzz.lexiup.features.stats.presentation

sealed interface StatsScreenEvent {
    data object CloseScreen : StatsScreenEvent
}