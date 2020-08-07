package be.chaidev.chronote.data.cache

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import be.chaidev.chronote.data.cache.dao.NoteDao
import be.chaidev.chronote.data.cache.dao.TopicDao
import be.chaidev.chronote.data.cache.entity.NoteEntity
import be.chaidev.chronote.data.cache.entity.TopicEntity
import be.chaidev.chronote.data.model.Note
import be.chaidev.chronote.data.model.Topic
import javax.inject.Inject

class RoomDataCache
@Inject
constructor(
    private val topicDao: TopicDao,
    private val noteDao: NoteDao
) : DataCache {
    override fun findTopic(topidId: String): Topic? {
        return topicDao.findTopic(topidId)?.toTopic(fetchNotesForTopic(topidId).value)
    }

    override fun getAllTopics(): LiveData<List<Topic>> {

        return topicDao.getAllTopics().map { it.toTopic(fetchNotesForTopic(it.topicId)) }

    }

    override fun saveTopic(topic: Topic) {

        // 1.read from room
        val cached = findTopic(topic.id)

        if (cached == null || cached.dateModified.isBefore(topic.dateModified)
        ) {
            // the topic is missing or outdated
            insertTopicAndNotes(topic)
        }
    }

    override fun saveTopics(topics: List<Topic>) {
        topics.forEach {
            saveTopic(it)
        }
    }

    override fun deleteTopic(topicId: String) {
        TODO("Not yet implemented")
    }

    override  fun returnOrderedTopicQuery(filterAndOrder: String): LiveData<List<Topic>> {
        TODO("Not yet implemented")
    }

    private fun insertTopicAndNotes(topic: Topic) {
        topicDao.insert(TopicEntity.fromTopic(topic))
        noteDao.insertAll(topic.notes.map { NoteEntity.fromNote(it, topic.id) })
    }


    private fun fetchNotesForTopic(topicId: String): LiveData<List<Note>> {

        // return empty immediately if topicId not found
        val cachedNotes = noteDao.getNotesForTopic(topicId)

        return if (cachedNotes.isNotEmpty()) {
            cachedNotes.map { noteEntity -> noteEntity.toNote() }
        } else {
            emptyList()
        }
    }

}