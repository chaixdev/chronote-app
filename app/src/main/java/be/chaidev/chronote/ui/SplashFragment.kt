package be.chaidev.chronote.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import be.chaidev.chronote.R
import be.chaidev.chronote.ui.mvi.DataState
import be.chaidev.chronote.ui.mvi.DataStateChangeListener
import be.chaidev.chronote.ui.topic.state.TopicStateEvent
import be.chaidev.chronote.ui.topic.state.TopicViewState
import be.chaidev.chronote.ui.topic.viewmodel.TopicBrowserViewModel
import be.chaidev.chronote.ui.topic.viewmodel.setTopicListData
import be.chaidev.chronote.util.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    lateinit var stageChangeListener: DataStateChangeListener
    private val viewModel: TopicBrowserViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // make sure we're listening to event responses
        subscribeObservers()

        if (savedInstanceState == null) {// if savedInstanceState is not null, nothing new needs to be done.

            // start loading the data into the viewmodel.
            viewModel.setStateEvent(TopicStateEvent.LoadTopicsEvent())
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->


            dataState?.let {
                // change in datastate detected, check if data still loading or
                if (!dataState.loading.isLoading) {
                    //handle the new data state change
                    handleNewTopicListData(dataState)
                }
                // forward to activity to handle messages
                stageChangeListener.onDataStateChange(dataState)
            }
        })


        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            Log.d(TAG, "BlogFragment, ViewState: ${viewState}")
            viewState?.topicBrowser?.topicsList?.let {
                if (!it.isEmpty()) {
                    findNavController().navigate(R.id.action_splashFragment_to_topicListFragment)
                } else {
                    Log.d(TAG, "Splash, ViewState:is empty")
                }
            }
        })
    }

    private fun handleNewTopicListData(dataState: DataState<TopicViewState>) {

        // Handle incoming data from DataState
        dataState.data?.let {
            it.data?.let {
                it.getContentIfNotHandled()?.let {
                    viewModel.setTopicListData(it.topicBrowser.topicsList)
                }
            }
        }

        dataState.error?.let { event ->
            event.peekContent().response.message?.let {
                // handle the error message event so it doesn't display in UI
                event.getContentIfNotHandled()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            stageChangeListener = context as DataStateChangeListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement DataStateChangeListener")
        }
    }
}