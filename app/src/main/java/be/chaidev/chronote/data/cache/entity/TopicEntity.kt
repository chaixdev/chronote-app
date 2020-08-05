package be.chaidev.chronote.data.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "topics")
data class TopicEntity(
    @PrimaryKey
    val id: String,
    val subjectTitle: String,
    val subjectUri: String,
    val subjectType: String,
    val subjectDuration: Long,
    val tags: String,
    val dateCreated: String,
    val dateModified: String
//    val notes: List<Note>,
)