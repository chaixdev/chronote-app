package be.chaidev.chronote.ui.topic.viewmodel
import be.chaidev.chronote.model.Topic
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.setTopicListData(topicList: List<Topic>){
    val update = getCurrentViewStateOrNew()
    update.topicBrowser.topicsList = topicList
    setViewState(update)
}
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.setTopic(topic: Topic){
    val update = getCurrentViewStateOrNew()
    update.viewTopic.topic = topic
    setViewState(update)
}

// Filter can be on tag
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.setTopicFilter(tag: String?){
    tag?.let{
        val update = getCurrentViewStateOrNew()
        update.topicBrowser.tagFilter = tag
        setViewState(update)
    }
}

// Order can be "-" or ""
//Note: "-" = DESC, "" = ASC
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.setTopicOrder(order: String){
    val update = getCurrentViewStateOrNew()
    update.topicBrowser.order = order
    setViewState(update)
}
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.removeDeletedTopics(){
    val update = getCurrentViewStateOrNew()
    val list = update.topicBrowser.topicsList.toMutableList()
    for(i in 0 until list.size){
        if(list[i] == getTopic()){
            list.remove(getTopic())
            break
        }
    }
    setTopicListData(list)
}
@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.setUpdatedTopic(topic:Topic){
    val update = getCurrentViewStateOrNew()
    val updatedTopic = update.updatedTopic

    topic.let{ updatedTopic.topic = it }
    update.updatedTopic = updatedTopic
    setViewState(update)
}

@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.updateListItem(newTopic: Topic){
    val update = getCurrentViewStateOrNew()
    val list = update.topicBrowser.topicsList.toMutableList()
    for(i in 0 until list.size){
        if(list[i].id == newTopic.id){
            list[i] = newTopic
            break
        }
    }
    update.topicBrowser.topicsList = list
    setViewState(update)
}

@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.onBlogPostUpdateSuccess(topic: Topic) {
    setUpdatedTopic(topic) // update update Fragment (not really necessary since navigating back)
    setTopic(topic) // update detail Fragment
    updateListItem(topic) // update list
}

@ExperimentalCoroutinesApi
fun TopicBrowserViewModel.setQueryInProgress(isInProgress: Boolean) {
    val update = getCurrentViewStateOrNew()
    update.topicBrowser.isQueryInProgress = isInProgress
    setViewState(update)
}
