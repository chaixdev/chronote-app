package be.chaidev.chronote.ui.topic.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import be.chaidev.chronote.R
import be.chaidev.chronote.model.Topic
import be.chaidev.chronote.ui.mvi.UICommunicationListener
import be.chaidev.chronote.ui.topic.state.TopicStateEvent
import be.chaidev.chronote.ui.topic.state.TopicViewState
import be.chaidev.chronote.ui.topic.viewmodel.*
import be.chaidev.chronote.util.GlideManager
import be.chaidev.chronote.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_topic_browser_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class TopicBrowserFragment
constructor(
    private val requestManager: GlideManager
) : Fragment(R.layout.fragment_topic_browser_list),
    TopicBrowserListAdapter.Interaction,
    SwipeRefreshLayout.OnRefreshListener {

    private val CLASS_NAME = "ListFragment"

    lateinit var uiCommunicationListener: UICommunicationListener

    lateinit var listAdapter: TopicBrowserListAdapter

    val viewModel: TopicBrowserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_refresh.setOnRefreshListener(this)
        initRecyclerView()
        subscribeObservers()
        initData()
    }

    override fun onPause() {
        super.onPause()
        saveLayoutManagerState()
    }

    private fun saveLayoutManagerState() {
        recycler_view.layoutManager?.onSaveInstanceState()?.let { lmState ->
            viewModel.setLayoutManagerState(lmState)
        }
    }

    fun restoreLayoutManager() {
        viewModel.getLayoutManagerState()?.let { lmState ->
            recycler_view?.layoutManager?.onRestoreInstanceState(lmState)
        }
    }


    private fun initData() {
        val viewState = viewModel.getCurrentViewStateOrNew()
        if (viewState.viewTopic.topic == null) {
            viewModel.setStateEvent(TopicStateEvent.LoadTopicsEvent())
        }
    }

    /*
     I'm creating an observer in this fragment b/c I want more control
     over it. When a blog is selected I immediately stop observing.
     Mainly for hiding the menu in DetailFragment.
     "uiCommunicationListener.hideCategoriesMenu()"
    */
    val observer: Observer<TopicViewState> = Observer { viewState ->
        if (viewState != null) {

            viewState.topicBrowser.let { view ->
                view.topicListData?.let { blogs ->
                    listAdapter.apply {
                        submitList(blogs)
                    }
                    displayTheresNothingHereTV((blogs.size > 0))
                }
            }
        }
    }

    private fun displayTheresNothingHereTV(isDataAvailable: Boolean) {
        if (isDataAvailable) {
            no_data_textview.visibility = View.GONE
        } else {
            no_data_textview.visibility = View.VISIBLE
        }
    }

    private fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, observer)
    }

    override fun onRefresh() {
        initData()
        swipe_refresh.isRefreshing = false
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@TopicBrowserFragment.context)
            addItemDecoration(TopSpacingItemDecoration(30))
            listAdapter = TopicBrowserListAdapter(requestManager, this@TopicBrowserFragment)
            adapter = listAdapter
        }
    }

    override fun restoreListPosition() {
        restoreLayoutManager()
    }

    override fun onItemSelected(position: Int, item: Topic) {
        removeViewStateObserver()
        viewModel.setTopic(item)
        findNavController().navigate(R.id.action_listFragment_to_detailFragment)
    }

    private fun removeViewStateObserver() {
        viewModel.viewState.removeObserver(observer)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUICommunicationListener(null)
    }


    fun setUICommunicationListener(mockUICommuncationListener: UICommunicationListener?) {

        // TEST: Set interface from mock
        if (mockUICommuncationListener != null) {
            this.uiCommunicationListener = mockUICommuncationListener
        } else { // PRODUCTION: if no mock, get from context
            try {
                uiCommunicationListener = (context as UICommunicationListener)
            } catch (e: Exception) {
                Log.e(CLASS_NAME, "$context must implement UICommunicationListener")
            }
        }
    }

}























