package be.chaidev.chronote.ui.mvi

interface StateEvent {

    fun errorInfo(): String
}

data class StateMessage(val response: Response)

data class Response(
    val message: String?,
    val uiComponentType: UIComponentType,
    val messageType: MessageType
)

sealed class UIComponentType {

    class Toast : UIComponentType()

    class Dialog : UIComponentType()

    class None : UIComponentType()
}

sealed class MessageType {

    class Success : MessageType()

    class Error : MessageType()

    class Info : MessageType()

    class None : MessageType()
}


interface StateMessageCallback {

    fun removeMessageFromStack()
}
