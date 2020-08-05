package be.chaidev.chronote.data.network.dto

import be.chaidev.chronote.data.model.Interval
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NoteDto(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("body")
    @Expose
    val body: String,

    @SerializedName("interval")
    @Expose
    val interval: Interval

    )

