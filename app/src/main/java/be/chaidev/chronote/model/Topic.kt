package be.chaidev.chronote.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topics")
data class Topic(
    @PrimaryKey
    val id:String,
    val uri: String,
    val subjectTitle: String,
    val duration: Long,
    val tags: String,
//    val notes: List<Note>,
    val dateCreated: String,
    val dateModified: String
)