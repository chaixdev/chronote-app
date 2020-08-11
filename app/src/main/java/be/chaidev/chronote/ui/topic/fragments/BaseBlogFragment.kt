package be.chaidev.chronote.ui.topic.fragments
//import com.bumptech.glide.RequestManager

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import be.chaidev.chronote.ui.mvi.DataStateChangeListener
import be.chaidev.chronote.ui.topic.viewmodel.TopicBrowserViewModel

abstract class BaseTopicFragment(layoutId: Int) : Fragment(layoutId) {

    val TAG: String = "AppDebug"

    lateinit var stateChangeListener: DataStateChangeListener

    lateinit var viewModel: TopicBrowserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            stateChangeListener = context as DataStateChangeListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement DataStateChangeListener")
        }
    }
}