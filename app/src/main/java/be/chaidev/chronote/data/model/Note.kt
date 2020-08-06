package be.chaidev.chronote.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Note (

    val id: String,
    val body: String,
    val time: Long
){
}
