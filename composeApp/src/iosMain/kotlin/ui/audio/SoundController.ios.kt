package ui.audio

import data.repository.PreferenceRepository
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSBundle
import platform.Foundation.NSURL
import kotlin.OptIn
import kotlin.String
import kotlin.apply
import kotlin.let

actual class SoundController actual constructor(
    private val preferenceRepository: PreferenceRepository
) : KoinComponent {

    private var playerButtonFeedback = createPlayer("files/sound_menu_button.wav", "wav")
    private var playerWarningFeedback = createPlayer("files/sound_menu_denied.ogg", "ogg")
    private var isSoundOn = true

    private val scope = CoroutineScope(Dispatchers.Default)
    private var prefsJob: Job? = null

    init {
        prefsJob?.cancel()
        prefsJob = scope.launch {
            preferenceRepository.preferences.collect { isSoundOn = it.sound }
        }
    }

    actual fun playSound(sound: AppSound) {
        if (isSoundOn) when (sound) {
            AppSound.ButtonFeedback -> if (!playerButtonFeedback.isPlaying()) playerButtonFeedback.play()
            AppSound.DeniedFeedback -> if (!playerWarningFeedback.isPlaying()) playerWarningFeedback.play()
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun createPlayer(resourceName: String, type: String): AVAudioPlayer {
        val path = NSBundle.mainBundle.pathForResource(resourceName, type)
        return path.let {
            val url = NSURL.fileURLWithPath(it!!)
            AVAudioPlayer(contentsOfURL = url, error = null).apply {
                prepareToPlay()
            }
        }
    }
}