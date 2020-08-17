package be.chaidev.chronote.ui


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import be.chaidev.chronote.R
import be.chaidev.chronote.ui.mvi.ErrorDialogCallback
import be.chaidev.chronote.ui.mvi.UICommunicationListener
import be.chaidev.chronote.ui.mvi.displayErrorDialog
import be.chaidev.chronote.ui.topic.state.TopicViewState
import be.chaidev.chronote.ui.topic.viewmodel.*
import be.chaidev.chronote.util.Constants.ERROR_STACK_BUNDLE_KEY
import be.chaidev.chronote.util.Constants.TOPIC_VIEW_STATE_BUNDLE_KEY
import be.chaidev.chronote.util.ErrorStack
import be.chaidev.chronote.util.ErrorState
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    UICommunicationListener {

    private val CLASS_NAME = "MainActivity"


    val viewModel: TopicBrowserViewModel by viewModels()

    // keep reference of dialogs for dismissing if activity destroyed
    // also prevent recreation of same dialog when activity recreated
    private val dialogs: HashMap<String, MaterialDialog> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()

        subscribeObservers()

        restoreInstanceState(savedInstanceState)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let { inState ->
            (inState[TOPIC_VIEW_STATE_BUNDLE_KEY] as TopicViewState?)?.let { viewState ->
                viewModel.setViewState(viewState)
            }
            (inState[ERROR_STACK_BUNDLE_KEY] as ArrayList<ErrorState>?)?.let { stack ->
                val errorStack = ErrorStack()
                errorStack.addAll(stack)
                viewModel.setErrorStack(errorStack)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.clearActiveJobCounter()
        outState.putParcelable(
            TOPIC_VIEW_STATE_BUNDLE_KEY,
            viewModel.getCurrentViewStateOrNew()
        )
        outState.putParcelableArrayList(
            ERROR_STACK_BUNDLE_KEY,
            viewModel.errorStack
        )
        super.onSaveInstanceState(outState)
    }

    private fun subscribeObservers() {
        viewModel.viewState.observe(this, Observer { viewState ->
            if (viewState != null) {
                displayMainProgressBar(viewModel.areAnyJobsActive())
            }
        })

        viewModel.errorState.observe(this, Observer { errorState ->
            errorState?.let {
                displayErrorMessage(errorState)
            }
        })
    }

    private fun displayErrorMessage(errorState: ErrorState) {
        if (!dialogs.containsKey(errorState.message)) {
            dialogs.put(
                errorState.message,
                displayErrorDialog(errorState.message, object : ErrorDialogCallback {
                    override fun clearError() {
                        viewModel.clearError(0)
                    }
                })
            )
        }
    }

    private fun setupActionBar() {
        tool_bar.setupWithNavController(
            findNavController(R.id.nav_host_fragment)
        )
    }

    override fun hideCategoriesMenu() {
        Log.i(CLASS_NAME, "hideCategoriesMenu")
        tool_bar.menu.clear()
        tool_bar.invalidate()
    }

    override fun displayMainProgressBar(isLoading: Boolean) {
        if (isLoading) {
            main_progress_bar.visibility = View.VISIBLE
        } else {
            main_progress_bar.visibility = View.GONE
        }
    }

    override fun hideToolbar() {
        tool_bar.visibility = View.GONE
    }

    override fun showToolbar() {
        tool_bar.visibility = View.VISIBLE
    }

    override fun hideStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        hideToolbar()
    }

    override fun showStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        showToolbar()
    }

    override fun expandAppBar() {
        findViewById<AppBarLayout>(R.id.app_bar).setExpanded(true)
    }

    override fun onDestroy() {
        cleanUpOnDestroy()
        super.onDestroy()
    }

    private fun cleanUpOnDestroy() {
        for (dialog in dialogs) {
            dialog.value.dismiss()
        }
    }
}
