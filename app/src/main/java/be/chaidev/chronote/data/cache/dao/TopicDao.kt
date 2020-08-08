package be.chaidev.chronote.data.cache.dao

import androidx.room.*
import be.chaidev.chronote.data.cache.entity.NoteEntity
import be.chaidev.chronote.data.cache.entity.TopicEntity
import be.chaidev.chronote.data.cache.entity.TopicWithNotes
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {

    // Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopic( topics: TopicEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes :List<NoteEntity>)

    // Read one
    @Transaction
    @Query("SELECT * FROM topics WHERE id=:topicId")
    fun getTopicsWithNotes(topicId:String):Flow<TopicWithNotes>

    // Read many
    @Transaction
    @Query("SELECT * FROM topics")
    fun getTopicsWithNotes(): Flow<List<TopicWithNotes>>

    @Query("SELECT * FROM topics ORDER BY dateModified ASC")
    fun returnOrderedTopicQuery(): Flow<List<TopicEntity>>

}
