package ui.audio

import data.repository.PreferenceRepository
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import platform.AVFAudio.AVAudioPlayer
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryPlayAndRecord
import platform.AVFAudio.AVAudioSessionModeDefault
import platform.AVFAudio.AVAudioSessionSetActiveOptionNotifyOthersOnDeactivation
import platform.AVFAudio.setActive
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.play
import platform.Foundation.NSBundle
import platform.Foundation.NSURL

actual class SoundController actual constructor(
    private val preferenceRepository: PreferenceRepository
) : KoinComponent {

    private val audioSession = AVAudioSession.sharedInstance()

    //private var playerButtonFeedback = createPlayer("sound_menu_button", "wav")
    //private var playerWarningFeedback = createPlayer("sound_menu_denied", "wav")
    private var isSoundOn = true

    private val scope = CoroutineScope(Dispatchers.Default)
    private var prefsJob: Job? = null

    init {
        //configureAudioSession()
        prefsJob?.cancel()
        prefsJob = scope.launch {
            preferenceRepository.preferences.collect { isSoundOn = it.sound }
        }
    }

    actual fun playSound(sound: AppSound) {
        println("Play sound: ${sound.name}")
        if (isSoundOn) println("${sound.name} played")
            /*when (sound) {
            AppSound.ButtonFeedback -> if (playerButtonFeedback.status == 0L) playerButtonFeedback.play()
            AppSound.DeniedFeedback -> if (playerWarningFeedback.status == 0L) playerWarningFeedback.play()
        }*/
    }

    private fun createPlayer(resourceName: String, type: String): AVPlayer {
        val path = NSBundle.mainBundle.pathForResource(resourceName, type)
        val url = NSURL.fileURLWithPath(path!!)

        /*val audioSession = AVAudioSession.sharedInstance()
        audioSession.setCategory(
            category = AVAudioSessionCategoryPlayAndRecord,
            mode = AVAudioSessionModeDefault,
            options = AVAudioSessionSetActiveOptionNotifyOthersOnDeactivation,
            error = null
        )
        audioSession.setActive(true, error = null)*/
        //AVPlayer()
        return AVPlayer.playerWithURL(url)
        /*return AVPlayer(contentsOfURL = url, error = null).apply {
            prepareToPlay()
        }*/
    }

}