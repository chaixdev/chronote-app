package be.chaidev.chronote.ui.topic_browser

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import be.chaidev.chronote.R
import be.chaidev.chronote.data.model.Topic
import be.chaidev.chronote.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.topic_browser_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TopicBrowserFragment constructor(
    private val parameter: String

) : Fragment(R.layout.topic_browser_fragment) {


    private val viewModel: TopicBrowserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "topicbrowserfragment created with parameter ${parameter}")

        subscribeObservers()
        viewModel.setStateEvent(TopicStateEvent.GetTopicEvents)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Topic>> -> {
                    displayProgressBar(false)
                    appendTopicTitles(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(progress_bar.isVisible)
                }
            }
        })
    }


    private fun displayError(message: String?) {
        if (message != null) {
            text.text = message
        } else {
            text.text = "unknown error"
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE

    }

    private fun appendTopicTitles(topics: List<Topic>) {
        val sb = StringBuilder()
        for (topic in topics) {
            sb.append(topic.title + "\n")
        }
        text.text = sb.toString()
    }

    companion object {
        private const val TAG = "TopicBrowserFragment"
    }
}