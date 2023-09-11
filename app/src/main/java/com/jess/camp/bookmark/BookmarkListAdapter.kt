package com.jess.camp.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jess.camp.TodoManager.getBookMarkedList
import com.jess.camp.TodoManager.toggleBookmark
import com.jess.camp.databinding.TodoItemBinding
import com.jess.camp.todo.home.TodoModel

class BookmarkListAdapter : RecyclerView.Adapter<BookmarkListAdapter.ViewHolder>() {

    private val list = getBookMarkedList()

    fun bookmarkedItem(item: TodoModel, position: Int?) {
        if (position == null) {
            return
        }
        toggleBookmark(item, position)
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    inner class ViewHolder(
        val binding: TodoItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TodoModel) = with(binding) {
            title.text = item.title
            description.text = item.description

            // 스위치 상태를 현재 아이템의 상태로 설정
            switchView.isChecked = item.bookmark

            // 스위치의 체크 상태가 변경될 때마다 아이템의 상태 업데이트
            switchView.setOnClickListener {
                bookmarkedItem(item, adapterPosition)
            }
        }
    }
}