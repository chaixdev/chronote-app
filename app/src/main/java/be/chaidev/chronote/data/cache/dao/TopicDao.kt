package be.chaidev.chronote.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.chaidev.chronote.data.cache.entity.TopicEntity

@Dao
interface TopicDao {

    @Query("SELECT * FROM topics ORDER BY id DESC")
    suspend fun getAll(): List<TopicEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert( topics: TopicEntity)

    @Query("DELETE FROM topics")
    suspend fun nukeTable()
}
