package be.chaidev.chronote.ui.mvi


interface ResponseHandler {

    fun onResponseReceived(
        response: Response,
        stateMessageCallback: StateMessageCallback
    )
}