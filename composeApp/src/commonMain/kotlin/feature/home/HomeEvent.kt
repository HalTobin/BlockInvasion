package feature.home

sealed class HomeEvent {
    data class SendScreenSize(val x: Int, val y: Int): HomeEvent()
}