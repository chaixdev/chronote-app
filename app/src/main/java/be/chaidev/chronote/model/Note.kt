package be.chaidev.chronote.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note (
    @PrimaryKey
    val id: String,
    val title: String,
    val body: String,
    val start: Long,
    val end: Long
)
