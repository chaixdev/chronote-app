package be.chaidev.chronote.ui.topic

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import be.chaidev.chronote.R
import be.chaidev.chronote.model.Topic
import be.chaidev.chronote.util.Constants.TAG
import be.chaidev.chronote.util.DateTimeUtils
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.topic_browser_item.view.*

class TopicBrowserListAdapter(
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val NO_MORE_RESULTS = -1
    private val TOPIC_ITEM = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "TopicBrowserListAdapter  onCreateViewHolder()")
        when(viewType){
            TOPIC_ITEM ->{
                return TopicViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.topic_browser_item,
                        parent,
                        false
                    ),
                    interaction = interaction
                )
            }
            else -> {
                return TopicViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.topic_browser_item,
                        parent,
                        false
                    ),
                    interaction = interaction
                )
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Topic)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TopicViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    fun submitList(blogList: List<Topic>?){
        val newList = blogList?.toMutableList()

        differ.submitList(newList)
    }

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Topic>() {

        override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    class TopicViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Topic) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            Log.d(TAG, "TopicViewHolder  bind()")
            itemView.tv_topic_title.text = item.subject.title
            itemView.tv_topic_date_modified.text = DateTimeUtils.formatInstant(item.dateModified)
            itemView.tv_topic_note_count.text = "${item.notes.size}"
            itemView.tags_container.removeAllViews()
            item.tags.forEach{
                val chip = Chip(itemView.context)
                chip.text = it
                chip.setChipBackgroundColorResource(R.color.secondaryColor)
                itemView.tags_container.addView(chip)
            }
        }
    }
}