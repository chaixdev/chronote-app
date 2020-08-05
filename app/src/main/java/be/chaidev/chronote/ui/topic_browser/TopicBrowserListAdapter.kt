package be.chaidev.chronote.ui.topic_browser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.chaidev.chronote.R
import be.chaidev.chronote.data.model.Topic
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.topic_browser_element.view.*

class TopicBrowserListAdapter(
    private val topics: ArrayList<Topic>
) : RecyclerView.Adapter<TopicBrowserListAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(topic: Topic) {
            itemView.tv_topic_title.text = topic.subject.title
            itemView.tv_topic_date_modified.text = topic.dateModified
            itemView.tags_container.removeAllViews()
            topic.tags.forEach{
                val chip = Chip(itemView.context)
                chip.setText(it)
                itemView.tags_container.addView(chip)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.topic_browser_element, parent,
                false
            )
        )

    override fun getItemCount(): Int = topics.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(topics[position])

    fun addData(list: List<Topic>) {
        topics.addAll(list)
    }
}