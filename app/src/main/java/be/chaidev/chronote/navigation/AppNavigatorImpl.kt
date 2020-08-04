package be.chaidev.chronote.navigation

import androidx.fragment.app.FragmentActivity
import be.chaidev.chronote.R
import be.chaidev.chronote.ui.ButtonsFragment
import be.chaidev.chronote.ui.LogsFragment
import be.chaidev.chronote.ui.chronometer.ChronoFragment

class AppNavigatorImpl(private val activity: FragmentActivity) : AppNavigator {

    override fun navigateTo(screen: Screens) {
        val fragment = when (screen) {
            Screens.BUTTONS -> ButtonsFragment()
            Screens.LOGS -> LogsFragment()
            Screens.CHRONO -> ChronoFragment()
        }

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }
}
