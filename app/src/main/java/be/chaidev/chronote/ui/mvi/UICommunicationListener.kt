package be.chaidev.chronote.ui.mvi


interface UICommunicationListener {

    fun onUIMessageReceived(uiMessage: UIMessage)
}


data class UIMessage(
    val message: String,
    val uiMessageType: UIMessageType
)

sealed class UIMessageType{

    class Toast: UIMessageType()

    class Dialog: UIMessageType()

    class AreYouSureDialog(
        val callback: AreYouSureCallback
    ): UIMessageType()

    class None: UIMessageType()
}