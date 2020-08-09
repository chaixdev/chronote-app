package be.chaidev.chronote.datasources.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import be.chaidev.chronote.datasources.Converters
import be.chaidev.chronote.datasources.cache.dao.TopicDao
import be.chaidev.chronote.datasources.cache.entity.NoteEntity
import be.chaidev.chronote.datasources.cache.entity.TopicEntity

@Database(
    entities = arrayOf(TopicEntity::class, NoteEntity::class),
    version = 6,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao
}


