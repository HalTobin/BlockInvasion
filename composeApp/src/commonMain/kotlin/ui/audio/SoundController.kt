package ui.audio

import data.repository.PreferenceRepository

expect class SoundController(preferenceRepository: PreferenceRepository) {
    fun playSound(sound: AppSound)
}

enum class AppSound { ButtonFeedback, DeniedFeedback }