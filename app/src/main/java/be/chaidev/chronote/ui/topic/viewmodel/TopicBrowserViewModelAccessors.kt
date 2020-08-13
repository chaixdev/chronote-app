package be.chaidev.chronote.ui.topic.viewmodel

import be.chaidev.chronote.model.Subject
import be.chaidev.chronote.model.Topic
import be.chaidev.chronote.model.Type
import be.chaidev.chronote.util.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.time.Instant

// GETTERS

@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.getFilter(): String {
    getCurrentViewStateOrNew().let {
        return it.topicBrowser.tagFilter ?: ""
    }
}

@FlowPreview
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.getOrder(): String {
    getCurrentViewStateOrNew().let {
        return it.topicBrowser.order ?: Constants.TOPIC_ORDER_DESC
    }
}

@FlowPreview
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

// SETTERS

@FlowPreview
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.setTopicListData(topicList: List<Topic>) {
    val update = getCurrentViewStateOrNew()
    update.topicBrowser.topicListData = topicList
    setViewState(update)
}

@FlowPreview
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.setTopic(topic: Topic) {
    val update = getCurrentViewStateOrNew()
    update.viewTopic.topic = topic
    setViewState(update)
}

// Filter can be on tag
@ExperimentalCoroutinesApi
@FlowPreview
fun TopicBrowserViewModel.setTopicFilter(tag: String?) {
    tag?.let {
        val update = getCurrentViewStateOrNew()
        update.topicBrowser.tagFilter = tag
        setViewState(update)
    }
}

// Order can be "-" or ""
//Note: "-" = DESC, "" = ASC
@FlowPreview
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.setTopicOrder(order: String) {
    val update = getCurrentViewStateOrNew()
    update.topicBrowser.order = order
    setViewState(update)
}

@FlowPreview
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.removeDeletedTopics() {
    val update = getCurrentViewStateOrNew()
    val list = update.topicBrowser.topicListData?.toMutableList()
    list?.let {
        for (i in 0..(list.size - 1)) {
            if (list[i] == getTopic()) {
                list.remove(getTopic())
                break
            }
        }
        setTopicListData(list.toList())
    }

}

@FlowPreview
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.setUpdatedTopic(topic: Topic) {
    val update = getCurrentViewStateOrNew()
    val updatedTopic = update.updatedTopic

    topic.let { updatedTopic.topic = it }
    update.updatedTopic = updatedTopic
    setViewState(update)
}

@FlowPreview
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.updateListItem(newTopic: Topic) {
    val update = getCurrentViewStateOrNew()
    val list = update.topicBrowser.topicListData?.toMutableList()
    list?.let {
        val newBlogPost = getTopic()
        for (i in 0..(list.size - 1)) {
            if (list[i].id == newBlogPost.id) {
                list[i] = newBlogPost
                break
            }
        }
        update.topicBrowser.topicListData = list
        setViewState(update)
    }
}

@FlowPreview
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.onTopicUpdateSuccess(topic: Topic) {
    setUpdatedTopic(topic) // update update Fragment (not really necessary since navigating back)
    setTopic(topic) // update detail Fragment
    updateListItem(topic) // update list
}