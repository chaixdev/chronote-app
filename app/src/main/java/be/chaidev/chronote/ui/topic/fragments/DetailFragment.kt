package be.chaidev.chronote.ui.topic.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import be.chaidev.chronote.R
import be.chaidev.chronote.model.Topic
import be.chaidev.chronote.ui.mvi.UICommunicationListener
import be.chaidev.chronote.ui.topic.viewmodel.TopicBrowserViewModel
import be.chaidev.chronote.util.GlideManager
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class DetailFragment
constructor(
    private val requestManager: GlideManager
) : Fragment(R.layout.fragment_detail) {

    private val CLASS_NAME = "DetailFragment"

    lateinit var uiCommunicationListener: UICommunicationListener

    val viewModel: TopicBrowserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()

        topic_image.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_finalFragment)
        }

        initUI()
    }

    private fun initUI() {
        uiCommunicationListener.showStatusBar()
        uiCommunicationListener.expandAppBar()
        uiCommunicationListener.hideCategoriesMenu()
    }

    private fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if (viewState != null) {
                viewState.viewTopic.topic?.let { topic ->
                    Log.d(CLASS_NAME, "$topic")
                    setTopicView(topic)
                }
            }
        })
    }

    private fun setTopicView(topic: Topic) {
        requestManager
            .setImage(topic.subject.getThumbnailUrl(), topic_image)
        topic_title.text = topic.subject.title
        topic_tags.text = topic.tags.joinToString(";")
        topic_body.text = "notes be here"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUICommunicationListener(null)
    }


    fun setUICommunicationListener(mockUICommuncationListener: UICommunicationListener?) {

        // TEST: Set interface from mock
        if (mockUICommuncationListener != null) {
            this.uiCommunicationListener = mockUICommuncationListener
        } else { // PRODUCTION: if no mock, get from context
            try {
                uiCommunicationListener = (context as UICommunicationListener)
            } catch (e: Exception) {
                Log.e(CLASS_NAME, "$context must implement UICommunicationListener")
            }
        }
    }


}


















