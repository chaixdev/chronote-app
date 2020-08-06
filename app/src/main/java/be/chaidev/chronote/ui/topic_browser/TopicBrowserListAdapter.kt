package be.chaidev.chronote.ui.topic_browser

import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.chaidev.chronote.R
import be.chaidev.chronote.data.model.Topic
import be.chaidev.chronote.util.DateTimeUtils
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.topic_browser_element.view.*

class TopicBrowserListAdapter(
    private val topics: ArrayList<Topic>
) : RecyclerView.Adapter<TopicBrowserListAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(topic: Topic) {
            itemView.tv_topic_title.text = topic.subject.title
            itemView.tv_topic_date_modified.text = DateTimeUtils.formatInstant(topic.dateModified)
            itemView.tv_topic_note_count.text = "${topic.notes.size}"
            itemView.tags_container.removeAllViews()
            topic.tags.forEach{
                val chip = Chip(itemView.context)
                chip.text = it
                chip.setChipBackgroundColorResource(R.color.secondaryColor)
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

    fun replace(list:List<Topic>){
        topics.clear()
        topics.addAll(list)

    }
}