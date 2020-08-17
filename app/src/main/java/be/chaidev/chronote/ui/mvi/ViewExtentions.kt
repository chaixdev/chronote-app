package be.chaidev.chronote.ui.mvi

import android.app.Activity
import android.widget.Toast
import be.chaidev.chronote.R
import com.afollestad.materialdialogs.MaterialDialog

fun Activity.displayToastMessage(message: String, length: Int) {
    Toast.makeText(this, message, length).show()
}

fun Activity.displayErrorDialog(
    errorMessage: String?,
    callback: ErrorDialogCallback
): MaterialDialog {
    return MaterialDialog(this)
        .show {
            title(R.string.text_error)
            message(text = errorMessage)
            positiveButton(R.string.text_ok) {
                callback.clearError()
                dismiss()
            }
            cancelOnTouchOutside(false)
        }
}

interface ErrorDialogCallback {

    fun clearError()
}

