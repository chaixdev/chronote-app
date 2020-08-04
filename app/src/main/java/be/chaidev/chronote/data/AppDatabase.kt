package be.chaidev.chronote.data

import androidx.room.Database
import androidx.room.RoomDatabase
import be.chaidev.chronote.data.dao.TopicDao
import be.chaidev.chronote.model.Topic

@Database(entities = arrayOf(Topic::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao
}
