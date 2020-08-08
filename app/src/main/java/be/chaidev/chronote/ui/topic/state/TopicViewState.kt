package be.chaidev.chronote.ui.topic.state

import android.os.Parcelable
import be.chaidev.chronote.model.Topic
import kotlinx.android.parcel.Parcelize

@Parcelize
class TopicViewState(

    // state for topic browser
    var topicBrowser: TopicBrowser = TopicBrowser(),

    // state for update topic fragment
    var updatedTopic: UpdateTopic = UpdateTopic(),

    // state for viewTopic fragment
    var viewTopic: UpdateTopic = UpdateTopic()

) : Parcelable {

    @Parcelize
    data class TopicBrowser(
        var topicsList: List<Topic> = ArrayList(),
        var isQueryInProgress: Boolean = true,
        var order: String = "",
        var tagFilter: String = ""

    ) : Parcelable

    @Parcelize
    class ViewTopic(
        var topic: Topic? = null

    ) : Parcelable

    @Parcelize
    class UpdateTopic(
        var topic: Topic? = null

    ) : Parcelable


}