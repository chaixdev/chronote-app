package be.chaidev.chronote.data.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NoteDto(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("body")
    @Expose
    val body: String,

    @SerializedName("start")
    @Expose
    val start: Long,

    @SerializedName("end")
    @Expose
    val end: Long
)

