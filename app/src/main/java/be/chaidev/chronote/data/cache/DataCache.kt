package be.chaidev.chronote.data.cache

import androidx.lifecycle.LiveData
import be.chaidev.chronote.data.model.Topic

interface DataCache {

    fun findTopic(topidId:String): LiveData<Topic?>

    fun getAllTopics():LiveData<List<Topic>>

    fun saveTopic(topic:Topic)

    fun saveTopics(topics:List<Topic>)

    fun deleteTopic(topicId:String)

    fun returnOrderedTopicQuery(filterAndOrder: String): LiveData<List<Topic>>
}