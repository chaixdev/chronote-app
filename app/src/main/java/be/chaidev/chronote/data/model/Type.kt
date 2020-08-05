package be.chaidev.chronote.data.model

import com.google.gson.annotations.SerializedName

enum class Type {
    @SerializedName("TIMER")
    TIMER,
    @SerializedName("YOUTUBE")
    YOUTUBE
}
