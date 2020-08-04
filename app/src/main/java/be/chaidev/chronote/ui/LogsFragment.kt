package be.chaidev.chronote.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import be.chaidev.chronote.ChronoteApp
import be.chaidev.chronote.R
import be.chaidev.chronote.data.TopicLocalDataSource
import be.chaidev.chronote.model.Topic
import be.chaidev.chronote.util.DateFormatter


/**
 * Fragment that displays the database logs.
 */
class LogsFragment : Fragment() {

    private lateinit var topics: TopicLocalDataSource
    private lateinit var dateFormatter: DateFormatter

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_logs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
        }
        recyclerView.adapter = TopicsViewAdapter(emptyList(),dateFormatter)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        populateFields(context)
    }

    private fun populateFields(context: Context) {
        topics = (context.applicationContext as ChronoteApp).serviceLocator.topicLocalDataSourceLocalDataSource
        dateFormatter = (context.applicationContext as ChronoteApp).serviceLocator.provideDateFormatter()
    }

    override fun onResume() {
        super.onResume()

        topics.getAllTopics { topics ->
            recyclerView.adapter =
                TopicsViewAdapter(
                    topics,
                    dateFormatter
                )
        }
    }
}

/**
 * RecyclerView adapter for the logs list.
 */
private class TopicsViewAdapter(
    private val topicDataSet: List<Topic>,
    private val daterFormatter: DateFormatter
) : RecyclerView.Adapter<TopicsViewAdapter.TopicsViewHolder>() {

    class TopicsViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicsViewHolder {
        return TopicsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.text_row_item, parent, false) as TextView
        )
    }

    override fun getItemCount(): Int {
        return topicDataSet.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TopicsViewHolder, position: Int) {
        val topic = topicDataSet[position]
        holder.textView.text = "${topic.subjectTitle}\n\t${topic.dateModified}"
    }
}
