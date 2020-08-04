package be.chaidev.chronote.data

import android.os.Handler
import android.os.Looper
import be.chaidev.chronote.data.dao.TopicDao
import be.chaidev.chronote.model.Topic
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data manager class that handles data manipulation between the database and the UI.
 */
@Singleton
class TopicLocalDataSource @Inject constructor(private val topicDao: TopicDao) {

    private val executorService: ExecutorService = Executors.newFixedThreadPool(4)
    private val mainThreadHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    fun addTopic(msg: String) {
        executorService.execute {
            topicDao.insertAll(
                Topic(
                    ""+System.nanoTime(),
                    "file://afile.txt",
                    msg,
                    123456L,
                    "green,bmx,extremesports",
                    "2020-06-04T00:00:00Z",
                    "2020-06-24T12:00:00Z"
                )
            )
        }
    }

    fun getAllTopics(callback: (List<Topic>) -> Unit) {
        executorService.execute {
            val topics = topicDao.getAll()
            mainThreadHandler.post { callback(topics) }
        }
    }

    fun removeTopics() {
        executorService.execute {
            topicDao.nukeTable()
        }
    }
}