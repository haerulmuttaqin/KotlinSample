package co.id.cpn.kotlinsample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.id.cpn.entity.ResultsItem
import co.id.cpn.kotlinsample.databinding.ItemMainBinding
import com.bumptech.glide.Glide

class MainItemAdapter constructor(private val onItemClick: (ResultsItem?) -> Unit) :
    PagingDataAdapter<ResultsItem, MainItemAdapter.ListItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentPosition = getItem(position)
        holder.itemView.setOnClickListener { onItemClick(currentPosition) }
        holder.bind(currentPosition)
    }

    class ListItemViewHolder(private var binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultsItem?) {
            binding.apply {
                itemTitle.text = item?.title
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ResultsItem>() {
            override fun areItemsTheSame(
                oldItem: ResultsItem,
                newItem: ResultsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResultsItem,
                newItem: ResultsItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}