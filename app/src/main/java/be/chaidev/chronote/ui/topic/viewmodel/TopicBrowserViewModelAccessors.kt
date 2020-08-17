package be.chaidev.chronote.ui.topic.viewmodel

import android.os.Parcelable
import android.util.Log
import be.chaidev.chronote.model.Topic
import be.chaidev.chronote.ui.topic.state.TopicViewState
import be.chaidev.chronote.util.ErrorStack
import be.chaidev.chronote.util.ErrorState
import be.chaidev.chronote.util.EspressoIdlingResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

// GETTERS

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun TopicBrowserViewModel.getCurrentViewStateOrNew(): TopicViewState {
    val value = viewState.value?.let {
        it
    } ?: TopicViewState()
    return value
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
fun TopicBrowserViewModel.getLayoutManagerState(): Parcelable? {
    val viewState = getCurrentViewStateOrNew()
    return viewState.topicBrowser.layoutManagerState
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun TopicBrowserViewModel.setLayoutManagerState(layoutManagerState: Parcelable) {
    val update = getCurrentViewStateOrNew()
    update.topicBrowser.layoutManagerState = layoutManagerState
    setViewState(update)
}

