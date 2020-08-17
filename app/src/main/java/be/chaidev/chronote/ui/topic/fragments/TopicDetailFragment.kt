package be.chaidev.chronote.ui.topic.fragments

//import com.bumptech.glide.RequestManager

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import be.chaidev.chronote.R
import be.chaidev.chronote.model.Note
import be.chaidev.chronote.model.Subject
import be.chaidev.chronote.model.Topic
import be.chaidev.chronote.model.Type
import be.chaidev.chronote.ui.mvi.StateMessageCallback
import be.chaidev.chronote.util.Constants.TOPIC_VIEW_STATE_BUNDLE_KEY
import be.chaidev.chronote.util.DateTimeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_topic_detail.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.time.Instant

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TopicDetailFragment : BaseTopicFragment(R.layout.fragment_topic_detail) {

    /**
     * !IMPORTANT!
     * Must save ViewState b/c in event of process death the LiveData in ViewModel will be lost
     */
    override fun onSaveInstanceState(outState: Bundle) {
        val viewState = viewModel.viewState.value

        //clear the list. Don't want to save a large list to bundle.
        viewState?.topicBrowser?.topicListData = ArrayList()

        outState.putParcelable(
            TOPIC_VIEW_STATE_BUNDLE_KEY,
            viewState
        )
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        subscribeObservers()
        val topic = Topic(
            "uuidSW1",
            Subject(
                Type.YOUTUBE,
                "https://www.youtube.com/watch?v=1g3_CFmnU7k",
                "Star Wars: A New Hope",
                165000
            ),
            listOf("movie"),
            Instant.now(),
            Instant.now(),
            listOf(
                Note(
                    "noteId001",
                    "this is a note",
                    30000
                )
            )
        )
        setTopicProperties(topic)
    }


    fun subscribeObservers() {

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.viewTopic.topic?.let { topic ->
                setTopicProperties(topic)
            }
        })

        viewModel.stateMessage.observe(viewLifecycleOwner, Observer { stateMessage ->

            stateMessage?.let {
                responseHandler.onResponseReceived(
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


    fun setTopicProperties(topic: Topic) {
//        requestManager
//            .load(blogPost.image)
//            .into(blog_image)
        topic_title.text = topic.subject.title
//        topic_note_count.setText(topic.notes.size)
        topic_date_modified.text = DateTimeUtils.formatInstant(topic.dateModified)
        topic_body.text = "sample text"
    }
}

