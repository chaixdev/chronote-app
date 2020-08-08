package be.chaidev.chronote.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import be.chaidev.chronote.ui.topic.TopicBrowserFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
class MainFragmentFactory @Inject constructor(
    private val parameter: String

) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            TopicBrowserFragment::class.java.name ->{
                TopicBrowserFragment()
            }
            else -> super.instantiate(classLoader, className)
        }
    }
}