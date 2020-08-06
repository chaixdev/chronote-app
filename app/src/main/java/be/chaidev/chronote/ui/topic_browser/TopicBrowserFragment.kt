package be.chaidev.chronote.ui.topic_browser

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var listAdapter : TopicBrowserListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "topicbrowserfragment created with parameter ${parameter}")

        subscribeObservers()
        setupUI()
        viewModel.setStateEvent(TopicStateEvent.GetTopicEvents)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Topic>> -> {
                    displayProgressBar(false)
                    populateList(dataState.data)
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
        var msg = message
        if (msg == null) {
            msg = "unknown error"

        }
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE

    }

    private fun populateList(topics: List<Topic>) {
        listAdapter.replace(topics)
        listAdapter.notifyDataSetChanged()
    }

    companion object {
        private const val TAG = "TopicBrowserFragment"
    }

    private fun setupUI() {
        topicBrowserList.layoutManager = LinearLayoutManager(context)
        listAdapter = TopicBrowserListAdapter(arrayListOf())
        topicBrowserList.addItemDecoration(
            DividerItemDecoration(
                topicBrowserList.context,
                (topicBrowserList.layoutManager as LinearLayoutManager).orientation
            )
        )
        topicBrowserList.adapter = listAdapter
    }

}