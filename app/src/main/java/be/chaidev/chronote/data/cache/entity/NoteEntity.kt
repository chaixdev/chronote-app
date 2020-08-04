package be.chaidev.chronote.data.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val body: String,
    val start: Long,
    val end: Long
)
