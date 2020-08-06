package be.chaidev.chronote.data.network.dto

import be.chaidev.chronote.data.model.Note
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NoteDto(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("body")
    @Expose
    val body: String,

    @SerializedName("time")
    @Expose
    val time: Long
) {
    fun toNote(): Note {
        return Note(
            id,
            body,
            time
        )
    }

    fun fromNote(note: Note): NoteDto {

        return NoteDto(
            note.id,
            note.body,
            note.time
        )
    }
}

