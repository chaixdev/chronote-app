package be.chaidev.chronote.data.cache

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
    override suspend fun findTopic(topidId: String): Topic? {
        return topicDao.findTopic(topidId)?.toTopic(fetchNotesForTopic(topidId))
    }

    override suspend fun getAllTopics(): List<Topic> {

        return topicDao.getAllTopics().map { it.toTopic(fetchNotesForTopic(it.topicId)) }

    }

    override suspend fun saveTopic(topic: Topic) {

        // 1.read from room
        val cached = findTopic(topic.id)

        if (cached == null || cached.dateModified.isBefore(topic.dateModified)
        ) {
            // the topic is missing or outdated
            insertTopicAndNotes(topic)
        }
    }

    override suspend fun saveTopics(topics: List<Topic>) {
        topics.forEach {
            saveTopic(it)
        }
    }

    override suspend fun deleteTopic(topicId: String) {
        TODO("Not yet implemented")
    }

    private suspend fun insertTopicAndNotes(topic: Topic) {
        topicDao.insert(TopicEntity.fromTopic(topic))
        noteDao.insertAll(topic.notes.map { NoteEntity.fromNote(it, topic.id) })
    }


    private suspend fun fetchNotesForTopic(topicId: String): List<Note> {

        // return empty immediately if topicId not found
        val cachedNotes = noteDao.getNotesForTopic(topicId)

        return if (cachedNotes.isNotEmpty()) {
            cachedNotes.map { noteEntity -> noteEntity.toNote() }
        } else {
            emptyList()
        }
    }

}