package be.chaidev.chronote.ui.topic.state


sealed class TopicStateEvent {

    class LoadTopicsEvent() : TopicStateEvent ()

    class DeleteTopicEvent() : TopicStateEvent()

    class UpdateTopicEvent() : TopicStateEvent()

    class None: TopicStateEvent()
}