package be.chaidev.chronote.ui.topic.viewmodel

import be.chaidev.chronote.data.model.Subject
import be.chaidev.chronote.data.model.Topic
import be.chaidev.chronote.data.model.Type
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.Instant
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.getFilter(): String {
    getCurrentViewStateOrNew().let {
        return it.topicBrowser.tagFilter
    }
}
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.getOrder(): String {
    getCurrentViewStateOrNew().let {
        return it.topicBrowser.order
    }
}
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.getTopic(): Topic {
    getCurrentViewStateOrNew().let {
        return it.viewTopic.topic?.let {
            return it
        } ?: getDummyTopic()
    }
}
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.getDummyTopic(): Topic {
    return Topic(
        "",
        Subject(Type.YOUTUBE, "", "", 12354),
        listOf("tag"),
        Instant.now(),
        Instant.now(),
        emptyList()
    )
}











