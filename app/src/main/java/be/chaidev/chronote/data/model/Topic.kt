package be.chaidev.chronote.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Topic(

    val id:String,
    val subject:Subject,
    val tags: List<String>,
    val dateCreated: Instant,
    val dateModified: Instant,
    val notes: List<Note>

){
}