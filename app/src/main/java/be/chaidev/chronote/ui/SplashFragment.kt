package be.chaidev.chronote.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import be.chaidev.chronote.R
import be.chaidev.chronote.ui.mvi.StateMessageCallback
import be.chaidev.chronote.ui.topic.fragments.BaseTopicFragment
import be.chaidev.chronote.ui.topic.state.TopicStateEvent
import be.chaidev.chronote.ui.topic.state.TopicViewState
import be.chaidev.chronote.ui.topic.viewmodel.getDummyTopic
import be.chaidev.chronote.util.Constants
import be.chaidev.chronote.util.Constants.TAG
import be.chaidev.chronote.util.Constants.TOPIC_VIEW_STATE_BUNDLE_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SplashFragment : BaseTopicFragment(R.layout.fragment_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Restore state after process death

        savedInstanceState?.let { inState ->
            Log.d(TAG, "BlogViewState: inState is NOT null")
            (inState[TOPIC_VIEW_STATE_BUNDLE_KEY] as TopicViewState?)?.let { viewState ->
                Log.d(TAG, "restoring view state: ${viewState}")
                viewModel.setViewState(viewState)
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // make sure we're listening to event responses

        val dummyTopic = viewModel.getDummyTopic()
        if (savedInstanceState == null) {// if savedInstanceState is not null, nothing new needs to be done.
            // start loading the data into the viewmodel.
            viewModel.setStateEvent(TopicStateEvent.LoadTopicsEvent())
        }
        subscribeObservers()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        val viewState = viewModel.viewState.value

        //clear the list. Don't want to save a large list to bundle.
        viewState?.topicBrowser?.topicListData = ArrayList()

        outState.putParcelable(Constants.TOPIC_VIEW_STATE_BUNDLE_KEY, viewState)
        super.onSaveInstanceState(outState)
    }

    private fun subscribeObservers() {

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.topicBrowser?.topicListData?.let {
                // topicListData is not null
                findNavController().navigate(R.id.action_splashFragment_to_topicBrowserFragment)
            }
        })

        viewModel.numActiveJobs.observe(viewLifecycleOwner, Observer { jobCounter ->
            uiCommunicationListener.displayProgressBar(viewModel.areAnyJobsActive())
        })

        viewModel.stateMessage.observe(viewLifecycleOwner, Observer { stateMessage ->
            stateMessage?.let {
                uiCommunicationListener.onResponseReceived(
                    response = it.response,
                    stateMessageCallback = object : StateMessageCallback {
                        override fun removeMessageFromStack() {
                            viewModel.clearStateMessage()
                        }
                    }
                )
            }
        })
    }
}