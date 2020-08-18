package be.chaidev.chronote.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import be.chaidev.chronote.ChronoteApp
import be.chaidev.chronote.R
import be.chaidev.chronote.ui.navigation.MainFragmentFactory
import be.chaidev.chronote.ui.topic.fragments.TopicBrowserFragment
import be.chaidev.chronote.ui.topic.fragments.TopicBrowserListAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
class NavigationTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

    @Test
    fun testNavigationToDetailFragment() {

        hiltRule.inject()
        val app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as ChronoteApp


        val scenario = launchFragmentInContainer<TopicBrowserFragment>()
        val navController = TestNavHostController(app)
        navController.setGraph(R.navigation.main_nav)
        navController.setCurrentDestination(R.id.topicBrowser)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        val recyclerView = onView(withId(R.id.recycler_view))
        recyclerView.check(ViewAssertions.matches(isDisplayed()))

        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<TopicBrowserListAdapter.TopicViewHolder>(5)
        )

        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<TopicBrowserListAdapter.TopicViewHolder>(5, ViewActions.click())
        )

        assertEquals(navController.currentDestination?.id, R.id.detailFragment)
    }
}
