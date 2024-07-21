package com.chapeaumoineau.pixelinvasion.feature.settings

sealed class SettingsEvent {
    data class ChangePixelNumber(val value: Int): SettingsEvent()
    data class ChangeGriX(val value: Int): SettingsEvent()
    data class ChangeGridY(val value: Int): SettingsEvent()
}