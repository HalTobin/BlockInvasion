package ui.audio

import blockinvasion.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

object AudioRes {
    const val BUTTON_FEEDBACK = "files/audio/sound_menu_button.ogg"

    suspend fun getButtonFeedBackSoundBytes() = BUTTON_FEEDBACK.toResBytes()
}

@OptIn(ExperimentalResourceApi::class)
fun String.toResUri() = Res.getUri(this)

@OptIn(ExperimentalResourceApi::class)
suspend fun String.toResBytes() = Res.readBytes(this)