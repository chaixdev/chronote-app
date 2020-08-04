package be.chaidev.chronote.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Topic(

    val id:String,
    val title: String,
    val duration: Long,
    val tags: String,

//    val notes: List<Note>,
    val dateCreated: String,
    val dateModified: String
//    val uri: String,
)