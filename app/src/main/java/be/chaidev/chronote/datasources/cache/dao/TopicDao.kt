package be.chaidev.chronote.datasources.cache.dao

import androidx.room.*
import be.chaidev.chronote.datasources.cache.entity.NoteEntity
import be.chaidev.chronote.datasources.cache.entity.TopicEntity
import be.chaidev.chronote.datasources.cache.entity.TopicWithNotes

@Dao
interface TopicDao {

    // Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopic(topics: TopicEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<NoteEntity>)

    // Read one
    @Transaction
    @Query("SELECT * FROM topics WHERE id=:topicId")
    suspend fun getTopicsWithNotes(topicId: String): TopicWithNotes

    // Read many
    @Transaction
    @Query("SELECT * FROM topics")
    suspend fun getTopicsWithNotes(): List<TopicWithNotes>

    // Update
    @Update
    suspend fun updateTopic(topic: TopicEntity)

    @Update
    suspend fun updateNotes(notes: List<NoteEntity>)

    // delete
    @Delete
    suspend fun deleteTopic(topic: TopicEntity)

    @Delete
    suspend fun deleteNotes(notes: List<NoteEntity>)

}
