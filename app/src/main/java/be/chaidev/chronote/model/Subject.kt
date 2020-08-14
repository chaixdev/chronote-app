package be.chaidev.chronote.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Subject(
    val type: Type,
    val uri: String,
    val title: String,
    val duration: Long
) : Parcelable {


    fun getThumbnailUrl(): String {
        var youtubeId = ""
        if (uri.contains("https://youtu.be/")) {
            youtubeId = uri.split("https://youtu.be/").last()
        } else if (uri.contains("https://www.youtube.com/watch?v=")) {
            youtubeId = uri.split("https://www.youtube.com/watch?v=").last()
        }
        return "https://img.youtube.com/vi/$youtubeId/0.jpg"
    }

}