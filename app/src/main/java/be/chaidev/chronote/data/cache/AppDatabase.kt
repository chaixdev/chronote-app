package be.chaidev.chronote.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import be.chaidev.chronote.data.cache.dao.NoteDao
import be.chaidev.chronote.data.cache.dao.TopicDao
import be.chaidev.chronote.data.cache.entity.NoteEntity
import be.chaidev.chronote.data.cache.entity.TopicEntity

@Database(
    entities = arrayOf(TopicEntity::class, NoteEntity::class),
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao
    abstract fun noteDao(): NoteDao

    companion object {
        val DATABASE_NAME: String = "chronote_db"
    }

}


