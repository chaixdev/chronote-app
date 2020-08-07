package be.chaidev.chronote.data.cache.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.chaidev.chronote.data.cache.entity.NoteEntity

@Dao
interface NoteDao {
    // note
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(note: List<NoteEntity>)

    @Query("SELECT * FROM notes WHERE topicId=:topidId")
    fun getNotesForTopic(topidId: String) : LiveData<List<NoteEntity>>

}