package be.chaidev.chronote.ui.topic.fragments
//import com.bumptech.glide.RequestManager

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import be.chaidev.chronote.ui.mvi.ResponseHandler
import be.chaidev.chronote.ui.topic.viewmodel.TopicBrowserViewModel
import be.chaidev.chronote.util.Constants.TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@ExperimentalCoroutinesApi
@FlowPreview
abstract class BaseTopicFragment(
    layoutResId: Int
) : Fragment(layoutResId) {


    protected val viewModel: TopicBrowserViewModel by viewModels()

    lateinit var responseHandler: ResponseHandler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChannel()
    }

    private fun setupChannel() = viewModel.setupChannel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            responseHandler = context as ResponseHandler
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement UICommunicationListener")
        }

    }
}