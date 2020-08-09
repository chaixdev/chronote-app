package be.chaidev.chronote.datasources.cache.dao

import androidx.room.*
import be.chaidev.chronote.datasources.cache.entity.NoteEntity
import be.chaidev.chronote.datasources.cache.entity.TopicEntity
import be.chaidev.chronote.datasources.cache.entity.TopicWithNotes
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
    fun getTopicsWithNotes(topicId: String): Flow<TopicWithNotes>

    // Read many
    @Transaction
    @Query("SELECT * FROM topics")
    fun getTopicsWithNotes(): Flow<List<TopicWithNotes>>

    // Update
    @Update
    fun updateTopic(topic: TopicEntity)

    @Update
    fun updateNotes(notes: List<NoteEntity>)

    // delete
    @Delete
    suspend fun deleteTopic(topic: TopicEntity)

    @Delete
    suspend fun deleteNotes(notes: List<NoteEntity>)

}
