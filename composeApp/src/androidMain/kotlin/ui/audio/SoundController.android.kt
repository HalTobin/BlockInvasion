package ui.audio

import android.content.Context
import android.media.MediaPlayer
import data.repository.PreferenceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.chapeaumoineau.blockinvasion.R
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class SoundController actual constructor(
    private val preferenceRepository: PreferenceRepository
): KoinComponent {

    private val context: Context by inject()
    private var playerButtonFeedback = MediaPlayer.create(context, R.raw.sound_menu_button)
    private var playerWarningFeedback = MediaPlayer.create(context, R.raw.sound_menu_denied)
    private var isSoundOn = true

    private val scope = CoroutineScope(Dispatchers.IO)
    private var prefsJob: Job? = null

    init {
        prefsJob?.cancel()
        prefsJob = scope.launch {
            preferenceRepository.preferences.collect { isSoundOn = it.sound }
        }
    }

    actual fun playSound(sound: AppSound) {
        if (isSoundOn) when (sound) {
            AppSound.ButtonFeedback -> if (!playerButtonFeedback.isPlaying) playerButtonFeedback.start()
            AppSound.DeniedFeedback -> if (!playerWarningFeedback.isPlaying) playerWarningFeedback.start()
        }
    }

}