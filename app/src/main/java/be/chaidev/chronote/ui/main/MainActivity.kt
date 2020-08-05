package be.chaidev.chronote.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import be.chaidev.chronote.R
import be.chaidev.chronote.ui.topic_browser.TopicBrowserFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


/**
 * Main activity of the application.
 *
 * Container for the Buttons & Logs fragments. This activity simply tracks clicks on buttons.
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.fragmentFactory = fragmentFactory
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, TopicBrowserFragment::class.java, null)
            .commit()

        if (savedInstanceState == null) {
//            navigator.navigateTo(Screens.BUTTONS)
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()

        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }


}
