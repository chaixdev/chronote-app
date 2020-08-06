package be.chaidev.chronote.ui.topic

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import be.chaidev.chronote.R
import be.chaidev.chronote.data.model.Topic
import be.chaidev.chronote.ui.mvi.GenericViewHolder
import be.chaidev.chronote.util.DateTimeUtils
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.topic_browser_item.view.*

class TopicBrowserListAdapter(
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val NO_MORE_RESULTS = -1
    private val TOPIC_ITEM = 0
    private val TAG: String = "AppDebug"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){

            NO_MORE_RESULTS ->{
                Log.e(TAG, "onCreateViewHolder: No more results...")
                return GenericViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.topic_browser_no_results,
                        parent,
                        false
                    )
                )
            }

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
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
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

    private val differ =
        AsyncListDiffer(
            BlogRecyclerChangeCallback(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )

    internal inner class BlogRecyclerChangeCallback(
        private val adapter: TopicBrowserListAdapter
    ) : ListUpdateCallback {

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }
    }

    class TopicViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Topic) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

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