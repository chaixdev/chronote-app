package be.chaidev.chronote.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import be.chaidev.chronote.R
import be.chaidev.chronote.ui.mvi.*
import be.chaidev.chronote.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity :
    AppCompatActivity(),
    DataStateChangeListener,
    UICommunicationListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(Constants.TAG, "main activity onCreate()")
        setTheme(R.style.MainTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDataStateChange(dataState: DataState<*>?) {
        dataState?.let {
            GlobalScope.launch(Dispatchers.Main) {
                Log.d(Constants.TAG, "main activity data state change")
                displayProgressBar(it.loading.isLoading)

                it.error?.let { errorEvent ->
                    handleStateError(errorEvent)
                }

                it.data?.let {
                    it.response?.let { responseEvent ->
                        handleStateResponse(responseEvent)
                    }
                }
            }
        }
    }

    fun displayProgressBar(bool: Boolean) {
        if (bool) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }

    private fun handleStateResponse(event: Event<Response>) {
        event.getContentIfNotHandled()?.let {

            when (it.responseType) {
                is ResponseType.Toast -> {
                    it.message?.let { message ->
                        displayToast(message)
                    }
                }

                is ResponseType.Dialog -> {
                    it.message?.let { message ->
                        displaySuccessDialog(message)
                    }
                }

                is ResponseType.None -> {
                    Log.i(Constants.TAG, "handleStateResponse: ${it.message}")
                }
            }

        }
    }

    private fun handleStateError(event: Event<StateError>) {
        event.getContentIfNotHandled()?.let {
            when (it.response.responseType) {
                is ResponseType.Toast -> {
                    it.response.message?.let { message ->
                        displayToast(message)
                    }
                }

                is ResponseType.Dialog -> {
                    it.response.message?.let { message ->
                        displayErrorDialog(message)
                    }
                }

                is ResponseType.None -> {
                    Log.i(Constants.TAG, "handleStateError: ${it.response.message}")
                }
            }
        }
    }

    override fun onUIMessageReceived(uiMessage: UIMessage) {
        when (uiMessage.uiMessageType) {

            is UIMessageType.AreYouSureDialog -> {
                areYouSureDialog(
                    uiMessage.message,
                    uiMessage.uiMessageType.callback
                )
            }

            is UIMessageType.Toast -> {
                displayToast(uiMessage.message)
            }

            is UIMessageType.Dialog -> {
                displayInfoDialog(uiMessage.message)
            }

            is UIMessageType.None -> {
                Log.i(Constants.TAG, "onUIMessageReceived: ${uiMessage.message}")
            }
        }
    }

    override fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager
                .hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
}
