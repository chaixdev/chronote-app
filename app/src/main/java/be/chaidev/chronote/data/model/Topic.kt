package be.chaidev.chronote.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Topic(

    val id:String,
    val subject:Subject,
    val tags: List<String>,
    val dateCreated: String,
    val dateModified: String

//    val notes: List<Note>,
)