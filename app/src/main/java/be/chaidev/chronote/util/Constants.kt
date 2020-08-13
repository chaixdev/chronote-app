package be.chaidev.chronote.util

object Constants {
    const val STREAMARKS_URL: String = "http://10.0.2.2:8008"
    const val TAG: String = "AppDebug"
    const val DATABASE_NAME = "chronote_db"

    const val NETWORK_TIMEOUT = 600000L
    const val CACHE_TIMEOUT = 600000L
    const val TESTING_NETWORK_DELAY = 0L // fake network delay for testing
    const val TESTING_CACHE_DELAY = 0L // fake cache delay for testing

    const val TOPIC_ORDER_ASC: String = ""
    const val TOPIC_ORDER_DESC: String = "-"


    const val MESSAGE_STACK_BUNDLE_KEY = "be.chaidev.chronote.ui.mvi.MessageStack"
    const val TOPIC_VIEW_STATE_BUNDLE_KEY = "be.chaidev.chronote.ui.topic.state.TopicViewState"
    const val PERMISSIONS_REQUEST_READ_STORAGE: Int = 301

}