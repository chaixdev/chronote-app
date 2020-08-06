package be.chaidev.chronote.data.cache

import be.chaidev.chronote.data.model.Topic

interface DataCache {

    suspend fun findTopic(topidId:String): Topic?

    suspend fun getAllTopics():List<Topic>

    suspend fun saveTopic(topic:Topic)

    suspend fun saveTopics(topics:List<Topic>)

    suspend fun deleteTopic(topicId:String)
}