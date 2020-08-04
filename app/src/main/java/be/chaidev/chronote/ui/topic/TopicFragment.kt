package be.chaidev.chronote.ui.topic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.chaidev.chronote.R

class TopicFragment : Fragment() {

    companion object {
        fun newInstance() = TopicFragment()
    }

    private lateinit var viewModel: TopicViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.topic_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(TopicViewModel::class.java)
        // TODO: Use the ViewModel
    }

}