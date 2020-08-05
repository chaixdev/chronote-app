package be.chaidev.chronote.data.network.dto

import be.chaidev.chronote.data.model.Subject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TopicDto(

    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("subject")
    @Expose
    val subject: Subject,

    @SerializedName("tags")
    @Expose
    val tags: List<String>,

    @SerializedName("dateCreated")
    @Expose
    val dateCreated: String,

    @SerializedName("dateModified")
    @Expose
    val dateModified: String,


    @SerializedName("notes")
    @Expose
    val notes: List<NoteDto>

)