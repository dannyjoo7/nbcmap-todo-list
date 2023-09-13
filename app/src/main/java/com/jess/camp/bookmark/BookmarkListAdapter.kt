package com.jess.camp.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jess.camp.databinding.BookmarkItemBinding
import com.jess.camp.databinding.TodoItemBinding

class BookmarkListAdapter(
    private val onBookmarkChecked: (Int, BookmarkModel) -> Unit
) : ListAdapter<BookmarkModel, BookmarkListAdapter.ViewHolder>(

    object : DiffUtil.ItemCallback<BookmarkModel>() {
        override fun areItemsTheSame(
            oldItem: BookmarkModel,
            newItem: BookmarkModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BookmarkModel,
            newItem: BookmarkModel
        ): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onBookmarkChecked
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(
        private val binding: TodoItemBinding,
        private val onBookmarkChecked: (Int, BookmarkModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BookmarkModel) = with(binding) {
            title.text = item.title
            description.text = item.description
            switchView.isChecked = item.isBookmark

            // 북마크 클릭
            switchView.setOnCheckedChangeListener { _, isChecked ->
                // 현재 바인딩된 아이템과 checked 된 값 비교 후 전달
                if (item.isBookmark != isChecked) {
                    onBookmarkChecked(
                        adapterPosition,
                        item.copy(
                            isBookmark = isChecked
                        )
                    )
                }
            }
        }
    }

}