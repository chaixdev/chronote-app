package be.chaidev.chronote.ui.navigation

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import be.chaidev.chronote.ui.topic.fragments.DetailFragment
import be.chaidev.chronote.ui.topic.fragments.FinalFragment
import be.chaidev.chronote.util.Constants
import be.chaidev.chronote.util.GlideManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MainFragmentFactory @Inject constructor(
    private val requestManager: GlideManager
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        Log.d(Constants.TAG, "mainfragmentfactory instantiate()")
        return when (className) {

//
//            TopicBrowserFragment::class.java.name -> {
//                val fragment = TopicBrowserFragment(viewModelFactory, requestManager)
//                fragment
//            }

            DetailFragment::class.java.name -> {
                val fragment = DetailFragment(requestManager)
                fragment
            }

            FinalFragment::class.java.name -> {
                val fragment = FinalFragment(requestManager)
                fragment
            }

            else -> super.instantiate(classLoader, className)
        }
    }
}