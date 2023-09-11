package com.jess.camp.todo.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jess.camp.TodoManager.getNoneBookMarkedList
import com.jess.camp.TodoManager.toggleNoneBookmark
import com.jess.camp.databinding.TodoItemBinding

class TodoListAdapter(
    private val onClickItem: (Int, TodoModel) -> Unit
) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    private val list = getNoneBookMarkedList()

    fun addItem(todoModel: TodoModel?) {
        todoModel?.let {
            list.add(todoModel)
            notifyItemChanged(list.size - 1)
        }
    }

    fun addItems(items: List<TodoModel>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun modifyItem(
        position: Int?,
        todoModel: TodoModel?
    ) {
        if (position == null || todoModel == null) {
            return
        }
        list[position] = todoModel
        notifyItemChanged(position)
    }

    fun removeItem(
        position: Int?
    ) {
        if (position == null) {
            return
        }
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun bookmarkedItem(item: TodoModel, position: Int?) {
        if (position == null) {
            return
        }
        toggleNoneBookmark(item, position)
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClickItem
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    inner class ViewHolder(
        val binding: TodoItemBinding,
        private val onClickItem: (Int, TodoModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TodoModel) = with(binding) {
            title.text = item.title
            description.text = item.description

            // 스위치 상태를 현재 아이템의 상태로 설정
            switchView.isChecked = item.bookmark

            container.setOnClickListener {
                onClickItem(adapterPosition, item)
            }

            // 스위치의 체크 상태가 변경될 때마다 아이템의 상태 업데이트
            switchView.setOnClickListener {
                bookmarkedItem(item, adapterPosition)
            }
        }
    }

}