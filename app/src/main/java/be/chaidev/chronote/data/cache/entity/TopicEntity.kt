package be.chaidev.chronote.data.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "topics")
data class TopicEntity(
    @PrimaryKey
    val id:String,
    val title: String,
    val duration: Long,
    val tags: String,
//    val notes: List<Note>,
    val dateCreated: String,
    val dateModified: String
//    val uri: String,
)