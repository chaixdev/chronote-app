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

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.MainTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
