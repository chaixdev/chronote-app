package be.chaidev.chronote.data.cache

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import be.chaidev.chronote.data.cache.dao.NoteDao
import be.chaidev.chronote.data.cache.dao.TopicDao
import be.chaidev.chronote.data.cache.entity.NoteEntity
import be.chaidev.chronote.data.cache.entity.TopicEntity
import be.chaidev.chronote.data.model.Note
import be.chaidev.chronote.data.model.Topic
import be.chaidev.chronote.ui.mvi.AbsentLiveData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomDataCache
@Inject
constructor(
    private val topicDao: TopicDao,
    private val noteDao: NoteDao
) : DataCache<Topic> {

    override fun find(topidId: String): LiveData<Topic> {
        val findTopic = topicDao.findTopic(topidId)
        return findTopic.map { topicEntity ->
            topicEntity.toTopic(
                fetchNotesForTopic(topidId).value!!
            )
        }.asLiveData()
    }

    override fun getAll(): LiveData<List<Topic>> {

        return topicDao
            .getAllTopics()
            .map { list: List<TopicEntity> ->
                list.map { topicEntity ->
                    topicEntity.toTopic(fetchNotesForTopic(topicEntity.topicId).value!!)
                }
            }.asLiveData()

    }

    override fun save(topic: Topic) {
        // 1.read from room
        val cached: Topic = find(topic.id).value!!
        if (cached == null || cached.dateModified.isBefore(topic.dateModified)
        ) {
            // the topic is missing or outdated
            insertTopicAndNotes(topic)
        }
    }

    override fun saveAll(topics: List<Topic>) {
        topics.forEach {
            save(it)
        }
    }

    override fun delete(topicId: String) {
        TODO("Not yet implemented")
    }

    override fun returnOrderedQuery(filterAndOrder: String): LiveData<List<Topic>> {
        TODO("Not yet implemented")
    }

    private fun insertTopicAndNotes(topic: Topic) {
        topicDao.insert(TopicEntity.fromTopic(topic))
        noteDao.insertAll(topic.notes.map { NoteEntity.fromNote(it, topic.id) })
    }


    private fun fetchNotesForTopic(topicId: String): LiveData<List<Note>> {
        return noteDao.getNotesForTopic(topicId)
            .map { list: List<NoteEntity> -> list.map(NoteEntity::toNote) }

    }

}