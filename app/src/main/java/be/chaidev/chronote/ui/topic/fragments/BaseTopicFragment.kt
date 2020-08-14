package be.chaidev.chronote.ui.topic.fragments
//import com.bumptech.glide.RequestManager

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import be.chaidev.chronote.R
import be.chaidev.chronote.ui.mvi.UICommunicationListener
import be.chaidev.chronote.ui.topic.viewmodel.TopicBrowserViewModel
import be.chaidev.chronote.util.Constants.TAG
import kotlinx.coroutines.FlowPreview


@FlowPreview
abstract class BaseTopicFragment(
    layoutResId: Int
) : Fragment(layoutResId) {


    protected val viewModel: TopicBrowserViewModel by viewModels()


    lateinit var uiCommunicationListener: UICommunicationListener


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar(R.id.topicBrowserFragment, activity as AppCompatActivity)
        setupChannel()
    }


    private fun setupChannel() = viewModel.setupChannel()

    fun setupActionBar(fragmentId: Int, activity: AppCompatActivity) {
        val appBarConfiguration = AppBarConfiguration(setOf(fragmentId))
        NavigationUI.setupActionBarWithNavController(
            activity,
            findNavController(),
            appBarConfiguration
        )
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            uiCommunicationListener = context as UICommunicationListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement UICommunicationListener")
        }

    }
}