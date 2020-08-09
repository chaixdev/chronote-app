package be.chaidev.chronote.ui.topic.state

import be.chaidev.chronote.model.Topic


sealed class TopicStateEvent {

    class LoadTopicsEvent : TopicStateEvent()

    class DeleteTopicEvent(val topic: Topic) : TopicStateEvent()

    class UpdateTopicEvent : TopicStateEvent()

    class None: TopicStateEvent()
}