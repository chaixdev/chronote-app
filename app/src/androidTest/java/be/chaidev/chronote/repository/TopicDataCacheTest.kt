package be.chaidev.chronote.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import be.chaidev.chronote.datasources.cache.dao.TopicDao
import be.chaidev.chronote.datasources.cache.entity.NoteEntity
import be.chaidev.chronote.datasources.cache.entity.TopicEntity
import be.chaidev.chronote.datasources.cache.entity.TopicWithNotes
import be.chaidev.chronote.testutil.DbTest
import be.chaidev.chronote.testutil.TestUtil.createTopic
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class RoomTest : DbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var topicDao: TopicDao

    @Before
    fun setup() {
        topicDao = db.topicDao()
    }

    @Test
    fun insertTopic() {
        runBlocking {
            launch {
                val topic = createTopic()

                db.topicDao().insertTopic(TopicEntity.fromTopic(topic))
                db.topicDao().insertNotes(topic.notes.map { note -> NoteEntity.fromNote(note, topic.id) })

                val withNotes = db.topicDao().getTopicsWithNotes().map(TopicWithNotes::toTopic)

                Assert.assertTrue(withNotes[0].id == "uuid1")
                Assert.assertTrue(withNotes[0].notes.size == 3)

            }
        }

    }
}