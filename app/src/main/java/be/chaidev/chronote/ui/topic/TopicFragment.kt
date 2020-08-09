package be.chaidev.chronote.ui.topic

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
import be.chaidev.chronote.ui.mvi.DataStateChangeListener
import be.chaidev.chronote.ui.mvi.UICommunicationListener
import be.chaidev.chronote.ui.topic.viewmodel.TopicBrowserViewModel
import be.chaidev.chronote.util.Constants.TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
abstract class TopicFragment(fragmentResourceReference: Int) : Fragment(fragmentResourceReference){

    lateinit var stateChangeListener: DataStateChangeListener
    lateinit var uiCommunicationListener: UICommunicationListener

    protected val viewModel: TopicBrowserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "TopicFragment, onCreated() ")
        setupActionBarWithNavController(R.id.topicBrowserFragment, activity as AppCompatActivity)
        setHasOptionsMenu(true)

        viewModel.cancelActiveJobs()
    }


    // here, fragmentId is id of fragment from graph to be EXCLUDED from action back bar nav
    fun setupActionBarWithNavController(fragmentId: Int, activity: AppCompatActivity){
        Log.d(TAG, "abstract TopicFragment  setupActionBarWithNavController()")
        val appBarConfiguration = AppBarConfiguration(setOf(fragmentId))
        NavigationUI.setupActionBarWithNavController(
            activity,
            findNavController(),
            appBarConfiguration
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "abstract topicfragment onAttach()")
        try{
            stateChangeListener = context as DataStateChangeListener
        }catch(e: ClassCastException){
            Log.e(TAG, "$context must implement DataStateChangeListener" )
        }

        try{
            uiCommunicationListener = context as UICommunicationListener
        }catch(e: ClassCastException){
            Log.e(TAG, "$context must implement UICommunicationListener" )
        }
    }

}