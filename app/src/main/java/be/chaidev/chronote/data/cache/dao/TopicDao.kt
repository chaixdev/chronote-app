package be.chaidev.chronote.data.cache.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import be.chaidev.chronote.data.cache.entity.TopicEntity
import be.chaidev.chronote.data.model.Topic
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {

    // topics
    @Query("SELECT * FROM topics ORDER BY topicId DESC")
    fun getAllTopics(): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topics WHERE topicId=:topicId")
    fun findTopic(topicId:String): Flow<TopicEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert( topics: TopicEntity)

    @Query("DELETE FROM topics")
    fun nukeTable()


    fun returnOrderedTopicQuery(filterAndOrder: Any): Flow<List<TopicEntity>>

}
