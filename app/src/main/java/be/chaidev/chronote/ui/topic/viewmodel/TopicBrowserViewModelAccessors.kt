package be.chaidev.chronote.ui.topic.viewmodel

import android.util.Log
import be.chaidev.chronote.model.Subject
import be.chaidev.chronote.model.Topic
import be.chaidev.chronote.model.Type
import be.chaidev.chronote.ui.topic.state.TopicViewState
import be.chaidev.chronote.util.Constants
import be.chaidev.chronote.util.ErrorStack
import be.chaidev.chronote.util.ErrorState
import be.chaidev.chronote.util.EspressoIdlingResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import java.time.Instant

// GETTERS

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun TopicBrowserViewModel.getCurrentViewStateOrNew(): TopicViewState {
    val value = viewState.value?.let {
        it
    } ?: TopicViewState()
    return value
}


@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.getFilter(): String {
    getCurrentViewStateOrNew().let {
        return it.topicBrowser.tagFilter ?: ""
    }
}

@InternalCoroutinesApi
@FlowPreview
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.getOrder(): String {
    getCurrentViewStateOrNew().let {
        return it.topicBrowser.order ?: Constants.TOPIC_ORDER_DESC
    }
}

@InternalCoroutinesApi
@FlowPreview
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.getTopic(): Topic {
    getCurrentViewStateOrNew().let {
        return it.viewTopic.topic?.let {
            return it
        } ?: getDummyTopic()
    }
}

@InternalCoroutinesApi
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

@InternalCoroutinesApi
@FlowPreview
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.setTopicListData(topicList: List<Topic>) {
    val update = getCurrentViewStateOrNew()
    update.topicBrowser.topicListData = topicList
    setViewState(update)
}

@InternalCoroutinesApi
@FlowPreview
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.setTopic(topic: Topic) {
    val update = getCurrentViewStateOrNew()
    update.viewTopic.topic = topic
    setViewState(update)
}

// Filter can be on tag
@InternalCoroutinesApi
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
@InternalCoroutinesApi
@FlowPreview
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.setTopicOrder(order: String) {
    val update = getCurrentViewStateOrNew()
    update.topicBrowser.order = order
    setViewState(update)
}

@InternalCoroutinesApi
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


@InternalCoroutinesApi
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


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun TopicBrowserViewModel.appendErrorState(errorState: ErrorState) {
    errorStack.add(errorState)
    Log.d(CLASS_NAME, "Appending error state. stack size: ${errorStack.size}")
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun TopicBrowserViewModel.clearError(index: Int) {
    errorStack.removeAt(index)
}


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun TopicBrowserViewModel.setErrorStack(errorStack: ErrorStack) {
    this.errorStack.addAll(errorStack)
}


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun TopicBrowserViewModel.removeJobFromCounter(stateEventName: String) {
    val update = getCurrentViewStateOrNew()
    update.activeJobCounter.remove(stateEventName)
    setViewState(update)
    EspressoIdlingResource.decrement()
}


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun TopicBrowserViewModel.isJobAlreadyActive(stateEventName: String): Boolean {
    val viewState = getCurrentViewStateOrNew()
    return viewState.activeJobCounter.contains(stateEventName)
}


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun TopicBrowserViewModel.addJobToCounter(stateEventName: String) {
    val update = getCurrentViewStateOrNew()
    update.activeJobCounter.add(stateEventName)
    setViewState(update)
    EspressoIdlingResource.increment()
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun TopicBrowserViewModel.clearActiveJobCounter() {
    val update = getCurrentViewStateOrNew()
    update.activeJobCounter.clear()
    setViewState(update)
}


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun TopicBrowserViewModel.areAnyJobsActive(): Boolean {
    val viewState = getCurrentViewStateOrNew()
    return viewState.activeJobCounter.size > 0
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun TopicBrowserViewModel.clearLayoutManagerState() {
    val update = getCurrentViewStateOrNew()
    update.topicBrowser.layoutManagerState = null
    setViewState(update)
}
