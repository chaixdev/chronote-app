package be.chaidev.chronote.ui.topic.state

import android.os.Parcelable
import be.chaidev.chronote.model.Topic
import kotlinx.android.parcel.Parcelize

@Parcelize
class TopicViewState(


    var activeJobCounter: HashSet<String> = HashSet(),

    // state for topic browser
    var topicBrowser: TopicBrowser = TopicBrowser(),

    // state for viewTopic fragment
    var viewTopic: ViewTopic = ViewTopic()

) : Parcelable {

    @Parcelize
    data class TopicBrowser(
        var topicListData: List<Topic>? = null,
        var isQueryInProgress: Boolean? = null,
        var order: String? = null,
        var tagFilter: String? = null,
        var layoutManagerState: Parcelable? = null

    ) : Parcelable

    @Parcelize
    class ViewTopic(
        var topic: Topic? = null

    ) : Parcelable

}