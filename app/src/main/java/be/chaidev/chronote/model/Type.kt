package be.chaidev.chronote.model

import com.google.gson.annotations.SerializedName

enum class Type {
    @SerializedName("TIMER")
    TIMER,
    @SerializedName("YOUTUBE")
    YOUTUBE
}
