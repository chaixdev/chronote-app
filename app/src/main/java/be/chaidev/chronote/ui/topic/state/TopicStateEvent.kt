package be.chaidev.chronote.ui.topic.state

import be.chaidev.chronote.model.Topic
import be.chaidev.chronote.ui.mvi.StateEvent


sealed class TopicStateEvent : StateEvent {

    class LoadTopicsEvent : TopicStateEvent() {
        override fun errorInfo(): String {
            return "failed to load topics"
        }

    }

    class DeleteTopicEvent(val topic: Topic) : TopicStateEvent() {
        override fun errorInfo(): String {
            return "failed to delet topic"
        }
    }

    class UpdateTopicEvent(val topic: Topic) : TopicStateEvent() {
        override fun errorInfo(): String {
            return "failed to update topic"
        }
    }

    class None : TopicStateEvent() {
        override fun errorInfo(): String {
            return "no message available"

        }
    }

}