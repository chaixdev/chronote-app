package be.chaidev.chronote.data.cache.dao

import androidx.room.*
import be.chaidev.chronote.data.cache.entity.TopicEntity

@Dao
interface TopicDao {

    // topics
    @Query("SELECT * FROM topics ORDER BY topicId DESC")
    suspend fun getAllTopics(): List<TopicEntity>

    @Query("SELECT * FROM topics WHERE topicId=:topicId")
    suspend fun findTopic(topicId:String): TopicEntity?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert( topics: TopicEntity)

    @Query("DELETE FROM topics")
    suspend fun nukeTable()

}
