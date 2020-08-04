package be.chaidev.chronote.data.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TopicDto(

    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("duration")
    @Expose
    val duration: Long,

    @SerializedName("tags")
    @Expose
    val tags: String,

//    val notes: List<Note>,

    @SerializedName("dateCreated")
    @Expose
    val dateCreated: String,

    @SerializedName("dateModified")
    @Expose
    val dateModified: String

)