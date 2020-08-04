package be.chaidev.chronote.navigation

interface AppNavigator {
    // Navigate to a given screen.
    fun navigateTo(screen: Screens)
}

enum class Screens {
    BUTTONS,
    LOGS,
    CHRONO
}
