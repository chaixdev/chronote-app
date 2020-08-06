package be.chaidev.chronote.ui.topic

import android.graphics.Rect
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import be.chaidev.chronote.R
import be.chaidev.chronote.data.model.Topic
import be.chaidev.chronote.ui.topic.viewmodel.setTopic
import be.chaidev.chronote.util.Constants
import be.chaidev.chronote.util.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.topic_browser_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TopicBrowserFragment:
    TopicFragment(R.layout.topic_browser_fragment),
    TopicBrowserListAdapter.Interaction,
    SwipeRefreshLayout.OnRefreshListener
{

    private lateinit var recyclerAdapter : TopicBrowserListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        setHasOptionsMenu(true)
        swipe_refresh.setOnRefreshListener(this)

        initRecyclerView()
        subscribeObservers()
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, Observer{ dataState ->
            if(dataState != null) {
                stateChangeListener.onDataStateChange(dataState)
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer{ viewState ->
            Log.d(TAG, "BlogFragment, ViewState: ${viewState}")
            if(viewState != null){
                recyclerAdapter.apply {
                    submitList(
                        blogList = viewState.topicBrowser.topicsList
                    )
                }

            }
        })
    }

    override fun onRefresh() {
        resetUI()
        swipe_refresh.isRefreshing = false
    }

    private fun initRecyclerView(){

        topicBrowserList.apply {
            layoutManager = LinearLayoutManager(this@TopicBrowserFragment.context)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            removeItemDecoration(topSpacingDecorator) // does nothing if not applied already
            addItemDecoration(topSpacingDecorator)

            recyclerAdapter = TopicBrowserListAdapter(this@TopicBrowserFragment)

            adapter = recyclerAdapter
        }
    }

    class TopSpacingItemDecoration(private val padding: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.top = padding
        }
    }

    override fun onItemSelected(position: Int, item: Topic) {
        viewModel.setTopic(item)
        findNavController().navigate(R.id.action_topicBrowserListFragment_to_topicFragment)
    }

    private  fun resetUI(){
        topicBrowserList.smoothScrollToPosition(0)
        stateChangeListener.hideSoftKeyboard()
        focusable_view.requestFocus()
    }
}