//package be.chaidev.chronote.ui.topic.fragments
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.*
//import be.chaidev.chronote.R
//import be.chaidev.chronote.model.Topic
//import be.chaidev.chronote.util.GlideManager
//import com.codingwithmitch.espressodaggerexamples.R
//import com.codingwithmitch.espressodaggerexamples.models.BlogPost
//import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResource
//import com.codingwithmitch.espressodaggerexamples.util.GlideManager
//import com.codingwithmitch.espressodaggerexamples.util.GlideRequestManager
//import kotlinx.android.synthetic.main.layout_blog_list_item.view.*
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers.Main
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//
//class TopicBrowserListAdapter(
//    private val requestManager: GlideManager,
//    private val interaction: Interaction? = null
//    ) :
//    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    private val CLASS_NAME = "BlogPostListAdapter"
//
//    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Topic>() {
//
//        override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
//            return oldItem == newItem
//        }
//
//    }
//    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//
//        return BlogPostViewHolder(
//            LayoutInflater.from(parent.context).inflate(
//                R.layout.layout_blog_list_item,
//                parent,
//                false
//            ),
//            interaction,
//            requestManager
//        )
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (holder) {
//            is BlogPostViewHolder -> {
//                holder.bind(differ.currentList.get(position))
//            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return differ.currentList.size
//    }
//
//    fun submitList(list: List<Topic>) {
//        val commitCallback = Runnable {
//
//            /*
//                if process died or nav back need to restore layoutmanager AFTER
//                data is set... very annoying.
//                Not sure why I need the delay... Can't figure this out. I've tested with lists
//                100x the size of this one and the 100ms delay works fine.
//             */
//            CoroutineScope(Main).launch {
//                delay(100)
//                interaction?.restoreListPosition()
//            }
//        }
//
//        differ.submitList(list, commitCallback)
//    }
//
//    class BlogPostViewHolder
//    constructor(
//        itemView: View,
//        private val interaction: Interaction?,
//        private val requestManager: GlideManager
//    ) : RecyclerView.ViewHolder(itemView) {
//
//        fun bind(item: Topic) = with(itemView) {
//            itemView.setOnClickListener {
//                interaction?.onItemSelected(adapterPosition, item)
//            }
//            requestManager
//                .setImage(item.subject.getThumbnailUrl(), itemView.blog_image)
//            itemView.topic_tags.text = item.tags.joinToString(";")
//            itemView.topic_title.text = item.subject.title
//        }
//    }
//
//    interface Interaction {
//        fun onItemSelected(position: Int, item: Topic)
//
//        fun restoreListPosition()
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
