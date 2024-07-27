package feature.settings

sealed class SettingsEvent {
    data class ChangePixelNumber(val value: Int): SettingsEvent()
    data class ChangeGriX(val value: Int): SettingsEvent()
    data class ChangeGridY(val value: Int): SettingsEvent()
    data class ChangeSoundOn(val value: Boolean): SettingsEvent()
    data class ChangeTheme(val value: String): SettingsEvent()
    data class ChangeLanguage(val value: String): SettingsEvent()
}