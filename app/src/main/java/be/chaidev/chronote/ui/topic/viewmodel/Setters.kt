package be.chaidev.chronote.ui.topic.viewmodel
import android.net.Uri
import be.chaidev.chronote.data.model.Topic

fun TopicBrowserViewModel.setTopicListData(topicList: List<Topic>){
    val update = getCurrentViewStateOrNew()
    update.topicBrowser.topicsList = topicList
    setViewState(update)
}

fun TopicBrowserViewModel.setTopic(topic: Topic){
    val update = getCurrentViewStateOrNew()
    update.viewTopic.topic = topic
    setViewState(update)
}

// Filter can be on tag
fun TopicBrowserViewModel.setTopicFilter(tag: String?){
    tag?.let{
        val update = getCurrentViewStateOrNew()
        update.topicBrowser.tagFilter = tag
        setViewState(update)
    }
}

// Order can be "-" or ""
//Note: "-" = DESC, "" = ASC
fun TopicBrowserViewModel.setTopicOrder(order: String){
    val update = getCurrentViewStateOrNew()
    update.topicBrowser.order = order
    setViewState(update)
}

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

fun TopicBrowserViewModel.setUpdatedTopic(topic:Topic){
    val update = getCurrentViewStateOrNew()
    val updatedTopic = update.updatedTopic

    topic?.let{ updatedTopic.topic = it }
    update.updatedTopic = updatedTopic
    setViewState(update)
}


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


fun TopicBrowserViewModel.onBlogPostUpdateSuccess(topic: Topic){
    setUpdatedTopic(topic) // update update Fragment (not really necessary since navigating back)
    setTopic(topic) // update detail Fragment
    updateListItem(topic) // update list
}







