package be.chaidev.chronote.ui.navigation

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import be.chaidev.chronote.ui.topic.fragments.TopicBrowserFragment
import be.chaidev.chronote.util.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
class MainFragmentFactory @Inject constructor() : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        Log.d(Constants.TAG, "mainfragmentfactory instantiate()")
        return when (className) {
            TopicBrowserFragment::class.java.name -> {
                TopicBrowserFragment()
            }
            else -> super.instantiate(classLoader, className)
        }
    }
}