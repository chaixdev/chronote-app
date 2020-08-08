package be.chaidev.chronote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import be.chaidev.chronote.data.cache.dao.TopicDao
import be.chaidev.chronote.data.cache.entity.NoteEntity
import be.chaidev.chronote.data.cache.entity.TopicEntity
import be.chaidev.chronote.data.cache.entity.TopicWithNotes
import be.chaidev.chronote.testutil.DbTest
import be.chaidev.chronote.testutil.TestUtil.createTopic
import be.chaidev.chronote.testutil.getOrAwaitValue
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

        val topic = createTopic()

        db.topicDao().insertTopic(TopicEntity.fromTopic(topic))
        db.topicDao().insertNotes(topic.notes.map{note -> NoteEntity.fromNote(note, topic.id) })

        val withNotes = db.topicDao().getTopicsWithNotes().asLiveData().getOrAwaitValue().map(TopicWithNotes::toTopic)

        Assert.assertTrue(withNotes[0].id=="uuid1")
        Assert.assertTrue(withNotes[0].notes.size==3)
    }
}