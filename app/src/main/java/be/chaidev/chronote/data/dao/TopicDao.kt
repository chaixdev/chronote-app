package be.chaidev.chronote.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import be.chaidev.chronote.model.Topic

@Dao
interface TopicDao {

    @Query("SELECT * FROM topics ORDER BY id DESC")
    fun getAll(): List<Topic>

    @Insert
    fun insertAll(vararg topics: Topic)

    @Query("DELETE FROM topics")
    fun nukeTable()
}
