package be.chaidev.chronote.ui.topic

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.chaidev.chronote.R
import be.chaidev.chronote.model.Topic
import be.chaidev.chronote.ui.topic.viewmodel.setTopic
import be.chaidev.chronote.ui.topic.viewmodel.setTopicListData
import be.chaidev.chronote.util.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.topic_browser_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TopicBrowserFragment:
    TopicFragment(R.layout.topic_browser_fragment),
    TopicBrowserListAdapter.Interaction
{

    private lateinit var recyclerAdapter : TopicBrowserListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "TopicBrowserFragment  onViewCreated()")

        setHasOptionsMenu(true)

        initRecyclerView()
        subscribeObservers()
        viewModel.loadTopics()

    }

    private fun subscribeObservers() {
        Log.d(TAG, "TopicBrowserFragment  subscribeObservers()")
        // observe the datastate (state of data that backs the view)
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            Log.d(TAG, "BlogFragment, ViewState: ${dataState}")
            if (dataState != null) {
                // let ui know something changed
                stateChangeListener.onDataStateChange(dataState)

                dataState.data?.let {
                    it.data?.let { event ->
                        event.getContentIfNotHandled()?.let {
                            Log.d(TAG, "TopicFragment, datastate: $it")
                            viewModel.setTopicListData(it.topicBrowser.topicsList)
                        }
                    }
                }
            }
        })
        // observe the viewstate (the actually displayed state)
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

    private fun initRecyclerView(){
        Log.d(TAG, "TopicFragment, ViewState: initrecyclerview")
        topicBrowserList.apply {
            layoutManager = LinearLayoutManager(activity)
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
        Log.d(TAG, "item clicked:$position, topic:$item")
        findNavController().navigate(R.id.action_topicBrowserFragment_to_topicDetailFragment)
    }

    private  fun resetUI(){
        topicBrowserList.smoothScrollToPosition(0)
        stateChangeListener.hideSoftKeyboard()
        focusable_view.requestFocus()
    }
}